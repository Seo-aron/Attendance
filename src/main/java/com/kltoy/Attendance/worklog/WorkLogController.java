package com.kltoy.Attendance.worklog;

import com.kltoy.Attendance.employee.Employee;
import com.kltoy.Attendance.worklog.dto.WorkLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/worklog")
public class WorkLogController {

    private final WorkLogRepository workLogRepository;

    @GetMapping("/list")
    public String worklogList(Model model, @ModelAttribute("loginUser") Employee employee) {
        // 인터셉터가 로그인을 보장하고, GlobalControllerAdvice가 employee 객체를 넣어주므로
        // session 확인 및 DB 조회가 필요 없습니다.

        boolean isBoss = "대표".equals(employee.getPosition());
        model.addAttribute("isBoss", isBoss);

        if (isBoss) {
            // 대표인 경우: 모든 업무일지를 가져와서 작성자(Employee) 기준으로 그룹화
            List<WorkLog> allWorkLogs = workLogRepository.findAll(Sort.by(Sort.Direction.DESC, "workDate"));
            
            // LinkedHashMap을 사용하여 DB에서 가져온 순서(최신 날짜순)가 화면에서도 유지되도록 수정
            Map<Employee, List<WorkLog>> groupedWorkLogs = allWorkLogs.stream()
                    .collect(Collectors.groupingBy(
                            WorkLog::getEmployee,
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));
            
            model.addAttribute("groupedWorkLogs", groupedWorkLogs);
        } else {
            // 일반 직원인 경우: 자신의 업무일지만 가져옴
            List<WorkLog> myWorkLogs = workLogRepository.findByEmployeeId(employee.getId());
            model.addAttribute("workLogs", myWorkLogs);
        }

        return "worklog/list";
    }

    @GetMapping("/new")
    public String workInsert() {
        // 로그인 체크 불필요 (인터셉터가 처리)
        return "worklog/form";
    }

    @PostMapping("/save")
    @Transactional
    public String workSave(WorkLogSaveRequestDto dto, @ModelAttribute("loginUser") Employee employee){
        // 저장할 WorkLog 객체 만들기
        WorkLog workLog = WorkLog.builder()
                .employee(employee)
                .workDate(dto.getWorkDate())
                .content(dto.getContent())
                .build();

        WorkLog savedLog = workLogRepository.save(workLog);

        return "redirect:/worklog/list";
    }

    @PostMapping("/updateStatus")
    @Transactional
    public String updateStatus(@RequestParam("logId") Long logId, 
                               @RequestParam("status") String status, 
                               @ModelAttribute("loginUser") Employee approver) {
        
        // 1. 상태를 변경할 업무일지(WorkLog) 조회
        WorkLog workLog = workLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("해당 업무일지가 존재하지 않습니다."));

        // 2. 선택된 상태(status)에 따라 결재 처리
        if ("APPROVED".equals(status)) {
            workLog.approve(approver);
        } else if ("REJECTED".equals(status)) {
            workLog.reject(approver);
        }

        // 3. 처리가 끝나면 다시 목록 화면으로 이동
        return "redirect:/worklog/list";
    }
}

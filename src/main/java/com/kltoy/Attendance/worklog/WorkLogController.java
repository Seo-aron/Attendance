package com.kltoy.Attendance.worklog;

import com.kltoy.Attendance.employee.Employee;
import com.kltoy.Attendance.employee.EmployeeRepository;
import com.kltoy.Attendance.worklog.dto.WorkLogSaveRequestDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/worklog")
public class WorkLogController {

    private final WorkLogRepository workLogRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/list")
    public String worklogList(Model model, HttpSession session) {
        Long employeeId = (Long) session.getAttribute("loginSession");

        List<WorkLog> workLogs = workLogRepository.findByEmployeeId(employeeId);
        model.addAttribute("workLogs", workLogs);

        return "worklog/list";
    }

    @GetMapping("/new")
    public String workInsert() {
        // 로그인 체크 불필요 (인터셉터가 처리)
        return "worklog/form";
    }

    @PostMapping("/save")
    @Transactional
    public String workSave(WorkLogSaveRequestDto dto, HttpSession session){
        Long employeeId = (Long) session.getAttribute("loginSession");

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 올바르지 않습니다."));

        // 저장할 WorkLog 객체 만들기
        WorkLog workLog = WorkLog.builder()
                .employee(employee)
                .workDate(dto.getWorkDate())
                .content(dto.getContent())
                .build();

        WorkLog savedLog = workLogRepository.save(workLog);

        return "redirect:/worklog/list";
    }
}
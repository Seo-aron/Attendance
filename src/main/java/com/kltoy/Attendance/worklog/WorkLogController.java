package com.kltoy.Attendance.worklog;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public String workSave(){
        //TODO: 매개변수: dto, session
        // dto파일 만들기

        return "redirectt:/worklog/list";
    }
}
package com.kltoy.Attendance.worklog;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<WorkLog> workLogs;

        // 로그인 상태 확인
        if (employeeId != null) {
            workLogs = workLogRepository.findByEmployeeId(employeeId);
        } else {
            return "redirect:/login/login";
        }

        model.addAttribute("workLog", workLogs);

        return "worklog/list";
    }
}

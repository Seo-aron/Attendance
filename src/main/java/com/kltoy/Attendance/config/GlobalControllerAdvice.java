package com.kltoy.Attendance.config;

import com.kltoy.Attendance.employee.Employee;
import com.kltoy.Attendance.employee.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final EmployeeRepository employeeRepository;

    // 모든 컨트롤러의 Model에 "loginUser"라는 이름으로 로그인한 직원 정보를 자동으로 담아줍니다.
    @ModelAttribute("loginUser")
    public Employee addLoginUserToModel(HttpSession session) {
        Long employeeId = (Long) session.getAttribute("loginSession");
        if (employeeId != null) {
            return employeeRepository.findById(employeeId).orElse(null);
        }
        return null;
    }
}

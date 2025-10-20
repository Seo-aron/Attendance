package com.kltoy.Attendance.login;

import com.kltoy.Attendance.employee.Employee;
import com.kltoy.Attendance.employee.EmployeeService;
import com.kltoy.Attendance.employee.dto.EmployeeJoinRequestDto;
import com.kltoy.Attendance.login.dto.LoginRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    final private EmployeeService employeeService;

    @GetMapping("/login")
    public String loginPage(){
        return "login/login";
    }

    @PostMapping("/login")
    public String loginProcess(@Valid LoginRequestDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/login";
        }

        Employee loginEmployee = employeeService.login(dto);

        if (loginEmployee == null) {
            model.addAttribute("loginError", "사원번호 혹은 비밀번호가 일치하지 않습니다.");
            return "login/login";
        }

        return "redirect:/employee/list";
    }


    @GetMapping("/join")
    public String employeeJoin(Model model) {
        model.addAttribute("employeeJoinRequest", new EmployeeJoinRequestDto());

        return "login/join";
    }

    @PostMapping("/join")
    public String joinProcess(@Valid EmployeeJoinRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/join";
        }
        employeeService.join(dto);

        return "redirect:/";
    }

}

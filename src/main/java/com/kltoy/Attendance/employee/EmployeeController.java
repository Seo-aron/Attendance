package com.kltoy.Attendance.employee;

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

import java.util.List;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public String employeeList(Model model) {
        List<Employee> employeeList = employeeRepository.findAll();
        model.addAttribute("employees", employeeList);

        return "employee/list";
    }
}

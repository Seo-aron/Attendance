package com.kltoy.Attendance.employee;

import com.kltoy.Attendance.employee.dto.EmployeeUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @ResponseBody // JSON 반환 명시
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        // GET 요청은 ID만 받습니다. RequestBody DTO는 필요 없습니다.
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id,
                                                    @Valid @RequestBody EmployeeUpdateRequestDto dto) {
        try {
            Employee updateEmployee = employeeService.update(id, dto);
            return ResponseEntity.ok(updateEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

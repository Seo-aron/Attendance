package com.kltoy.Attendance.employee.dto;

import com.kltoy.Attendance.employee.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequestDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String phone;

    @NotBlank(message = "부서는 필수 입력 항목입니다.")
    private String department;

    @NotBlank(message = "직책은 필수 입력 항목입니다.")
    private String position;

    @NotNull(message = "재직 상태는 필수 선택 항목입니다.") // Enum 타입은 NotBlank 대신 NotNull 사용
    private Employee.EmployeeStatus status;
}
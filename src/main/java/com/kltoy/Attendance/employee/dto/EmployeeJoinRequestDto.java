package com.kltoy.Attendance.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 데이터 유효성 검사 DTO
 */

@Getter
@Setter
public class EmployeeJoinRequestDto {

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @NotBlank
    @Size(min = 4, message = "비밀번호는 4글자 이상입니다.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String phone;

    @NotBlank(message = "부서는 필수 입력 항목입니다.")
    private String department;

    @NotBlank(message = "직책은 필수 입력 항목입니다.")
    private String position;

    @NotBlank(message = "입사일은 필수 입력 항목입니다.")
    private String hireDate;
}

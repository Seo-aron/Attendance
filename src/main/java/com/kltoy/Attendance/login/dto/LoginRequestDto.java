package com.kltoy.Attendance.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "사원번를 입력해주세요")
    private String empNo;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}

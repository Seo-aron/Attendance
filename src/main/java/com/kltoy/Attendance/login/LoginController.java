package com.kltoy.Attendance.login;

import com.kltoy.Attendance.employee.Employee;
import com.kltoy.Attendance.employee.EmployeeService;
import com.kltoy.Attendance.employee.dto.EmployeeJoinRequestDto;
import com.kltoy.Attendance.login.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String loginProcess(@Valid LoginRequestDto dto, BindingResult bindingResult, Model model,
                               HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/login";
        }

        Employee loginEmployee = employeeService.login(dto);

        if (loginEmployee == null) {
            model.addAttribute("loginError", "사원번호 혹은 비밀번호가 일치하지 않습니다.");
            return "login/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginSession", loginEmployee.getId());

        return "redirect:/employee/list";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // 세션 안전하게 파괴
        return "redirect:/login/login";
    }

    @GetMapping("/join")
    public String employeeJoin(Model model) {
        // 생성될 사번을 미리 가져와서 모델에 담습니다.
        String expectedEmpNo = employeeService.createEmpNo();
        EmployeeJoinRequestDto dto = new EmployeeJoinRequestDto();
        dto.setEmpNo(expectedEmpNo); // DTO에 사번 설정
        
        model.addAttribute("employeeJoinRequest", dto);
        model.addAttribute("expectedEmpNo", expectedEmpNo);

        return "login/join";
    }

    @PostMapping("/join")
    public String joinProcess(@Valid EmployeeJoinRequestDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "login/join";
        }
        String newEmpNo = employeeService.join(dto);
        
        // 타임리프 inline 자바스크립트에서 자동으로 문자열 이스케이프 처리를 해주기 때문에 단일 \n으로 변경합니다.
        redirectAttributes.addFlashAttribute("joinSuccessMessage", "회원가입이 완료되었습니다.\n귀하의 사번은 [" + newEmpNo + "] 입니다.");

        return "redirect:/login/login";
    }

}

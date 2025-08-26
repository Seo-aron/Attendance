package com.kltoy.Attendance.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestUserRepository testUserRepository;

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("user", testUserRepository.findAll());

        return "test";
    }
}

package com.kltoy.Attendance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login/login";
    }

    @GetMapping("/main")
    public String main() {
        return "redirect:/employee/list";
    }
}

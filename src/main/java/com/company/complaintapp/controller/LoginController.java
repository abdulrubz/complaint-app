package com.company.complaintapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String welcomePage() {
        System.out.println("In home endpoint");
        return "home";
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}

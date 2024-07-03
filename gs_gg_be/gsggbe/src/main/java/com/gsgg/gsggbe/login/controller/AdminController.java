package com.gsgg.gsggbe.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AdminController {
    @GetMapping("/admin")
    public String adminLogIn() {
        return "Admin Controller";
    }

}

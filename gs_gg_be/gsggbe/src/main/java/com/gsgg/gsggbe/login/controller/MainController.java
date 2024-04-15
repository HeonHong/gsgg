package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.service.CustomUserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final CustomUserDetailsService customUserDetailsService;

    public MainController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/login")
    public String mainLogIn() {

        return "Main Controller";
    }
}

package com.example.coordination.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class HomerController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/home")
    public String home() {
        return "home";
    }
}

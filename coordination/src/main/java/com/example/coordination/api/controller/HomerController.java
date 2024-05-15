package com.example.coordination.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class HomerController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/home", "/"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String home() {
        return "home";
    }

    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }

    @GetMapping("/brand-min-price")
    public String brandMinPrice() {
        return "brand-min-price";
    }

    @GetMapping("/category-min-max-price")
    public String categoryMinMaxPrice() {
        return "category-min-max-price";
    }
}

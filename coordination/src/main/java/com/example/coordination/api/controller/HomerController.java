package com.example.coordination.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class HomerController {
    /**
     * @return
     * @title 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * @param request
     * @param response
     * @return
     * @title 로그아웃 페이지
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    /**
     * @return
     * @title 홈 페이지
     */
    @RequestMapping(value = {"/home", "/"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String home() {
        return "home";
    }

    /**
     * @return
     * @title 카테고리 페이지
     */
    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }

    /**
     * @return
     * @title 최소가격 브랜드 페이지
     */
    @GetMapping("/brand-min-price")
    public String brandMinPrice() {
        return "brand-min-price";
    }

    /**
     * @return
     * @title 카테고리 별 퇴소,퇴고 가격
     */
    @GetMapping("/category-min-max-price")
    public String categoryMinMaxPrice() {
        return "category-min-max-price";
    }

    /**
     * @return
     * @title 어드민 페이지
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}

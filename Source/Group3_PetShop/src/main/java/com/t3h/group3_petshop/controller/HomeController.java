package com.t3h.group3_petshop.controller;

import com.t3h.group3_petshop.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views")
public class HomeController {
    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Trang chủ");

        return "index";
    }
}

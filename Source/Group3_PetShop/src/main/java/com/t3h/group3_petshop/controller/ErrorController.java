package com.t3h.group3_petshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("pageTitle", "Trang chủ");
        return "index";
    }
}
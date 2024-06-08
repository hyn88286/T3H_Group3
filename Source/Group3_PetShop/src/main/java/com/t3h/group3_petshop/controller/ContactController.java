package com.t3h.group3_petshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/contact")
public class ContactController {
    @GetMapping("")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "liên hệ");
        return "contact";
    }
}

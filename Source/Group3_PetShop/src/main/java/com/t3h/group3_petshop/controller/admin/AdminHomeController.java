package com.t3h.group3_petshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {
    @GetMapping("server/home")
    public String home() {
        return "server/index";
    }
}

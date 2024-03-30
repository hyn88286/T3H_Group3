package com.t3h.group3_petshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.StringBufferInputStream;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @GetMapping()
    public String admin(){
        return "admin/index";
    }

    @GetMapping("/user")
    public String user(){
        return "admin/user";
    }
}

package com.t3h.group3_petshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class AdminController {
    @RequestMapping("/admin")
//    @GetMapping("/index")
    public String admin(){
        return "admin/index";
    }
}
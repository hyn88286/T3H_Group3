package com.the.doanthucung.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Admincontroller {
    @RequestMapping("/admin")
//    @GetMapping("/index")
    public String admin(){
        return "admin/index";
    }
}

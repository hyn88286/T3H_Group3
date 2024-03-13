package com.t3h.group3_petshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/home")
public class HomeController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}

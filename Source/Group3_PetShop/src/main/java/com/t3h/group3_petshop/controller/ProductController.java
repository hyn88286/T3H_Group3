package com.t3h.group3_petshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/product")
public class ProductController {
    @GetMapping()
    public String index(){
        return "product";
    }

}

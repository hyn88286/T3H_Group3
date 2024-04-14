package com.t3h.group3_petshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @GetMapping()
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/user")
    public String adminUser() {
        return "admin/user";
    }

    @GetMapping("/category")
    public String adminCategory() {
        return "admin/category";
    }
    @GetMapping("/comment")
    public String adminComment() {
        return "admin/comment";
    }

    @GetMapping("/product")
    public String product(){
        return "admin/product";
    }
}

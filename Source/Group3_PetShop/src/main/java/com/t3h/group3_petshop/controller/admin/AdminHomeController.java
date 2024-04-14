package com.t3h.group3_petshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @GetMapping()
    public String admin(Model model) {
        model.addAttribute("adminTitle", "Trang chủ");
        return "admin/index";
    }

    @GetMapping("/user")
    public String adminUser(Model model) {
        model.addAttribute("adminTitle", "Quản lý tài khoản");
        return "admin/user";
    }

    @GetMapping("/category")
    public String adminCategory(Model model) {
        model.addAttribute("adminTitle", "Quản lý danh mục");
        return "admin/category";
    }

    @GetMapping("/comment")
    public String adminComment(Model model) {
        model.addAttribute("adminTitle", "Quản lý bình luận");
        return "admin/comment";
    }

    @GetMapping("/product")
    public String product(Model model) {
        model.addAttribute("adminTitle", "Quản lý sản phẩm");
        return "admin/product";
    }

    @GetMapping("/Oder")
    public String adminOder(Model model) {
        model.addAttribute("adminTitle", "Quản lý đơn hàng");
        return "admin/Oder";
    }
}

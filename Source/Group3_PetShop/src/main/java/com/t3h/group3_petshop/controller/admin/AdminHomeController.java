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
        model.addAttribute("adminTitle", "Trang Chủ Admin PetHund");
        return "admin/index";
    }

    @GetMapping("/user")
    public String adminUser(Model model) {
        model.addAttribute("adminTitle", "Quản Lý Tài Khoản");
        return "admin/user";
    }

    @GetMapping("/category")
    public String adminCategory(Model model) {
        model.addAttribute("adminTitle", "Quản Lý Danh Mục");
        return "admin/category";
    }

    @GetMapping("/comment")
    public String adminComment(Model model) {
        model.addAttribute("adminTitle", "Quản lý Bình Luận Người Dùng");
        return "admin/comment";
    }

    @GetMapping("/product")
    public String product(Model model) {
        model.addAttribute("adminTitle", "Quản Lý Sản Phẩm");
        return "admin/product";
    }

    @GetMapping("/Oder")
    public String adminOder(Model model) {
        model.addAttribute("adminTitle", "Quản Lý Đơn Hàng");
        return "admin/Oder";
    }

    @GetMapping("/addProduct")

    public String addProduct(Model model){
        model.addAttribute("adminTitle", "Thêm Mới Sản Phẩm");
        return "admin/addProduct";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(Model model){
        model.addAttribute("adminTitle", "Cập Nhật Sản Phẩm");
        return "admin/updateProduct";
    }
}

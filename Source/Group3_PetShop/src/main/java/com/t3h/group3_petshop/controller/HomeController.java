package com.t3h.group3_petshop.controller;

import com.t3h.group3_petshop.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/home")
public class HomeController {
    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Trang chủ");

        return "index";
    }


    @GetMapping("sản-phẩm")// link này chuyển trang trong layout dùng chung
    public String product(Model model) {
        model.addAttribute("pageTitle", "sản phẩm");

        return "product"; //  này là chỉ tới trang giao diên html
    }



    @GetMapping("Giỏ-hàng")// link này chuyển trang trong layout dùng chung
    public String shopCart(Model model) {
        model.addAttribute("pageTitle", "Thông tin giỏ hàng");

        return "shop_cart"; //  này là chỉ tới trang giao diên html
    }



}

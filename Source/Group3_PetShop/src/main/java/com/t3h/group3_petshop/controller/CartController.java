package com.t3h.group3_petshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/cart")
public class CartController {
    @GetMapping()
    public String product(Model model) {
        model.addAttribute("pageTitle", "Giỏ hàng");
        return "shop_cart";
    }
}

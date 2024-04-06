package com.t3h.group3_petshop.controller;

import com.t3h.group3_petshop.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/order")
public class OrderController {
//    @GetMapping("/place-order/{code}")
//    public String submitOrder(@PathVariable String code){
//        return "place_order";
//    }

    @GetMapping("/place-order/{code}")// link này chuyển trang trong layout dùng chung
    public String shop(@PathVariable("code") String code, Model model){
        model.addAttribute("pageTitle", "Thanh toán đơn hàng");

        return "place_order"; //  này là chỉ tới trang giao diên html
    }
}

package com.t3h.group3_petshop.controller;

import com.t3h.group3_petshop.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/order")
public class OrderController {
    @GetMapping("/place-order/{code}")
    public String submitOrder(@PathVariable String code) {
        return "place_order";
    }

    @GetMapping()
    public String listOrder() {
        return "order";
    }

    @GetMapping("/payment/result/{code}")
    public String resultPayment(@PathVariable String code) {
        return "result_payment";
    }

}

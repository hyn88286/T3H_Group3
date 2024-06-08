package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
public class PaymentMethodResource {
    @Autowired
    private IPaymentService paymentService;
    @PostMapping("/vnpay")
    public ResponseEntity<?> getVnPay(@RequestBody OrderFilterRequest request) {
        return ResponseEntity.ok(paymentService.createVnPay(request));
    }
}

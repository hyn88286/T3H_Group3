package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
public class PaymentMethodResource {
    @Autowired
    private IPaymentService paymentService;
    @GetMapping("/vnpay")
    public ResponseEntity<?> getVnPay() {
        return ResponseEntity.ok(paymentService.createVnPay());
    }
}

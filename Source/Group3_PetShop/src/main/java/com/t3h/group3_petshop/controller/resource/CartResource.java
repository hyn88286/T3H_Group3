package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.CartDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartResource {

    @Autowired
    private ICartService service;

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<CartDTO>>> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                              @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(service.addToCart(cartDTO));
    }
}

package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
    @RequestMapping("api/order")
public class OrderResource {

    private final IOrderService service;

    public OrderResource(IOrderService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<OrderDTO>>> getAll(@RequestBody OrderFilterRequest filterRequest,
                                                               @RequestParam(name = "page",required = false,defaultValue = "0") int page,
                                                               @RequestParam(name = "size",required = false,defaultValue = "10") int size){

        return ResponseEntity.ok(service.getAll(filterRequest,page,size));
    }

}

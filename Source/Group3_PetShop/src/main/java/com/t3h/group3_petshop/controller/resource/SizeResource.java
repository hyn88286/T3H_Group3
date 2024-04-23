package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.CategoryDTO;
import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.request.CategoryFilterRequest;
import com.t3h.group3_petshop.model.request.SizeFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/size")
public class SizeResource {
    @Autowired
    private ISizeService sizeService;
    @PostMapping()
    public ResponseEntity<BaseResponse<Page<SizeDTO>>> getAll(@RequestBody SizeFilterRequest filterRequest,
                                                              @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                              @RequestParam(name = "size", required = false, defaultValue = "10") int size){

        return ResponseEntity.ok(sizeService.getAll(filterRequest,page,size));
    }
}

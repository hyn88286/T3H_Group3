package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product")
public class ProductResource {

    private final IProductService service;


    public ProductResource(IProductService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<ProductDTO>>> getAll(@RequestBody ProductFilterRequest filterRequest,
                                                                 @RequestParam(name = "page",required = false,defaultValue = "0") int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10") int size){

        return ResponseEntity.ok(service.getAll(filterRequest,page,size));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduction(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(service.createProduct(productDTO));
    }
}
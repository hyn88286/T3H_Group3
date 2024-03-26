package com.the.doanthucung.controller.resource;

import com.the.doanthucung.model.dto.ProductDTO;
import com.the.doanthucung.model.request.ProductFilterRequest;
import com.the.doanthucung.model.response.BaseResponse;
import com.the.doanthucung.service.IProductService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductResource {
    private final IProductService service;

    public ProductResource(IProductService service) {
        this.service = service;
    }
    @PostMapping()
    public ResponseEntity<BaseResponse<Page<ProductDTO>>> getAll(@RequestBody ProductFilterRequest filterRequest,
                                                                 @RequestParam(name = "page",required = false,defaultValue ="0" ) int page,
                                                            @RequestParam(name = "size", required = false,defaultValue = "10") int size){
        return ResponseEntity.ok(service.getAll(filterRequest,page,size));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createProduction(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(service.createProduct(productDTO));
    }
    @GetMapping
    public ResponseEntity<Resource> exportData (){
        return ResponseEntity.ok(null);
    }
}

package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("api/product")
public class ProductResource {

    private final IProductService service;

    public ProductResource(IProductService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<ProductDTO>>> getAll(@RequestBody ProductFilterRequest filterRequest,
                                                                 @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(service.getAll(filterRequest, page, size));
    }

    @PostMapping("/create")
    public ResponseEntity<?> updateProduction(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.createProduct(productDTO));
    }

    @PostMapping("/update/{code}")
    public ResponseEntity<?> updateProduction(@PathVariable("code") String code, @RequestBody ProductDTO productDTO) throws Exception {
        return ResponseEntity.ok(service.updateProduct(code, productDTO));
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getProductBy(@RequestBody ProductFilterRequest filterRequest) throws Exception {
        return ResponseEntity.ok(service.getProductBy(filterRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> deleteProduct(@PathVariable("id") Long id) {
        BaseResponse<?> response = service.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
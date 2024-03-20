package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.ProductImageDTO;
import com.t3h.group3_petshop.model.request.ProductImageRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/image/product")
public class ImageResource {
    @Autowired
    private IImageService service;
    @PostMapping()
    public ResponseEntity<BaseResponse<ProductImageDTO>> getFirstByProductId(@RequestBody ProductImageRequest filterRequest){
        return ResponseEntity.ok(service.findByProductId(filterRequest));
    }
}

package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.CartDTO;
import com.t3h.group3_petshop.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/image")
public class FileResource {
    @Autowired
    private IFileService service;

    @PostMapping("/storage")
    public ResponseEntity<?> storageImage(@RequestParam("image") MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.storageImage(file));
    }
}

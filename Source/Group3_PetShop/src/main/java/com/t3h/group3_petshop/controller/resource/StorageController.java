package com.t3h.group3_petshop.controller.resource;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("api/storage")
public class StorageController {
    @Autowired
    private IStorageService storageService;
    @PostMapping
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") Set<MultipartFile> files, Long productId) throws IOException {
        BaseResponse<?> uploadImage = storageService.uploadMultipartFile(files, productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
}

package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface IStorageService {
    BaseResponse<?> uploadMultipartFile(Set<MultipartFile> files, Long productId) throws IOException;
}

package com.t3h.group3_petshop.service;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;


public interface IFileService {
    BaseResponse<String> storageImage(MultipartFile file) throws Exception;
}

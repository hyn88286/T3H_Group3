package com.t3h.group3_petshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStorageService {

    String uploadImage(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName);
}

package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.ProductImageEntity;
import com.t3h.group3_petshop.model.dto.ProductImageDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.ProductRepository;
<<<<<<< HEAD
import com.t3h.group3_petshop.repository.StorageRepository;
=======
import com.t3h.group3_petshop.repository.ImageRepository;
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
import com.t3h.group3_petshop.service.IStorageService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
public class StorageServiceImpl implements IStorageService {
    @Autowired
<<<<<<< HEAD
    private StorageRepository storageRepository;
=======
    private ImageRepository storageRepository;
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public BaseResponse<?> uploadMultipartFile(Set<MultipartFile> files, Long productId) throws IOException {
        File directory = new File(Constant.IMAGE_PRODUCT_PATH);
        if (!directory.exists()) { //Kiểm tra thư mục đã tồn tại chưa
            directory.mkdirs(); //Tạo các thư mục theo path cần thiết
        }
        long timestamp = Instant.now().getEpochSecond(); //Lấy thời gian hiện tại trả về timestamp

        BaseResponse<?> baseResponse = new BaseResponse<>();
        for (MultipartFile file : files) {
            boolean uploadFile = false;
<<<<<<< HEAD
            //Thêm timestamp trước phần extension để tránh trùng file name
            int lastIndex = file.getOriginalFilename().lastIndexOf('.');
            String nameBeforeExtension = file.getOriginalFilename().substring(0, lastIndex);
            String extension = file.getOriginalFilename().substring(lastIndex + 1);
            String fileName = nameBeforeExtension + "_" + timestamp + "." + extension;
=======

            int lastIndex = file.getOriginalFilename().lastIndexOf('.');
            String extension = file.getOriginalFilename().substring(lastIndex + 1);
            String fileName =  timestamp + "." + extension;
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94

            String filePath = Constant.IMAGE_PRODUCT_PATH + fileName; //Đường dẫn lưu ảnh

            Optional<ProductEntity> productEntity = productRepository.findById(productId);

            ProductImageDTO productImageDTO = new ProductImageDTO();

            if (productEntity.isEmpty()) {
                baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
                baseResponse.setMessage("Product not exits in system");
                return baseResponse;
            }

            //Lưu ảnh vào trong database
            ProductImageEntity fileData = modelMapper.map(productImageDTO, ProductImageEntity.class);
<<<<<<< HEAD
            fileData.setName(file.getOriginalFilename());
=======
            fileData.setName(fileName);
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
            fileData.setType(file.getContentType());
            fileData.setFilePath(filePath);
            fileData.setProductEntity(productEntity.get());
            storageRepository.save(fileData);
            file.transferTo(new File(filePath)); //Lưu trữ ảnh vào trong folder

            if (fileData != null) {
                uploadFile = true;
            }

            if (!uploadFile) {
                baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
                baseResponse.setMessage("Upload failed");
                return baseResponse;
            }
        }
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage("Upload successfully");
        return baseResponse;
    }
}

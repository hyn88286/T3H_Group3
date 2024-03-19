package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.ProductImageEntity;
import com.t3h.group3_petshop.model.dto.ProductImageDTO;
import com.t3h.group3_petshop.model.request.ProductImageRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.ImageRepository;
import com.t3h.group3_petshop.service.IImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseResponse<ProductImageDTO> findByProductId(ProductImageRequest productImageRequest) {
        ProductImageEntity productImageEntity = imageRepository.findFirstImageByProductId(productImageRequest);
        BaseResponse<ProductImageDTO> baseResponse = new BaseResponse<>();
        if (productImageEntity == null) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Loading image failed");
        }else {
            ProductImageDTO productImageDTO = modelMapper.map(productImageEntity, ProductImageDTO.class);
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage("Loading image successfully");
            baseResponse.setData(productImageDTO);
        }

        return baseResponse;
    }
}

package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.ProductImageDTO;
import com.t3h.group3_petshop.model.request.ProductImageRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;

public interface IImageService {
    BaseResponse<ProductImageDTO> findByProductId(ProductImageRequest productImageRequest);
}

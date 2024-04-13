package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;

public interface IProductService {
    BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size);

    BaseResponse<?> createProduct(ProductDTO productDTO);

    BaseResponse<ProductDTO> getProductBy(ProductFilterRequest filterRequest);
    BaseResponse<?> deleteProduct(Long productId);
}

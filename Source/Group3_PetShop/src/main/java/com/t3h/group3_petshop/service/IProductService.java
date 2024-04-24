package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface IProductService {
    BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size);

    BaseResponse<?> updateProduct(String code,ProductDTO productDTO);

    BaseResponse<?> createProduct(ProductDTO productDTO);

    BaseResponse<ProductDTO> getProductBy(ProductFilterRequest filterRequest) throws Exception;

    BaseResponse<?> deleteProduct(Long id);
}

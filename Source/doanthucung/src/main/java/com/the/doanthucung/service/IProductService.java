package com.the.doanthucung.service;

import com.the.doanthucung.model.dto.ProductDTO;
import com.the.doanthucung.model.request.ProductFilterRequest;
import com.the.doanthucung.model.response.BaseResponse;
import org.springframework.data.domain.Page;

public interface IProductService  {

    BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size);

    BaseResponse<?> createProduct(ProductDTO productDTO);
}

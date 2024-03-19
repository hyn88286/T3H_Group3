package com.t3h.group3_petshop.service;

<<<<<<< HEAD
import com.t3h.group3_petshop.entity.ProductEntity;
=======
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;

public interface IProductService {
    BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size);

    BaseResponse<?> createProduct(ProductDTO productDTO);
}

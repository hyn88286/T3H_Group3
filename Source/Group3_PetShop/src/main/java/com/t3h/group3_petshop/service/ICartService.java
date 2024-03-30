package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.CartDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.CartFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;

public interface ICartService {
    BaseResponse<Page<CartDTO>> getAll(int page, int size);

    BaseResponse<?> addToCart(CartDTO cartDTO);

    BaseResponse<Long> countCart();

}

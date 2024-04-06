package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.CategoryDTO;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.request.CategoryFilterRequest;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    BaseResponse<Page<CategoryDTO>> getAll(CategoryFilterRequest filterRequest, int page, int size);

    CategoryEntity create(CategoryEntity category);
    CategoryEntity findById(Long id);
    BaseResponse update(Long id , CategoryEntity category);

    BaseResponse<?> delete(Long id);
}

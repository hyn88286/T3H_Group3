package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.CategoryDTO;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.request.CategoryFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CategoryRepository;
import com.t3h.group3_petshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BaseResponse<Page<CategoryDTO>> getAll(CategoryFilterRequest categoryFilterRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<CategoryEntity> categoryEntities = categoryRepository.findAllByFilter(categoryFilterRequest,pageable);
        List<CategoryDTO> categoryDTOS = categoryEntities.getContent().stream().map(categoryEntity -> {
            CategoryDTO categoryDTO = modelMapper.map(categoryEntities,CategoryDTO.class);
            categoryDTO.setName(categoryEntity.getName());
            categoryDTO.setId(categoryEntity.getId());
            categoryDTO.setCode(categoryEntity.getCode());
            categoryDTO.setProductId(categoryDTO.getProductId());
            return categoryDTO;
        }).collect(Collectors.toList());

        Page<CategoryDTO> pageData = new PageImpl(categoryDTOS,pageable,categoryEntities.getTotalElements());
        BaseResponse<Page<CategoryDTO>> response = new BaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("success");
        response.setData(pageData);
        return response;
    }

    @Override
    public CategoryEntity create(CategoryEntity category) {
        this.categoryRepository.save(category);
        return category;

    }

    //  hàm tìm kiếm
    @Override
    public CategoryEntity findById(Long id) {
        return this.categoryRepository.findById(id).get();
    }



//    @Override
//    public BaseResponse<?> update(Long id ,UserEntity user) {
//        BaseResponse<?> baseResponse = new BaseResponse<>();
//        Optional<UserEntity> userEntity = userRepository.findById(id);
//        userEntity.get().setUsername(user.getUsername());
//        userEntity.get().setFullName(user.getFullName());
//        userEntity.get().setEmail(user.getEmail());
//        userEntity.get().setPhone(user.getPhone());
//        userRepository.save(userEntity.get());
//        return baseResponse;
//    }

    @Override
    public BaseResponse<?> update(Long id ,CategoryEntity category) {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        categoryEntity.get().setName(category.getName());
        categoryEntity.get().setCode(category.getCode());

        categoryRepository.save(categoryEntity.get());
        return baseResponse;
    }


    @Override
    public BaseResponse<?> delete(Long id) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(id);
        if (optionalCategoryEntity.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("not categorry");
            return baseResponse;
        }
        CategoryEntity user = optionalCategoryEntity.get();
        user.setDeleted(true);
        categoryRepository.save(user);

        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage("categorry delete succerfull");
        return baseResponse;
    }



}

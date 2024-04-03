package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.CategoryEntity;
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
            categoryDTO.setCode(categoryDTO.getCode());
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



    @Override
    public CategoryEntity update(CategoryEntity category) {

            this.categoryRepository.save(category);
            return category;

    }

//    @Override
//    public Boolean delete(Long id) {
//        try {
//            this.categoryRepository.delete(findById(id));
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return false;
//    }

    @Override
    public Boolean delete(Long id) {
        try {
            Optional<CategoryEntity> categoryOptional = this.categoryRepository.findById(id);// tìm kiếm id
            if (categoryOptional.isPresent()) {
                CategoryEntity category = categoryOptional.get();
                category.setDeleted(true); // Đánh dấu đối tượng là đã xóa
                this.categoryRepository.save(category); // Lưu lại thay đổi vào cơ sở dữ liệu
                return true;
            } else {
                // Xử lý trường hợp không tìm thấy đối tượng với ID cụ thể
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

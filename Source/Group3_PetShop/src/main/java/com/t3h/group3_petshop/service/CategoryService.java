package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryEntity> getAll();
    CategoryEntity create(CategoryEntity category);
    CategoryEntity findById(Long id);
    CategoryEntity update(CategoryEntity category);
    Boolean delete(Long id);
}

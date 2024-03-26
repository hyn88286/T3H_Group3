package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.repository.CategoryRepository;
import com.t3h.group3_petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Boolean create(CategoryEntity category) {

        try {
            this.categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //  hàm tìm kiếm
    @Override
    public CategoryEntity findById(Long id) {
        return this.categoryRepository.findById(id).get();
    }



    @Override
    public Boolean update(CategoryEntity category) {
        try {
            this.categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public Boolean delete(Long id) {
        try {
            this.categoryRepository.delete(findById(id));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}

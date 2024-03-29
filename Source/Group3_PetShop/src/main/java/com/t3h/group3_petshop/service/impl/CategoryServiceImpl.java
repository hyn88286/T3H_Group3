package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.repository.CategoryRepository;
import com.t3h.group3_petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getAll() {
        return this.categoryRepository.findAll();
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

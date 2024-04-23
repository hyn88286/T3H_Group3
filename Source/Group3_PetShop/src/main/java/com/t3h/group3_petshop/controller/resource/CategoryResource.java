package com.t3h.group3_petshop.controller.resource;
import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.model.dto.CategoryDTO;
import com.t3h.group3_petshop.model.request.CategoryFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<?> addcread(@RequestBody CategoryEntity category){
        return ResponseEntity.ok(categoryService.create(category));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody CategoryEntity categoryEntity) {
        try {
            return ResponseEntity.ok(categoryService.update(id, categoryEntity));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam("id") long id) {

        return ResponseEntity.ok(categoryService.delete(id ));
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<CategoryDTO>>> getAll(@RequestBody CategoryFilterRequest filterRequest,
                                                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                  @RequestParam(name = "size", required = false, defaultValue = "10") int size){

        return ResponseEntity.ok(categoryService.getAll(filterRequest,page,size));
    }
}

package com.t3h.group3_petshop.controller.resource;
import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
public class CategoryResource {
    @Autowired
    private  CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<?> addcread(@RequestBody CategoryEntity category){
        return ResponseEntity.ok(categoryService.create(category));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") long id) {
        return ResponseEntity.ok(categoryService.delete(id ));
    }
}

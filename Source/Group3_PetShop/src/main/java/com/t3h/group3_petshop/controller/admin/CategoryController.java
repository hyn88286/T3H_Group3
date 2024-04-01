package com.t3h.group3_petshop.controller.admin;


import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    public String index(Model model){
        List<CategoryEntity> list = this.categoryService.getAll();
        model.addAttribute("list",list);
        return "admin/category/index";
    }
    @GetMapping("/add-category")
    public String add(Model model){;
        CategoryEntity category =new CategoryEntity();
        category.setCategoryStatus(true);
        model.addAttribute("category",category);
//        this.categoryService.create(category);
        return "admin/category/add";
    }


    @PostMapping("/add-category")
    public String save(@ModelAttribute("category") CategoryEntity category){
        if (this.categoryService.create(category)) {
            return "redirect:/admin/category";

        }else {
            return "admin/category/add-category";
        }
    }

    @GetMapping("/edit-category/{id}")
    public String edit(Model model,@PathVariable("id") Long id ){
        CategoryEntity category = this.categoryService.findById(id);
        model.addAttribute("category",category);
        return "admin/category/edit";
    }

    @PostMapping("/edit-category")
    public String Update(@ModelAttribute("category") CategoryEntity category){
        if (this.categoryService.create(category)) {
            return "redirect:/admin/category";

        }else {
            return "admin/category/add-category";
        }
    }

    @GetMapping("/delete-category/{id}")
    public String delete(@PathVariable("id") Long id ){
        if (this.categoryService.delete(id)) {
            return "redirect:/admin/category";

        }else {
            return "redirect:/admin/category";
        }
    }
}

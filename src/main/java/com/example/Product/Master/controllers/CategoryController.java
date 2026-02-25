package com.example.Product.Master.controllers;

import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.services.CategoryService;
import com.example.Product.Master.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@Valid @RequestBody CategoryEntity category){
        return ResponseEntity.status(201).body(categoryService.createCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryEntity updatedCategory){
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, updatedCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

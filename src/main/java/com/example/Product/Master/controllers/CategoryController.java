package com.example.Product.Master.controllers;

import com.example.Product.Master.dto.CategoryRequestDTO;
import com.example.Product.Master.dto.CategoryResponseDTO;
import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.services.CategoryService;
import com.example.Product.Master.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequestDTO category){
        categoryService.createAndUpdateCategory(category);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@Valid @RequestBody CategoryRequestDTO category){
        categoryService.createAndUpdateCategory(category);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

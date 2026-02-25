package com.example.Product.Master.controllers;

import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.services.CategoryService;
import com.example.Product.Master.services.SubCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService){
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    public ResponseEntity<SubCategoryEntity> createCategory(@Valid @RequestBody SubCategoryEntity subCategory){
        return ResponseEntity.status(201).body(subCategoryService.createSubCategory(subCategory));
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryEntity>> getAllSubCategories(){
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }

    @PutMapping("/{subCategoryId}")
    public ResponseEntity<SubCategoryEntity> updateSubCategory(@PathVariable Long subCategoryId, @Valid @RequestBody SubCategoryEntity updatedSubCategory){
        return ResponseEntity.ok(subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory));
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long subCategoryId){
        subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.noContent().build();
    }
}

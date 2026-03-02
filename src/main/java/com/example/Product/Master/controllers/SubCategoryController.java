package com.example.Product.Master.controllers;

import com.example.Product.Master.dto.SubCategoryRequestDTO;
import com.example.Product.Master.dto.SubCategoryResponseDTO;
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
    public ResponseEntity<Void> createSubCategory(@Valid @RequestBody SubCategoryRequestDTO subCategory){
        subCategoryService.createAndUpdateSubCategory(subCategory);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryResponseDTO>> getAllSubCategories(){
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<SubCategoryResponseDTO>> getSubCategoriesByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.ok(subCategoryService.getSubCategoriesByCategoryId(categoryId));
    }

    @PutMapping
    public ResponseEntity<Void> updateSubCategory(@Valid @RequestBody SubCategoryRequestDTO updatedSubCategory){
        subCategoryService.createAndUpdateSubCategory(updatedSubCategory);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long subCategoryId){
        subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.noContent().build();
    }
}

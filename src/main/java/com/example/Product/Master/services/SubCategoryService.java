package com.example.Product.Master.services;

import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.exception.ResourceNotFoundException;
import com.example.Product.Master.repository.CategoryRepository;
import com.example.Product.Master.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public SubCategoryEntity createSubCategory(Long categoryId, SubCategoryEntity subCategory){
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find Category with Id: " + categoryId));
        subCategory.setCategory(category);

        if(subCategory.getProductList() != null){
            for (ProductEntity product : subCategory.getProductList()){
                product.setSubCategory(subCategory);
            }
        }

        return subCategoryRepository.save(subCategory);
    }

    public List<SubCategoryEntity> getAllSubCategories(){
        return subCategoryRepository.findAll();
    }

    public SubCategoryEntity updateSubCategory(Long subCategoryId, SubCategoryEntity updatedSubCategory){

        SubCategoryEntity existingSubCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subCategoryId));

        existingSubCategory.setSubCategoryName(updatedSubCategory.getSubCategoryName());
        existingSubCategory.setDescription(updatedSubCategory.getDescription());
        existingSubCategory.setActive(updatedSubCategory.isActive());

        return subCategoryRepository.save(existingSubCategory);
    }

    public void deleteSubCategory(Long subCategoryId){

        SubCategoryEntity existingSubCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subCategoryId));
        subCategoryRepository.delete(existingSubCategory);
    }
}

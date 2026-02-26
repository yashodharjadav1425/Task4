package com.example.Product.Master.services;

import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.exception.ResourceNotFoundException;
import com.example.Product.Master.repository.CategoryRepository;
import com.example.Product.Master.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity createCategory(CategoryEntity category){

        if(category.getSubCategoryList() != null){
            for (SubCategoryEntity subCategory : category.getSubCategoryList()){
                subCategory.setCategory(category);
            }
        }

        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }

    public CategoryEntity updateCategory(Long categoryId, CategoryEntity updatedCategory){
        CategoryEntity existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setDescription(updatedCategory.getDescription());
        existingCategory.setActive(updatedCategory.isActive());

        return categoryRepository.save(existingCategory);
    }


    public void deleteCategory(Long categoryId){

        CategoryEntity existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        categoryRepository.delete(existingCategory);
    }

}

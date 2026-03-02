package com.example.Product.Master.services;

import com.example.Product.Master.dto.CategoryRequestDTO;
import com.example.Product.Master.dto.CategoryResponseDTO;
import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.exception.ResourceNotFoundException;
import com.example.Product.Master.repository.CategoryRepository;
import com.example.Product.Master.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createAndUpdateCategory(CategoryRequestDTO categoryRequestDTO){

        if(categoryRequestDTO.getCategoryId() == null){

            CategoryEntity categoryEntity = new CategoryEntity();

            categoryEntity.setCategoryName(categoryRequestDTO.getCategoryName());
            categoryEntity.setDescription(categoryRequestDTO.getDescription());
            categoryEntity.setActive(true);

            categoryRepository.save(categoryEntity);
        }else{
            CategoryEntity existCategory = categoryRepository.findById(categoryRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryRequestDTO.getCategoryId()));

            existCategory.setCategoryName(categoryRequestDTO.getCategoryName());
            existCategory.setDescription(categoryRequestDTO.getDescription());
            existCategory.setActive(categoryRequestDTO.getIsActive());

            categoryRepository.save(existCategory);
        }
    }

    public List<CategoryResponseDTO> getAllCategories(){

        List<CategoryResponseDTO> categoryResponseDTO = new ArrayList<>();

        for (CategoryEntity category : categoryRepository.findAll()){

            CategoryResponseDTO categoryResponse = new CategoryResponseDTO();

            categoryResponse.setCategoryId(category.getCategoryId());
            categoryResponse.setCategoryName(category.getCategoryName());
            categoryResponse.setDescription(category.getDescription());
            categoryResponse.setIsActive(category.isActive());

            categoryResponseDTO.add(categoryResponse);
        }

        return categoryResponseDTO;
    }

//    public CategoryEntity updateCategory(Long categoryId, CategoryEntity updatedCategory){
//        CategoryEntity existingCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
//
//        existingCategory.setCategoryName(updatedCategory.getCategoryName());
//        existingCategory.setDescription(updatedCategory.getDescription());
//        existingCategory.setActive(updatedCategory.isActive());
//
//        return categoryRepository.save(existingCategory);
//    }


    public void deleteCategory(Long categoryId){

        CategoryEntity existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        categoryRepository.delete(existingCategory);
    }

}

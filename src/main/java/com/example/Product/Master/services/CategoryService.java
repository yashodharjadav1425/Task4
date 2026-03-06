package com.example.Product.Master.services;

import com.example.Product.Master.dto.CategoryRequestDTO;
import com.example.Product.Master.dto.CategoryResponseDTO;
import com.example.Product.Master.entity.CategoryEntity;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.exception.ResourceNotFoundException;
import com.example.Product.Master.repository.CategoryRepository;
import com.example.Product.Master.repository.ProductRepository;
import com.example.Product.Master.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
    }

    public void createAndUpdateCategory(CategoryRequestDTO categoryRequestDTO){

        String categoryName = categoryRequestDTO.getCategoryName().trim();

        if(categoryRequestDTO.getCategoryId() == null){

            if (categoryRepository.existsByCategoryNameIgnoreCase(categoryName)){
                throw new RuntimeException("Category already present");
            }

            CategoryEntity categoryEntity = new CategoryEntity();

            categoryEntity.setCategoryName(categoryRequestDTO.getCategoryName());
            categoryEntity.setDescription(categoryRequestDTO.getDescription());
            categoryEntity.setIsDeleted(1);
            categoryEntity.setActive(true);

            categoryRepository.save(categoryEntity);
        }else{
            CategoryEntity existCategory = categoryRepository.findById(categoryRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryRequestDTO.getCategoryId()));

            if(categoryRepository.existsByCategoryNameIgnoreCase(categoryName)
                    && !existCategory.getCategoryName().equalsIgnoreCase(categoryName)){
                throw new RuntimeException("Category already present");
            }

            existCategory.setCategoryName(categoryRequestDTO.getCategoryName());
            existCategory.setDescription(categoryRequestDTO.getDescription());
            existCategory.setActive(categoryRequestDTO.getIsActive());
            existCategory.setIsDeleted(1);

            categoryRepository.save(existCategory);
        }
    }

    public List<CategoryResponseDTO> getAllCategories(){

        List<CategoryResponseDTO> categoryResponseDTO = new ArrayList<>();

        for (CategoryEntity category : categoryRepository.findAll()){

            if(category.getIsDeleted() != 9){
                CategoryResponseDTO categoryResponse = new CategoryResponseDTO();

                categoryResponse.setCategoryId(category.getCategoryId());
                categoryResponse.setCategoryName(category.getCategoryName());
                categoryResponse.setDescription(category.getDescription());
                categoryResponse.setIsActive(category.isActive());

                categoryResponseDTO.add(categoryResponse);
            }
        }

        return categoryResponseDTO;
    }


    public void deleteCategory(Long categoryId){

        CategoryEntity existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        boolean subCategoryExists = subCategoryRepository.existsByCategory(categoryId);
        boolean productExists = productRepository.existsByCategory(categoryId);

        if (subCategoryExists || productExists) {
            throw new RuntimeException("Category cannot be deleted because it contains subcategories or products");
        }

        existingCategory.setIsDeleted(9);

        categoryRepository.save(existingCategory);
    }

}

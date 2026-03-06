package com.example.Product.Master.services;

import com.example.Product.Master.dto.CategoryRequestDTO;
import com.example.Product.Master.dto.CategoryResponseDTO;
import com.example.Product.Master.dto.SubCategoryRequestDTO;
import com.example.Product.Master.dto.SubCategoryResponseDTO;
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
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void createAndUpdateSubCategory(SubCategoryRequestDTO subCategoryRequestDTO){

        String subCategoryName = subCategoryRequestDTO.getSubCategoryName().trim();

        if(subCategoryRequestDTO.getSubCategoryId() == null){

            if (subCategoryRepository.existsBySubCategoryNameIgnoreCase(subCategoryName)){
                throw new RuntimeException("Subcategory already present");
            }

            SubCategoryEntity subCategoryEntity = new SubCategoryEntity();

            subCategoryEntity.setCategory(subCategoryRequestDTO.getCategory());
            subCategoryEntity.setSubCategoryName(subCategoryRequestDTO.getSubCategoryName());
            subCategoryEntity.setDescription(subCategoryRequestDTO.getDescription());
            subCategoryEntity.setActive(true);
            subCategoryEntity.setIsDeleted(1);


            subCategoryRepository.save(subCategoryEntity);
        }else{
            SubCategoryEntity existSubCategory = subCategoryRepository.findById(subCategoryRequestDTO.getSubCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subCategoryRequestDTO.getSubCategoryId()));

            if(subCategoryRepository.existsBySubCategoryNameIgnoreCase(subCategoryName)
                    && !existSubCategory.getSubCategoryName().equalsIgnoreCase(subCategoryName)){
                throw new RuntimeException("Subcategory already present");
            }

            existSubCategory.setCategory(subCategoryRequestDTO.getCategory());
            existSubCategory.setSubCategoryName(subCategoryRequestDTO.getSubCategoryName());
            existSubCategory.setDescription(subCategoryRequestDTO.getDescription());
            existSubCategory.setActive(subCategoryRequestDTO.isActive());
            existSubCategory.setIsDeleted(1);

            subCategoryRepository.save(existSubCategory);
        }
    }

    public List<SubCategoryResponseDTO> getAllSubCategories(){

        List<SubCategoryResponseDTO> subCategoryResponseDTO = new ArrayList<>();

        for (SubCategoryEntity subCategory : subCategoryRepository.findAll()){

            if (subCategory.getIsDeleted() != 9){
                SubCategoryResponseDTO subCategoryResponse = new SubCategoryResponseDTO();

                subCategoryResponse.setActive(subCategory.isActive());
                subCategoryResponse.setSubCategoryId(subCategory.getSubCategoryId());

                CategoryEntity category = categoryRepository.findById(subCategory.getCategory())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                subCategoryResponse.setCategoryId(subCategory.getCategory());
                subCategoryResponse.setCategoryName(category.getCategoryName());
                subCategoryResponse.setSubCategoryName(subCategory.getSubCategoryName());
                subCategoryResponse.setDescription(subCategory.getDescription());

                subCategoryResponseDTO.add(subCategoryResponse);
            }
        }

        return subCategoryResponseDTO;
    }

    public List<SubCategoryResponseDTO> getSubCategoriesByCategoryId(Long categoryId) {

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        List<SubCategoryEntity> subCategories = subCategoryRepository.findByCategory(categoryId);

        List<SubCategoryResponseDTO> subCategoryResponseDTOList = new ArrayList<>();
        for (SubCategoryEntity sub : subCategories) {
            if(sub.getIsDeleted() != 9){
                SubCategoryResponseDTO subCategoryResponseDTO = new SubCategoryResponseDTO();
                subCategoryResponseDTO.setSubCategoryId(sub.getSubCategoryId());
                subCategoryResponseDTO.setSubCategoryName(sub.getSubCategoryName());
                subCategoryResponseDTO.setDescription(sub.getDescription());
                subCategoryResponseDTO.setActive(sub.isActive());
                subCategoryResponseDTO.setCategoryId(category.getCategoryId());
                subCategoryResponseDTO.setCategoryName(category.getCategoryName());
                subCategoryResponseDTOList.add(subCategoryResponseDTO);
            }
        }

        return subCategoryResponseDTOList;
    }

    public void deleteSubCategory(Long subCategoryId){

        SubCategoryEntity existingSubCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subCategoryId));

        boolean subCategoryExists = productRepository.existsBySubCategory(subCategoryId);

        if(subCategoryExists){
            throw new RuntimeException("Subcategory cannot be deleted because it contains products");
        }

        existingSubCategory.setIsDeleted(9);
        subCategoryRepository.save(existingSubCategory);
    }
}

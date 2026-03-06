package com.example.Product.Master.dto;

import com.example.Product.Master.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponseDTO {

    private boolean isActive;

    private Long subCategoryId;

    private Long categoryId;

    private String categoryName;

    private String subCategoryName;

    private String description;


}

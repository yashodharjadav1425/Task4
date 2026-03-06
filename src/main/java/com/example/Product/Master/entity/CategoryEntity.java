package com.example.Product.Master.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    @Size(max = 30, message = "Category name must has length less than 30 characters.")
    @Column(unique = true, nullable = false)
    private String categoryName;

    @NotBlank(message = "Description is not null or empty")
    @Size(max = 255, message = "Description size must be less than 255 characters.")
    private String description;

    private boolean isActive;

    private Integer isDeleted;

}

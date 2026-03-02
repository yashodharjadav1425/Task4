package com.example.Product.Master.entity;

import com.example.Product.Master.services.CategoryService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Setter
    @Getter
    @NotBlank(message = "Product name should not be blank")
    private String productName;

    @Setter
    @Getter
    @NotBlank(message = "Product description should not be blank")
    private String description;

    @Setter
    @Getter
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price format is invalid")
    private BigDecimal price;

    @Setter
    @Getter
    @NotNull(message = "Manufacture date is required")
    @Past(message = "Manufacture date must be in the past")
    private LocalDate manufactureDate;

    @NotNull(message = "Product serial number is required")
    private Long productSerialNumber;

    @NotBlank(message = "Warranty & Support is required")
    @Size(max = 255, message = "Warranty & Support cannot exceed 255 characters")
    private String warrantyAndSupport;

    @NotNull(message = "Product condition is required")
    private String productCondition;

    @NotBlank(message = "Product color is required")
    private String productColor;

    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot exceed 100%")
    private double discount;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "From date cannot be in the future")
    private LocalDate fromDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "To date must be today or in the future")
    private LocalDate toDate;

    private boolean isActive;

    @NotNull(message = "Category is not null")
    private Long category;

    @NotNull(message = "Subcategory is not null")
    private Long subCategory;

}

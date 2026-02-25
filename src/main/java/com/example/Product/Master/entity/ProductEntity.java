package com.example.Product.Master.entity;

import com.example.Product.Master.services.CategoryService;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product name should not be blank")
    private String productName;

    @NotBlank(message = "Product description should not be blank")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price format is invalid")
    private BigDecimal price;

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

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "subCategoryId")
    private SubCategoryEntity subCategory;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public SubCategoryEntity getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategoryEntity subCategory) {
        this.subCategory = subCategory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public long getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(long productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public String getWarrantyAndSupport() {
        return warrantyAndSupport;
    }

    public void setWarrantyAndSupport(String warrantyAndSupport) {
        this.warrantyAndSupport = warrantyAndSupport;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

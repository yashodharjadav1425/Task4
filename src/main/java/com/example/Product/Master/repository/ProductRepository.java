package com.example.Product.Master.repository;

import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByCategory(Long categoryId);
    boolean existsBySubCategory(Long subCategoryId);
}

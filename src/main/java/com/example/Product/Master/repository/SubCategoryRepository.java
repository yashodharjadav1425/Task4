package com.example.Product.Master.repository;

import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.services.SubCategoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {
    List<SubCategoryEntity> findByCategory(Long categoryId);
}

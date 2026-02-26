package com.example.Product.Master.services;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.exception.ResourceNotFoundException;
import com.example.Product.Master.repository.ProductRepository;
import com.example.Product.Master.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ProductService(ProductRepository productRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public ProductEntity createProduct(Long subCategoryId, ProductEntity product){

        SubCategoryEntity subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find Category with Id: " + subCategoryId));
        product.setSubCategory(subCategory);
        return productRepository.save(product);
    }

    public List<ProductEntity> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductEntity updateProduct(Long productId, ProductEntity updatedProduct){
        ProductEntity existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setSubCategory(updatedProduct.getSubCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setManufactureDate(updatedProduct.getManufactureDate());
        existingProduct.setProductSerialNumber(updatedProduct.getProductSerialNumber());
        existingProduct.setWarrantyAndSupport(updatedProduct.getWarrantyAndSupport());
        existingProduct.setProductCondition(updatedProduct.getProductCondition());
        existingProduct.setProductColor(updatedProduct.getProductColor());
        existingProduct.setDiscount(updatedProduct.getDiscount());
        existingProduct.setFromDate(updatedProduct.getFromDate());
        existingProduct.setToDate(updatedProduct.getToDate());
        existingProduct.setActive(updatedProduct.isActive());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id){

        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }
}

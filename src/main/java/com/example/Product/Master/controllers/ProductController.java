package com.example.Product.Master.controllers;


import com.example.Product.Master.dto.ProductRequestDTO;
import com.example.Product.Master.dto.ProductResponseDTO;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequestDTO product){
        productService.createAndUpdateProduct(product);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductRequestDTO updatedProduct){
        productService.createAndUpdateProduct(updatedProduct);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

package com.example.Product.Master.services;
import com.example.Product.Master.dto.ProductRequestDTO;
import com.example.Product.Master.dto.ProductResponseDTO;
import com.example.Product.Master.dto.SubCategoryRequestDTO;
import com.example.Product.Master.dto.SubCategoryResponseDTO;
import com.example.Product.Master.entity.ProductEntity;
import com.example.Product.Master.entity.SubCategoryEntity;
import com.example.Product.Master.exception.ResourceNotFoundException;
import com.example.Product.Master.repository.ProductRepository;
import com.example.Product.Master.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ProductService(ProductRepository productRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public void createAndUpdateProduct(ProductRequestDTO productRequestDTO){

        if(productRequestDTO.getProductId() == null){

            ProductEntity productEntity = new ProductEntity();

            productEntity.setProductName(productRequestDTO.getProductName());
            productEntity.setDescription(productRequestDTO.getDescription());
            productEntity.setPrice(productRequestDTO.getPrice());

            productEntity.setManufactureDate(productRequestDTO.getManufactureDate());
            productEntity.setProductSerialNumber(productRequestDTO.getProductSerialNumber());
            productEntity.setWarrantyAndSupport(productRequestDTO.getWarrantyAndSupport());
            productEntity.setProductCondition(productRequestDTO.getProductCondition());
            productEntity.setProductColor(productRequestDTO.getProductColor());
            productEntity.setDiscount(productRequestDTO.getDiscount());
            productEntity.setFromDate(productRequestDTO.getFromDate());
            productEntity.setToDate(productRequestDTO.getToDate());
            productEntity.setCategory(productRequestDTO.getCategory());
            productEntity.setSubCategory(productRequestDTO.getSubCategory());

            productEntity.setActive(true);


            productRepository.save(productEntity);
        }else{
            ProductEntity existProduct = productRepository.findById(productRequestDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productRequestDTO.getProductId()));

            existProduct.setProductName(productRequestDTO.getProductName());
            existProduct.setDescription(productRequestDTO.getDescription());
            existProduct.setPrice(productRequestDTO.getPrice());
            existProduct.setManufactureDate(productRequestDTO.getManufactureDate());
            existProduct.setProductSerialNumber(productRequestDTO.getProductSerialNumber());
            existProduct.setWarrantyAndSupport(productRequestDTO.getWarrantyAndSupport());
            existProduct.setProductCondition(productRequestDTO.getProductCondition());
            existProduct.setProductColor(productRequestDTO.getProductColor());
            existProduct.setDiscount(productRequestDTO.getDiscount());
            existProduct.setFromDate(productRequestDTO.getFromDate());
            existProduct.setToDate(productRequestDTO.getToDate());
            existProduct.setCategory(productRequestDTO.getCategory());
            existProduct.setSubCategory(productRequestDTO.getSubCategory());
            existProduct.setActive(productRequestDTO.isActive());


            productRepository.save(existProduct);
        }
    }

    public List<ProductResponseDTO> getAllProducts(){

        List<ProductResponseDTO> productResponseDTO = new ArrayList<>();

        for (ProductEntity product : productRepository.findAll()){

            ProductResponseDTO productResponse = new ProductResponseDTO();

            productResponse.setActive(product.isActive());
            productResponse.setProductId(product.getProductId());
            productResponse.setProductName(product.getProductName());
            productResponse.setDescription(product.getDescription());
            productResponse.setCategory(product.getCategory());
            productResponse.setPrice(product.getPrice());
            productResponse.setDiscount(product.getDiscount());

            productResponseDTO.add(productResponse);
        }

        return productResponseDTO;
    }


    public void deleteProduct(Long id){

        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        productRepository.delete(existingProduct);
    }
}

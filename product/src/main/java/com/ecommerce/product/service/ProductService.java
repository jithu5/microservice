package com.ecommerce.product.service;


import com.ecommerce.product.dto.ProductReqDto;
import com.ecommerce.product.dto.ProductResDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepo productRepo;

    private ProductResDto mapProductToProductResDto(Product product){
        ProductResDto productResDto = new ProductResDto();
        productResDto.setId(product.getId());
        productResDto.setName(product.getName());
        productResDto.setPrice(product.getPrice());
        productResDto.setQuantity(product.getQuantity());
        productResDto.setCategory(product.getCategory());
        productResDto.setImageUrl(product.getImageUrl());
        productResDto.setDescription(product.getDescription());
        productResDto.setActive(product.isActive());
        productResDto.setCreatedAt(product.getCreatedAt());
        productResDto.setUpdatedAt(product.getUpdatedAt());
        return productResDto;
    }

    private void updateProduct(Product product, ProductReqDto productReqDto) {
        product.setPrice(productReqDto.getPrice());
        product.setQuantity(productReqDto.getQuantity());
        product.setCategory(productReqDto.getCategory());
        product.setImageUrl(productReqDto.getImageUrl());
        product.setDescription(productReqDto.getDescription());
        product.setActive(product.isActive());
        product.setName(productReqDto.getName());
    }

    public void createNewProduct(ProductReqDto product) {
        Product  newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setCategory(product.getCategory());
        newProduct.setDescription(product.getDescription());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setImageUrl(product.getImageUrl());

        productRepo.save(newProduct);
    }

    public Optional<ProductResDto> getProductById(Long id) {
        return productRepo.findById(id).map(this::mapProductToProductResDto);
    }

    public List<ProductResDto> getProducts(){
        return productRepo.findAll().stream()
                .map(this::mapProductToProductResDto)
                .collect(Collectors.toList());
    }

    public boolean updateProductById(Long id, ProductReqDto product) {
        return productRepo.findById(id).map(product1 -> {
            updateProduct(product1, product);
            productRepo.save(product1);
            return true;
        }).orElse(false);

    }

    public void deleteProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Product not found with id " + id)
                );
        productRepo.delete(product);
    }

}

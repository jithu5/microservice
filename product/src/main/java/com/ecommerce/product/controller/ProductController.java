package com.ecommerce.product.controller;


import com.ecommerce.product.dto.ProductReqDto;
import com.ecommerce.product.dto.ProductResDto;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductReqDto product) {
            productService.createNewProduct(product);
            return ResponseEntity.ok().body("Product created successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductResDto>> getAllProducts() {
        List<ProductResDto> productList = productService.getProducts();
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/{id}")
    public ProductResDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id).map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build()).getBody();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody ProductReqDto product) {
        boolean isUpdated = productService.updateProductById(id,product);
        if (isUpdated) {
            return ResponseEntity.ok().body("Product updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok().body("Product deleted successfully");
        } catch (Exception e) {
            return  ResponseEntity.notFound().build();
        }
    }
}

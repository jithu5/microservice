package com.ecommerce.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductReqDto {
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String imageUrl;

}

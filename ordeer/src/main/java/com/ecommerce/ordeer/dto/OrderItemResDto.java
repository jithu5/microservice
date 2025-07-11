package com.ecommerce.ordeer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}

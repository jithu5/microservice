package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemReqDto {
    private Long productId;

    private int quantity;
    private BigDecimal price;
}

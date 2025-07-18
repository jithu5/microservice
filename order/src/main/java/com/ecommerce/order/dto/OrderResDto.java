package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResDto {
    private Long id;
    private BigDecimal totalAmount;
    private OrderResDto status;
    private List<OrderItemResDto> items;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}

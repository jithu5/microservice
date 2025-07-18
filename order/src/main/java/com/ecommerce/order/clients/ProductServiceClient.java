package com.ecommerce.order.clients;

import com.ecommerce.order.dto.ProductResDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/products/{productId}")
    Optional<ProductResDto> getProductById(@PathVariable String productId);

}

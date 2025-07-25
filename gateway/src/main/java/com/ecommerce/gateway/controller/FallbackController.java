package com.ecommerce.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FallbackController {

    @GetMapping("/fallback/products")
    public ResponseEntity<List<String>> productFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(List.of("Product Service is not available"));
    }
}

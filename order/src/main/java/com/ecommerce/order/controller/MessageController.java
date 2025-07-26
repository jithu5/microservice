package com.ecommerce.order.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @GetMapping
    @RateLimiter(name = "rateBreaker", fallbackMethod = "fallBackRate")
    public String getMessage() {
        return "Hello World";
    }

    public String fallBackRate() {
        return "Limit reached for ";
    }
}

package com.ecommerce.ordeer.controller;

import com.ecommerce.ordeer.dto.CartItemReqDto;
import com.ecommerce.ordeer.dto.CartItemResDto;
import com.ecommerce.ordeer.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                            @RequestBody CartItemReqDto cartItemReqDto){
        try {
            cartService.addToCart(userId,cartItemReqDto);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID")  String userId,
                                                 @PathVariable("productId") String productId){
        try {
            cartService.deleteItemFromCart(userId,productId);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<?> getCartItems(@RequestHeader("X-User-ID") String userId) {
        try {
            List<CartItemResDto> cartItemResDtos = cartService.findCartByUserId(userId);
            return ResponseEntity.ok().body(cartItemResDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}

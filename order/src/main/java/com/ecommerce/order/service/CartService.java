package com.ecommerce.order.service;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemReqDto;
import com.ecommerce.order.dto.CartItemResDto;
import com.ecommerce.order.dto.ProductResDto;
import com.ecommerce.order.dto.UserResDto;
import com.ecommerce.order.entity.CartItem;
import com.ecommerce.order.repository.CartItemRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartItemRepo cartItemRepo;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    @CircuitBreaker(name = "productService",fallbackMethod = "fallbackAddToCart" )
    public void addToCart(String userId, CartItemReqDto cartItemReqDto) {
         Optional<ProductResDto> productOpt = productServiceClient.getProductById(String.valueOf(cartItemReqDto.getProductId()));

         if (productOpt.isEmpty()) {
             throw new RuntimeException("product not found");
         }

        ProductResDto product = productOpt.get();
         log.info("quantity: {} ", product.getQuantity());
         log.info("quantity: {} ", cartItemReqDto.getQuantity());
        if (product.getQuantity() < cartItemReqDto.getQuantity())
            throw new RuntimeException("product quantity less than quantity");
        Optional<UserResDto> userOpt = userServiceClient.getUserById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        UserResDto user = userOpt.get();
        CartItem existingCartItem = cartItemRepo.findByUserIdAndProductId(userId, String.valueOf(cartItemReqDto.getProductId()));
        if (existingCartItem == null) {
            // create
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(String.valueOf(cartItemReqDto.getProductId()));
            cartItem.setQuantity(cartItemReqDto.getQuantity());
            cartItem.setPrice(cartItemReqDto.getPrice());
            cartItemRepo.save(cartItem);

        }else{
            // update
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemReqDto.getQuantity() );
            existingCartItem.setPrice(BigDecimal.valueOf(100.00));
            cartItemRepo.save(existingCartItem);
        }
    }

    public void fallbackAddToCart(String userId,
                                  CartItemReqDto cartItemReqDto,
                                  Exception exception) {
        log.error("fallbackAddToCart called");
        throw new RuntimeException("fallbackAddToCart called");
    }

    @Transactional
    public void deleteItemFromCart(String userId, String productId) {
//        Optional<Product> productOpt = productRepo.findById(Long.valueOf(productId));
//        if (productOpt.isEmpty()) {
//            throw new RuntimeException("product not found");
//        }
//        Product product = productOpt.get();

//        Optional<User> userOpt = userRepo.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty()) {
//            throw new RuntimeException("user not found");
//        }

            cartItemRepo.deleteByUserIdAndProductId(userId,productId);

    }

    public List<CartItemResDto> findCartByUserId(String userId) {
//        User user = userRepo.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("user not found"));
        Optional<UserResDto> userOpt = userServiceClient.getUserById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        List<CartItem> cartItems = cartItemRepo.findByUserId(userId);
        log.info("cartItems size: {}", cartItems.size());
        return cartItems.stream().map(this::mapCartItemToResDto).toList();
    }

    private CartItemResDto mapCartItemToResDto(CartItem cartItem) {
        CartItemResDto cartItemResDto = new CartItemResDto();
        cartItemResDto.setCreatedAt(cartItem.getCreatedAt());
        cartItemResDto.setUpdatedAt(cartItem.getUpdatedAt());
        cartItemResDto.setPrice(cartItem.getPrice());
        cartItemResDto.setQuantity(cartItem.getQuantity());
        cartItemResDto.setProductId(cartItem.getProductId());

        return cartItemResDto;
    }

    @Transactional
    public void clearCart(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }
}

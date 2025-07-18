package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemResDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final CartService cartService;

    public void createOrder(String userId) {
        List<CartItemResDto> cartItems = cartService.findCartByUserId(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("No carts found");
        }
//        Optional<User> userOptional = userRepo.findById(Long.valueOf(userId));
//
//        if (userOptional.isEmpty()) throw new RuntimeException("user not found");
//
//        User user = userOptional.get();

        // Calculate the price
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItemResDto cartItem : cartItems) {
            BigDecimal itemPrice = cartItem.getPrice() != null ? cartItem.getPrice() : BigDecimal.ZERO;
            totalPrice = totalPrice.add(itemPrice);
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .collect(Collectors.toList());
        order.setItems(orderItems);

        orderRepo.save(order);

        // clear the cart
        cartService.clearCart(userId);
    }
}

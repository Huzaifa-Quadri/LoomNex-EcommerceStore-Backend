package com.huzaifaq.LoomNex_EcommerceStore.Service;

import com.huzaifaq.LoomNex_EcommerceStore.Model.OrderItem;
import com.huzaifaq.LoomNex_EcommerceStore.Model.OrderStatus;
import com.huzaifaq.LoomNex_EcommerceStore.Model.Orders;
import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import com.huzaifaq.LoomNex_EcommerceStore.Model.User;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.OrderRepo;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.ProductRepo;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.UserRepo;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderDTO;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    private final UserRepo userRepository;
    private final ProductRepo productRepository;
    private final OrderRepo orderRepository;

    public OrderService(UserRepo userRepository, ProductRepo productRepository, OrderRepo orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not Found"));

        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemsDTOs = new ArrayList<>();
        double calculatedTotal = 0.0;

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not Found: " + productId));

            if (product.getQuantityLeft() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            // Deduct inventory
            product.setQuantityLeft(product.getQuantityLeft() - quantity);
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setImageUrl(product.getImageUrl());
            orderItem.setCategory(product.getCategory());

            orderItems.add(orderItem);
            orderItemsDTOs.add(new OrderItemDTO(product.getName(), product.getPrice(), quantity));
            
            calculatedTotal += (product.getPrice() * quantity);
        }

        order.setTotalAmount(calculatedTotal);
        order.setOrderItems(orderItems);
        Orders savedOrder = orderRepository.save(order);

        return new OrderDTO(savedOrder.getId(), savedOrder.getTotalAmount(), savedOrder.getStatus(), savedOrder.getOrderDate(), orderItemsDTOs);
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAllOrdersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        List<Orders> orders = orderRepository.findByUserIdWithUsers(userId);
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders orders) {
        List<OrderItemDTO> orderItems = orders.getOrderItems().stream().map(
                orderItem -> new OrderItemDTO(
                        orderItem.getProduct().getName(),
                        orderItem.getProduct().getPrice(),
                        orderItem.getQuantity()))
                .collect(Collectors.toList());

        return new OrderDTO(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getStatus(),
                orders.getOrderDate(),
                orders.getUser() != null ? orders.getUser().getName() : "unknown",
                orders.getUser() != null ? orders.getUser().getEmail() : "unknown",
                orderItems
        );
    }
}
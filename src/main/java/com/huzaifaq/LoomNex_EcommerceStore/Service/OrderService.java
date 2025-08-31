package com.huzaifaq.LoomNex_EcommerceStore.Service;

import com.huzaifaq.LoomNex_EcommerceStore.Model.OrderItem;
import com.huzaifaq.LoomNex_EcommerceStore.Model.Orders;
import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import com.huzaifaq.LoomNex_EcommerceStore.Model.User;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.OrderRepo;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.ProductRepo;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.UserRepo;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderDTO;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private OrderRepo orderRepository;

    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities, double totalAmount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not Found / Not Logged in"));

        //code here
        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("Pending");         //TODO: Make it as ENUM - pending, active, etc ...
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemsDTOs = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()){
            Product product = productRepository.findById(entry.getKey()).orElseThrow(() -> new RuntimeException("Product not Found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue()); // here we are giving quantity from the Map

            //? Now adding all in the list -> As all the items user is gonna purchase
            orderItems.add(orderItem);

            orderItemsDTOs.add(new OrderItemDTO(product.getName(), product.getPrice(), entry.getValue()));
        }

        order.setOrderItems(orderItems);
        Orders saveOrder = orderRepository.save(order);


        return new OrderDTO(saveOrder.getId(), saveOrder.getTotalAmount(), saveOrder.getStatus(), saveOrder.getOrderDate(), orderItemsDTOs);
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAllOrdersWithUsers();

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
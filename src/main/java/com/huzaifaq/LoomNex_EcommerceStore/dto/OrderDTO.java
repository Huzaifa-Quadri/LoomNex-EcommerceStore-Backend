package com.huzaifaq.LoomNex_EcommerceStore.dto;

import com.huzaifaq.LoomNex_EcommerceStore.Model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String userName;
    private String email;
    private List<OrderItemDTO> orderitems;

    public OrderDTO(Long id, double totalAmount, OrderStatus status, LocalDateTime orderDate, String userName, String email, List<OrderItemDTO> orderItemsDTOs) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.userName = userName;
        this.email = email;
        this.orderitems = orderItemsDTOs;
    }

    public OrderDTO(Long id, double totalAmount, OrderStatus status, LocalDateTime orderDate, List<OrderItemDTO> orderItemsDTOs) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.orderitems = orderItemsDTOs;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<OrderItemDTO> getOrderitems() { return orderitems; }
    public void setOrderitems(List<OrderItemDTO> orderitems) { this.orderitems = orderitems; }
}
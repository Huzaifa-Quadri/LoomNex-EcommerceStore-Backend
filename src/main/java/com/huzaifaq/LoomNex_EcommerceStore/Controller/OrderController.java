package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Service.OrderService;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderDTO;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderservice;

    public OrderController(OrderService orderservice) {
        this.orderservice = orderservice;
    }

    @PostMapping("place/{userId}")
    public OrderDTO placeOrder(@PathVariable Long userId, @Valid @RequestBody OrderRequest orderRequest){
        return orderservice.placeOrder(userId, orderRequest.getProductQuantities());
    }

    @GetMapping("all-orders/{userId}")
    public List<OrderDTO> getAllOrders(@PathVariable Long userId){
        return orderservice.getOrdersByUser(userId);
    }
}
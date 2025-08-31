package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Service.OrderService;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderDTO;
import com.huzaifaq.LoomNex_EcommerceStore.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*") //Allow all - well replace it with spring security
public class OrderController {

    @Autowired
    OrderService orderservice;

    @PostMapping("place/{userId}")
    public OrderDTO placeOrder(@PathVariable Long userId, @RequestBody OrderRequest orderRequest){
        return orderservice.placeOrder(userId, orderRequest.getProductQuantities(), orderRequest.getTotalAmount());
    }

    @GetMapping("all-orders/{userId}")
    public List<OrderDTO> getAllOrders(){
        return orderservice.getAllOrders();
    }
}
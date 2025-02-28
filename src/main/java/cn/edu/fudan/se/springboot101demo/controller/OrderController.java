package cn.edu.fudan.se.springboot101demo.controller;

import cn.edu.fudan.se.springboot101demo.DTO.NewOrderRequest;
import cn.edu.fudan.se.springboot101demo.DTO.OrderResponse;
import cn.edu.fudan.se.springboot101demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {this.orderService = orderService;}

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderResponseById(orderId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody @Valid NewOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

}

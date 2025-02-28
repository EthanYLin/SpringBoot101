package cn.edu.fudan.se.springboot101demo.service;

import cn.edu.fudan.se.springboot101demo.DTO.NewOrderRequest;
import cn.edu.fudan.se.springboot101demo.DTO.OrderResponse;
import cn.edu.fudan.se.springboot101demo.entity.Order;
import cn.edu.fudan.se.springboot101demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public OrderResponse getOrderResponseById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::new)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrderById(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    public OrderResponse createOrder(NewOrderRequest orderRequest) {
        var consumer = userService.getUserById(orderRequest.consumerId());
        userService.deductBalance(consumer, orderRequest.price());
        var order = new Order(consumer, orderRequest.productName(), orderRequest.price());
        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

}

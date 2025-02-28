package cn.edu.fudan.se.springboot101demo.DTO;

import cn.edu.fudan.se.springboot101demo.entity.Order;

public record OrderResponse(
    Long id,
    Long consumerId,
    String consumerName,
    String productName,
    Integer price
) {
    public OrderResponse(Order order){
        this(
            order.getId(),
            order.getConsumer().getId(),
            order.getConsumer().getName(),
            order.getProductName(),
            order.getPrice()
        );
    }
}

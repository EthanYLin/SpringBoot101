package cn.edu.fudan.se.springboot101demo.DTO;

public record NewOrderRequest(
    Long consumerId,
    String productName,
    Integer price
) {
}

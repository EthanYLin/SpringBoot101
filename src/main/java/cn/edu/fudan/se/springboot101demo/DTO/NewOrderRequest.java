package cn.edu.fudan.se.springboot101demo.DTO;

import jakarta.validation.constraints.NotNull;

public record NewOrderRequest(
    @NotNull(message = "consumerId cannot be null") Long consumerId,
    @NotNull(message = "productName cannot be null") String productName,
    @NotNull(message = "price cannot be null") Integer price
) {
}

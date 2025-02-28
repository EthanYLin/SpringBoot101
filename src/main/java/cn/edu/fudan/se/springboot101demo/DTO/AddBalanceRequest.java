package cn.edu.fudan.se.springboot101demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddBalanceRequest(
    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be greater than 0")
    Integer amount
) {
}

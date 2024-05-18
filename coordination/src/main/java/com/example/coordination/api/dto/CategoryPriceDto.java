package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryPriceDto(
        @NotNull Category category,
        @NotNull @Min(1) Integer price
) {
    @Override
    public String toString() {
        return "CategoryPriceDto{" +
                "category=" + category +
                ", price=" + price +
                '}';
    }
}

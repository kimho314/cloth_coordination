package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryPriceDto(
        @NotNull CategoryType categoryType,
        @NotNull @Min(1) Integer price
) {
    @Override
    public String toString() {
        return "CategoryPriceDto{" +
                "category=" + categoryType +
                ", price=" + price +
                '}';
    }
}

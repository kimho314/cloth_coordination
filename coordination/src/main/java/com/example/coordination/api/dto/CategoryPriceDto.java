package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;
import lombok.Builder;

@Builder
public record CategoryPriceDto(
        Category category,
        Integer price
) {
    @Override
    public String toString() {
        return "CategoryPriceDto{" +
                "category=" + category +
                ", price=" + price +
                '}';
    }
}

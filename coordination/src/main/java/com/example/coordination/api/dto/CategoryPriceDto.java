package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import lombok.Builder;

@Builder
public record CategoryPriceDto(
        CategoryType categoryType,
        Integer price
) {
    @Override
    public String toString() {
        return "CategoryPriceDto{" +
                "category=" + categoryType +
                ", price=" + price +
                '}';
    }
}

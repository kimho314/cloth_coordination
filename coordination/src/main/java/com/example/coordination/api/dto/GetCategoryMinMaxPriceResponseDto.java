package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import lombok.Builder;

@Builder
public record GetCategoryMinMaxPriceResponseDto(
        CategoryType categoryType,
        BrandPriceDto minPrice,
        BrandPriceDto maxPrice
) {

    @Override
    public String toString() {
        return "GetCategoryMinMaxPriceResponseDto{" +
                "category=" + categoryType +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}

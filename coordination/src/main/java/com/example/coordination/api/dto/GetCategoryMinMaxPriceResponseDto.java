package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;
import lombok.Builder;

@Builder
public record GetCategoryMinMaxPriceResponseDto(
        Category category,
        BrandPriceDto minPrice,
        BrandPriceDto maxPrice
) {
    public static GetCategoryMinMaxPriceResponseDto createDefault() {
        return GetCategoryMinMaxPriceResponseDto.builder()
                .build();
    }

    @Override
    public String toString() {
        return "GetCategoryMinMaxPriceResponseDto{" +
                "category=" + category +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
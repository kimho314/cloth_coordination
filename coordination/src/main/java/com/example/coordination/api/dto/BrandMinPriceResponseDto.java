package com.example.coordination.api.dto;

import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public record BrandMinPriceResponseDto(
        String brandName,
        List<CategoryPriceDto> categoryPriceDtos,
        Long totalPrice
) {
    public static BrandMinPriceResponseDto createDefault() {
        return new BrandMinPriceResponseDto("", Collections.emptyList(), 0L);
    }

    @Override
    public String toString() {
        return "BrandMinPriceResponseDto{" +
                "brandName='" + brandName + '\'' +
                ", categoryPriceDtos=" + categoryPriceDtos +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

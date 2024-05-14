package com.example.coordination.api.dto;

import lombok.Builder;

@Builder
public record BrandPriceDto(
        String brandName,
        Integer price
) {
    @Override
    public String toString() {
        return "BrandPriceDto{" +
                "brandName='" + brandName + '\'' +
                ", price=" + price +
                '}';
    }
}

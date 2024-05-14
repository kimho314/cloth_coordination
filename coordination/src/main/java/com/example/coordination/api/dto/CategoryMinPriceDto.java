package com.example.coordination.api.dto;

import lombok.Builder;

@Builder
public record CategoryMinPriceDto(
        String category,
        String brandName,
        Integer price
) {
    @Override
    public String toString() {
        return "CategoryMinPriceDto{" +
                "category='" + category + '\'' +
                ", brandName='" + brandName + '\'' +
                ", price=" + price +
                '}';
    }
}

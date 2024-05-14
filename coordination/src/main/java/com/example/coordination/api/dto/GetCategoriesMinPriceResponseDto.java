package com.example.coordination.api.dto;

import java.util.List;

public record GetCategoriesMinPriceResponseDto(
        Long totalPrice,
        List<CategoryMinPriceDto> categoryMinPriceDtos
) {
}

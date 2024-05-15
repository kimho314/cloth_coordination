package com.example.coordination.api.dto;

import java.util.List;

public record BrandRequestDto(
        String brandName,
        List<CategoryPriceDto> categoryPriceDtos
) {
}

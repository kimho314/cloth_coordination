package com.example.coordination.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BrandRequestDto(
        @NotBlank String brandName,
        @NotNull List<CategoryPriceDto> categoryPriceDtos
) {
}

package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCategoryRequestDto(
        @NotBlank String brandName,
        @NotNull CategoryType categoryType,
        @NotNull @Min(1) Integer price
) {
}

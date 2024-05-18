package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddCategoryRequestDto(
        @NotBlank String brandName,
        @NotNull Category category,
        @NotNull @Min(1) Integer price
) {
}

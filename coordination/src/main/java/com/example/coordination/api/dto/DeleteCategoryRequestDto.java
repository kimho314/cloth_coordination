package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeleteCategoryRequestDto(
        @NotBlank String brandName,
        @NotNull Category category
) {
}

package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SaveCategoryDto(
        @NotNull Long brandId,
        @NotNull Integer price,
        @NotNull CategoryType categoryType
) {
}

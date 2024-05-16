package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;

public record DeleteCategoryRequestDto(
        String brandName,
        Category category
) {
}

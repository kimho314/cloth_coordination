package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;

public record ModifyCategoryRequestDto(
        String brandName,
        Category category,
        Integer price
) {
}

package com.example.coordination.domain.dto;

public record MinMaxCategoryDto(
        Long categoryId,
        Integer minAmount,
        Integer maxAmount
) {
}

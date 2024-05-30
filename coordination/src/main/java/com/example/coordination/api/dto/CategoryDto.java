package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoryDto(
        Long id,
        Integer price,
        CategoryType categoryType,
        LocalDateTime createdAt
) {
}

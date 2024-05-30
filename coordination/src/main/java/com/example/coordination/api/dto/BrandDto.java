package com.example.coordination.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BrandDto(
        Long id,
        String name,
        LocalDateTime createdAt,
        List<CategoryDto> categoryDtos
) {
}

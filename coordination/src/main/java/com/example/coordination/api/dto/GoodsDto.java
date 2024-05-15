package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.Category;
import lombok.Builder;

@Builder
public record GoodsDto(
        String brandName,
        Category category,
        Integer price) {
}

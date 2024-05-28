package com.example.coordination.api.dto;

import com.example.coordination.domain.enums.CategoryType;
import lombok.Builder;

@Builder
public record GoodsDto(
        String brandName,
        CategoryType categoryType,
        Integer price) {
}

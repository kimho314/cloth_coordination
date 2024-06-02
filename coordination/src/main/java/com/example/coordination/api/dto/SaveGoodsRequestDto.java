package com.example.coordination.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SaveGoodsRequestDto(
        @NotNull Long brandId,
        @NotNull List<GoodsDto> goodsDtos
) {
}

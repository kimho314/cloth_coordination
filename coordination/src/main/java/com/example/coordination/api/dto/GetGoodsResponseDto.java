package com.example.coordination.api.dto;

import java.util.List;
import java.util.Map;

public record GetGoodsResponseDto(
        Map<String, List<GoodsDto>> goodsMap
) {
}

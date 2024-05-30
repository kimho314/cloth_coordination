package com.example.coordination.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SaveBrandRequestDto(
        @NotBlank String brandName
) {
}

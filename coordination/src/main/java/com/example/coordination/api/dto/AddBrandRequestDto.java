package com.example.coordination.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddBrandRequestDto(
        @NotBlank String brandName
) {
}

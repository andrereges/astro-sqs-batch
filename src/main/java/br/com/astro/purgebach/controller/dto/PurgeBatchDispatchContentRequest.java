package br.com.astro.purgebach.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PurgeBatchDispatchContentRequest(
        @NotNull @Min(1) Long quantity,
        @NotBlank String channelCode,
        @NotBlank String operationType
) {}

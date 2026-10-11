package br.com.astro.purgebach.controller.dto;

import jakarta.validation.constraints.NotNull;

public record PurgeBatchDispatchEnvelopeRequest(
        @NotNull String envelopeId,
        @NotNull String createdAt,
        @NotNull PurgeBatchDispatchContentRequest content
) {}

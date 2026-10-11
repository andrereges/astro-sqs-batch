package br.com.astro.discoverybatch.controller.dto;

import jakarta.validation.constraints.NotNull;

public record DiscoveryBatchDispatchEnvelopeRequest(
        @NotNull String envelopeId,
        @NotNull String createdAt,
        @NotNull DiscoveryBatchDispatchContentRequest content
) {}

package br.com.astro.publisher.domain.entity;

import java.time.Instant;
import java.util.UUID;

public record PublishEnvelope(
        String envelopeId,
        Instant createdAt,
        Object content
) {
    public static PublishEnvelope of(Object content) {
        return new PublishEnvelope(
                UUID.randomUUID().toString(),
                Instant.now(),
                content
        );
    }
}

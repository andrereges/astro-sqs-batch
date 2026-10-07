package br.com.astro.consumer.domain;

import java.time.Instant;
import java.util.UUID;

public record ConsumeEnvelope<T>(
        UUID messageId,
        String broker,
        String destination,
        Instant createdAt,
        T payload
) {
    public static <T> ConsumeEnvelope<T> of(
            String broker,
            String destination,
            T payload
    ) {
        return new ConsumeEnvelope<>(
                UUID.randomUUID(),
                broker,
                destination,
                Instant.now(),
                payload
        );
    }
}

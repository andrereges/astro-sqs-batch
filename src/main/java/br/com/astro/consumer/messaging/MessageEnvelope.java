package br.com.astro.consumer.messaging;

import java.time.Instant;
import java.util.UUID;

public record MessageEnvelope<T>(
        UUID messageId,
        String type,
        Instant createdAt,
        T payload
) {
    public static <T> MessageEnvelope<T> of(
            String type,
            T payload
    ) {
        return new MessageEnvelope<>(
                UUID.randomUUID(),
                type,
                Instant.now(),
                payload
        );
    }
}

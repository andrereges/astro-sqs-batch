package br.com.astro.publisher.domain;

import java.time.Instant;

public record PublishEnvelope<T>(
        BrokerEnum broker,
        String destination,
        Instant createdAt,
        T payload
) {
    public static <T> PublishEnvelope<T> of(
            BrokerEnum broker,
            String destination,
            T payload
    ) {
        return new PublishEnvelope<>(
                broker,
                destination,
                Instant.now(),
                payload
        );
    }
}

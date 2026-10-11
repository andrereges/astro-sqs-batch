package br.com.astro.publisher.infrastructure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.publisher.broker")
public record PublisherBrokerProperty(
        Broker kafka,
        Broker rabbitmq,
        Broker sqs
) {
    public record Broker(boolean enabled) {
    }
}
package br.com.astro.consumer.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.consumer.broker")
public record ConsumerBrokerProperty(
        Broker kafka,
        Broker rabbitmq,
        Broker sqs
) {
    public record Broker(boolean enabled) {
    }
}
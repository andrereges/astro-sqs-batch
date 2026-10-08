package br.com.astro.consumer.entity;

import br.com.astro.consumer.exception.BrokerNotFoundException;

public enum BrokerEnum {
    KAFKA,
    RABBITMQ,
    SQS;

    public static BrokerEnum from(String value) {
        if (value == null || value.isBlank()) {
            throw new BrokerNotFoundException(
                    "Broker is required"
            );
        }

        try {
            return BrokerEnum.valueOf(
                    value.trim().toUpperCase()
            );
        } catch (IllegalArgumentException exception) {
            throw new BrokerNotFoundException(
                    "Invalid broker: " + value,
                    exception.getCause()
            );
        }
    }
}

package br.com.astro.producer.application.port.out;

public interface MessagePublisherPort {

    <T> void publish(
            String queueName,
            String type,
            T payload
    );
}

package br.com.astro.producer.application.port.out;

public class PublishMessageService {

    private final MessagePublisherPort publisherPort;

    public PublishMessageService(MessagePublisherPort publisherPort) {
        this.publisherPort = publisherPort;
    }

    public <T> void publish(
            String queueName,
            String type,
            T payload
    ) {
        publisherPort.publish(queueName, type, payload);
    }
}

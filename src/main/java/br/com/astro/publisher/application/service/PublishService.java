package br.com.astro.publisher.application.service;

import br.com.astro.publisher.application.port.in.PublishUseCase;
import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.application.registry.PublishRegistry;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;

public class PublishService implements PublishUseCase {

    private final PublishRegistry registry;

    public PublishService(PublishRegistry registry) {
        this.registry = registry;
    }

    @Override
    public PublishEnvelope execute(
            BrokerEnum broker,
            String destination,
            Object content
    ) {
        PublishPort publisher =
                registry.getBroker(broker);

        publisher.publishValidator().validate(
                broker,
                destination,
                content
        );

        return publisher.publish(destination, content);
    }
}

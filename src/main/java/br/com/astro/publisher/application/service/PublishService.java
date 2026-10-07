package br.com.astro.publisher.application.service;

import br.com.astro.publisher.application.port.in.PublishUseCase;
import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.application.registry.PublishRegistry;
import br.com.astro.publisher.domain.BrokerEnum;

public class PublishService implements PublishUseCase {

    private final PublishRegistry registry;

    public PublishService(PublishRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void execute(
            BrokerEnum broker,
            String destination,
            Object message
    ) {
        PublishPort publisher =
                registry.get(broker);

        publisher.publish(destination, message);
    }
}

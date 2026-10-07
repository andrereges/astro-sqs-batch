package br.com.astro.publisher.application.registry;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.BrokerEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PublishRegistry {

    private final Map<String, PublishPort> publishers;

    public PublishRegistry(
            List<PublishPort> publishers
    ) {
        this.publishers = publishers.stream()
                .collect(Collectors.toUnmodifiableMap(
                        publisher -> publisher.broker().name(),
                        Function.identity()
                ));
    }

    public PublishPort get(BrokerEnum broker) {
        var publisher = publishers.get(broker.name().toUpperCase());

        if (publisher == null) {
            throw new IllegalArgumentException(
                    "Broker não suportado: " + broker
            );
        }

        return publisher;
    }

    public Map<String, PublishPort> getPublishers() {
        return publishers;
    }
}

package br.com.astro.publisher.application.port.out;

import br.com.astro.publisher.domain.BrokerEnum;

public interface PublishPort {

    BrokerEnum broker();

    <T> void publish(
            String destination,
            T payload
    );
}

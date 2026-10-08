package br.com.astro.publisher.application.port.out;

import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;

public interface PublishPort {

    BrokerEnum broker();

    PublishEnvelope publish(
            String destination,
            Object content
    );
}

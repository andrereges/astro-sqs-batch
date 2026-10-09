package br.com.astro.publisher.application.port.out;

import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import br.com.astro.publisher.domain.validator.PublishValidator;

public interface PublishPort {

    BrokerEnum broker();

    PublishValidator publishValidator();

    PublishEnvelope publish(
            String destination,
            Object content
    );
}

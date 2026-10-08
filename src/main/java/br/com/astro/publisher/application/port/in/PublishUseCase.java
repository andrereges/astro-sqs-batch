package br.com.astro.publisher.application.port.in;

import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;

public interface PublishUseCase {

    PublishEnvelope execute(
            BrokerEnum broker,
            String destination,
            Object content
    );
}

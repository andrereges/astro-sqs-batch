package br.com.astro.publisher.application.port.in;

import br.com.astro.publisher.domain.BrokerEnum;

public interface PublishUseCase {

    <T> void execute(
            BrokerEnum broker,
            String destination,
            T payload
    );
}

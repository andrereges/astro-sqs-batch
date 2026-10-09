package br.com.astro.publisher.domain.validator;

import br.com.astro.publisher.domain.entity.BrokerEnum;

public interface PublishValidator {

    void validate(BrokerEnum broker, String destination, Object content);
}

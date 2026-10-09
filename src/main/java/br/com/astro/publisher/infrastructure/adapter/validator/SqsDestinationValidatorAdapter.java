package br.com.astro.publisher.infrastructure.adapter.validator;

import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.validator.PublishValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsDestinationValidatorAdapter implements PublishValidator {

    @Override
    public void validate(BrokerEnum broker,
                         String destination,
                         Object content) {
        this.validateDestination(destination);
    }

    private void validateDestination(String destination) {
        log.info("Skipping validate Destination Validator SQS");
    }
}

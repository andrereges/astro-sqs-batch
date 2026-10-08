package br.com.astro.publisher.infrastructure.rest.impl;

import br.com.astro.publisher.application.port.in.PublishUseCase;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import br.com.astro.publisher.infrastructure.rest.PublishController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publish")
@RequiredArgsConstructor
public class PublishControllerImpl implements PublishController {

    private final PublishUseCase publishUseCase;

    @Override
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PublishEnvelope> publish(
            @RequestHeader(name = "x-broker") String broker,
            @RequestHeader(name = "x-destination") String destination,
            @RequestBody Object content
    ) {

        PublishEnvelope envelope = publishUseCase.execute(BrokerEnum.from(broker), destination, content);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(envelope);
    }
}

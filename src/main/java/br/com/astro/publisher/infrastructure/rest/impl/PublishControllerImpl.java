package br.com.astro.publisher.infrastructure.rest.impl;

import br.com.astro.publisher.application.port.in.PublishUseCase;
import br.com.astro.publisher.domain.BrokerEnum;
import br.com.astro.publisher.infrastructure.rest.PublishController;
import br.com.astro.publisher.infrastructure.rest.dto.PublishRequest;
import br.com.astro.publisher.infrastructure.rest.dto.PublishResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    public ResponseEntity<PublishResponse> publish(
            @RequestBody PublishRequest request
    ) {

        publishUseCase.execute(BrokerEnum.valueOf(request.broker()), request.destination(), request.payload());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new PublishResponse(UUID.randomUUID().toString()));
    }
}

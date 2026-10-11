package br.com.astro.consumer.handler.impl;

import br.com.astro.consumer.entity.ServiceDestinationEnum;
import br.com.astro.consumer.handler.DispatchHandler;
import br.com.astro.consumer.property.BatchProperty;
import br.com.astro.consumer.router.BatchEndpointRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchDispatchHandler implements DispatchHandler {

    private final RestClient.Builder restClientBuilder;
    private final BatchEndpointRouter endpointRouter;

    @Override
    public void dispatch(
            ServiceDestinationEnum destination,
            Object content) {

        BatchProperty.BatchService service =
                endpointRouter.resolve(destination);

        String target = service.baseUrl() + service.endpoint();

        log.info("Dispatching to destination: {}", destination);
        log.info("Dispatching to endpoint: {}", target);
        log.info("Dispatching message content: {}", content);

        try {
            RestClient restClient = restClientBuilder
                    .baseUrl(service.baseUrl())
                    .build();

            restClient.post()
                    .uri(service.endpoint())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(content)
                    .retrieve()
                    .toBodilessEntity();

            log.info(
                    "Batch endpoint responded successfully. destination={}",
                    destination
            );

        } catch (Exception exception) {
            log.error(
                    "Failed to dispatch to batch. destination={}, endpoint={}",
                    destination,
                    target,
                    exception
            );

            throw exception;
        }
    }
}
package br.com.astro.consumer.router;

import br.com.astro.consumer.entity.ServiceDestinationEnum;
import br.com.astro.consumer.property.BatchProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BatchEndpointRouter {

    private final BatchProperty batchProperty;

    public BatchProperty.BatchService resolve(ServiceDestinationEnum destination) {
        var service = batchProperty.services().get(
                destination.name().toLowerCase(Locale.ROOT));

        if (service == null) {
            throw new IllegalArgumentException(
                    "Nenhum serviço batch configurado para: " + destination);
        }

        return service;
    }
}

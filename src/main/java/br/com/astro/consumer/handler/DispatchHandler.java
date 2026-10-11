package br.com.astro.consumer.handler;

import br.com.astro.consumer.entity.ServiceDestinationEnum;

public interface DispatchHandler {

    void dispatch(ServiceDestinationEnum destination, Object content);
}
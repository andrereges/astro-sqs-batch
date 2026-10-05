package br.com.astro.consumer.application.port.in;

public interface ConsumeMessageUseCase {

    void execute(String message);
}
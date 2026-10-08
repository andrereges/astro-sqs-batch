package br.com.astro.publisher.domain.exception;

public class BrokerNotFoundException extends RuntimeException {

    public BrokerNotFoundException(String message) {
        super(message);
    }

    public BrokerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package br.com.astro.consumer.exception;

public class BrokerNotFoundException extends RuntimeException {

    public BrokerNotFoundException(String message) {
        super(message);
    }

    public BrokerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

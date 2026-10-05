package br.com.astro.consumer.application.port.in;

public class ConsumeMessageService implements ConsumeMessageUseCase{

    @Override
    public void execute(String message) {
        System.out.println(message);
    }
}

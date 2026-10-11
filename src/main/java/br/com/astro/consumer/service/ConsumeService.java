package br.com.astro.consumer.service;

public interface ConsumeService {

    void consumeDiscovery(String content);
    void consumePurge(String content);
}

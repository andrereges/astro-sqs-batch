package br.com.astro;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableRabbit
@EnableKafka
@SpringBootApplication
public class AstroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AstroApplication.class, args);
	}

}

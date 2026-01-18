package com.nakomans.economizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EconomizzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EconomizzaApplication.class, args);
	}

}

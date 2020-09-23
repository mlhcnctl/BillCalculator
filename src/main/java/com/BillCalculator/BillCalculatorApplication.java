package com.BillCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BillCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillCalculatorApplication.class, args);
	}

}

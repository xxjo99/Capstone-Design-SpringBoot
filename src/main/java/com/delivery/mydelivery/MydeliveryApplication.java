package com.delivery.mydelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.delivery.mydelivery")
@EnableAutoConfiguration
public class MydeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydeliveryApplication.class, args);
	}

}

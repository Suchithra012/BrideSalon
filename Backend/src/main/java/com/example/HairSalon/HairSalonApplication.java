package com.example.HairSalon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
		exclude = {SecurityAutoConfiguration.class}
)
public class HairSalonApplication {
	public HairSalonApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(HairSalonApplication.class, args);
	}
}
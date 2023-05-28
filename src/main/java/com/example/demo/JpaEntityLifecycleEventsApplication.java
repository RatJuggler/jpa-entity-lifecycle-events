package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaEntityLifecycleEventsApplication {

	private static final Logger log = LoggerFactory.getLogger(JpaEntityLifecycleEventsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaEntityLifecycleEventsApplication.class, args);
	}
}

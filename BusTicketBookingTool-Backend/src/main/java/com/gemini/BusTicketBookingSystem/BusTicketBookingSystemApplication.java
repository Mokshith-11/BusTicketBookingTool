package com.gemini.BusTicketBookingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
 * Beginner guide:
 * - This is part of the Spring Boot application startup/configuration flow.
 * - Spring scans the project from here, creates beans for controllers/services/repositories, and starts the API server.
 * - After startup, frontend HTTP requests can reach the mapped controller endpoints.
 */
public class BusTicketBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusTicketBookingSystemApplication.class, args);
	}
}
package com.leadergym.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaderGymApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderGymApplication.class, args);
		System.out.println("LeaderGymApplication is running...");
	}

}

package com.Clubbr.Clubbr;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableScheduling
public class ClubbrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubbrApplication.class, args);
	}
}

package com.smarttaskboard.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartTaskBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartTaskBoardApplication.class, args);
	}

}

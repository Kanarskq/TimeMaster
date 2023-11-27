package com.example.TimeMaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeMasterApplication.class, args);
	}

}

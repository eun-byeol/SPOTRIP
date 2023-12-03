package com.ssafy.spotrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ssafy.spotrip"})
public class SpotripApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotripApplication.class, args);
	}

}

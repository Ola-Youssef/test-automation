package com.graduation_project.street2shelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Street2shelterApplication extends SpringBootServletInitializer {
		//extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Street2shelterApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Street2shelterApplication.class);
	}
}

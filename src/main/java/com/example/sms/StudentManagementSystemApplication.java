package com.example.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication is a powerful 'super' annotation. It combines 3 annotations:
 * 1. @Configuration - Marks this class as a source of configuration definitions.
 * 2. @EnableAutoConfiguration - Tells Spring to start adding beans based on our classpath settings automatically.
 * 3. @ComponentScan - Tells Spring to look for other components, configurations, and services in the 'com.example.sms' package.
 */
@SpringBootApplication
public class StudentManagementSystemApplication {

	public static void main(String[] args) {
		// This line is what actually boots up the Spring framework and runs our embedded Tomcat server.
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}

}

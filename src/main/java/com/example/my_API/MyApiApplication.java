package com.example.my_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.my_API.config.property.my_APIApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(my_APIApiProperty.class)
public class MyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyApiApplication.class, args);
	}

}

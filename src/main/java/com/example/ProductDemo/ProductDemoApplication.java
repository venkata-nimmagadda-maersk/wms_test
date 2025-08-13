package com.example.ProductDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EntityScan(basePackages = "com.example.ProductDemo")
public class ProductDemoApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ProductDemoApplication.class, args);

	}

}

package com.cybersoft.uniclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UniclubApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniclubApplication.class, args);
	}

}

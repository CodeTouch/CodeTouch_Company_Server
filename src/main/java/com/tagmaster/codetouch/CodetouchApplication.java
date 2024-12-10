package com.tagmaster.codetouch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"com.tagmaster.codetouch.repository"}) // UserRepository 위치
//@EntityScan(basePackages = {"com.tagmaster.codetouch.entity"})
public class CodetouchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodetouchApplication.class, args);
	}

}

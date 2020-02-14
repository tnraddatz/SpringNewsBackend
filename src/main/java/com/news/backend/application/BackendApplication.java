package com.news.backend.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.news.backend.dao.repository")
@ComponentScan("com.news.backend")
@EntityScan("com.news.backend.dao.model")
@SpringBootApplication(scanBasePackages={"com.news.backend","com.news.backend.application"})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

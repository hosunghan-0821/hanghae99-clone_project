package com.hanghae.clone_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloneProjectApplication {


	public static void main(String[] args) {
		new SpringApplicationBuilder(CloneProjectApplication.class).run(args);

	}

}

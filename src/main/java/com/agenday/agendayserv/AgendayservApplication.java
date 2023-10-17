package com.agenday.agendayserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
public class AgendayservApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgendayservApplication.class, args);
	}
}

package com.spring.universidad.cryptop2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class UniversidadBackendApplication {
	public static final Logger logger = LogManager.getLogger(UniversidadBackendApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
	}

}

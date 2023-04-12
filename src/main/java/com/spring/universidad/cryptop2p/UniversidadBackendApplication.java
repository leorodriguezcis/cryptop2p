package com.spring.universidad.cryptop2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversidadBackendApplication {


	public static void main(String[] args) {
		String[] str = SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
	}

}

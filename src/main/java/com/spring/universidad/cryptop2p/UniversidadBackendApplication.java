package com.spring.universidad.cryptop2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class UniversidadBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
	}

}

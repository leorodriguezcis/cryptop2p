package com.spring.universidad.cryptop2p;

import com.spring.universidad.cryptop2p.modelo.entities.dto.DolarDTOHelper;
import com.spring.universidad.cryptop2p.modelo.entities.dto.DollarDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UniversidadBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
	}

}

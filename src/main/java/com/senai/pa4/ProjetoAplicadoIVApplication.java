package com.senai.pa4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
public class ProjetoAplicadoIVApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoAplicadoIVApplication.class, args);
	}

}

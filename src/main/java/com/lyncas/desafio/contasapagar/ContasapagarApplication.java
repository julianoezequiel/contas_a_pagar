package com.lyncas.desafio.contasapagar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal para inicialização da aplicação Contas a Pagar. Esta classe
 * contém o método main que inicia a aplicação Spring Boot.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@SpringBootApplication
public class ContasapagarApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 *
	 * @param args Argumentos de linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(ContasapagarApplication.class, args);
	}

}

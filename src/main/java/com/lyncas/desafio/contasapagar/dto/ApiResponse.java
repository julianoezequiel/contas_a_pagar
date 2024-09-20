package com.lyncas.desafio.contasapagar.dto;

/**
 * Classe que representa a resposta padrão em JSON para os endpoints da API.
 * Contém uma mensagem e um status HTTP.
 *
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 */
public class ApiResponse {

	/**
	 * Mensagem informativa sobre o resultado da operação.
	 */
	private String message;

	/**
	 * Código de status HTTP associado à resposta.
	 */
	private int status;

	/**
	 * Construtor da classe ApiResponse.
	 *
	 * @param message a mensagem descrevendo o resultado da operação
	 * @param status  o código de status HTTP representando o estado da resposta
	 */
	public ApiResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}

	/**
	 * Obtém a mensagem da resposta.
	 *
	 * @return a mensagem da resposta
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Define a mensagem da resposta.
	 *
	 * @param message a nova mensagem da resposta
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Obtém o código de status HTTP da resposta.
	 *
	 * @return o código de status HTTP da resposta
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Define o código de status HTTP da resposta.
	 *
	 * @param status o novo código de status HTTP da resposta
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}

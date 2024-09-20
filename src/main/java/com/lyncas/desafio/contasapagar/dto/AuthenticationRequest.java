package com.lyncas.desafio.contasapagar.dto;

/**
 * Classe que representa a requisição de autenticação do usuário. Contém o nome
 * de usuário e a senha que serão utilizados para autenticação.
 *
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
public class AuthenticationRequest {

	/** O nome de usuário utilizado na autenticação */
	private String username;

	/** A senha utilizada na autenticação */
	private String password;

	/**
	 * Construtor com parâmetros para inicializar os atributos username e password.
	 *
	 * @param username o nome de usuário
	 * @param password a senha do usuário
	 */
	public AuthenticationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Construtor padrão sem parâmetros. Necessário para frameworks que exigem um
	 * construtor padrão.
	 */
	public AuthenticationRequest() {
		// Construtor padrão vazio
	}

	/**
	 * Obtém o nome de usuário.
	 *
	 * @return o nome de usuário
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Define o nome de usuário.
	 *
	 * @param username o nome de usuário a ser definido
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Obtém a senha.
	 *
	 * @return a senha do usuário
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Define a senha do usuário.
	 *
	 * @param password a senha a ser definida
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

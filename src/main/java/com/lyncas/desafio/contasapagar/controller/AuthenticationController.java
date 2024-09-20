package com.lyncas.desafio.contasapagar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lyncas.desafio.contasapagar.dto.AuthenticationRequest;
import com.lyncas.desafio.contasapagar.utils.JwtUtil;

/**
 * Controlador responsável por lidar com a autenticação dos usuários e geração
 * de tokens JWT.
 *
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Autentica o usuário com base nas credenciais fornecidas e gera um token JWT
	 * se a autenticação for bem-sucedida.
	 *
	 * @param authenticationRequest objeto contendo o nome de usuário e senha
	 * @return uma string com o token JWT gerado
	 * @throws Exception se a autenticação falhar ou ocorrer algum erro
	 */
	@PostMapping("/authenticate")
	public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			// Autentica o usuário com o token de autenticação usando nome de usuário e
			// senha
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			// Lança exceção em caso de falha na autenticação
			throw new Exception("Usuário ou senha inválidos", e);
		}

		// Carrega os detalhes do usuário autenticado
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		// Gera o token JWT com base no nome de usuário
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());

		// Retorna o token JWT gerado
		return jwt;
	}
}

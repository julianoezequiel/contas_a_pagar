package com.lyncas.desafio.contasapagar.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utilitário para manipulação de tokens JWT (JSON Web Token). Esta classe
 * fornece métodos para gerar, validar e extrair informações de tokens JWT.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@Component
public class JwtUtil {

	/**
	 * Chave secreta utilizada para assinar os tokens JWT.
	 */
	private String SECRET_KEY = "secreta";

	/**
	 * Extrai o nome de usuário (subject) do token JWT.
	 * 
	 * @param token o token JWT do qual se deseja extrair o nome de usuário
	 * @return o nome de usuário contido no token
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extrai a data de expiração do token JWT.
	 * 
	 * @param token o token JWT do qual se deseja extrair a data de expiração
	 * @return a data de expiração do token
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extrai uma informação específica do token JWT, usando uma função para
	 * processar as declarações (claims) do token.
	 * 
	 * @param <T>            o tipo da informação a ser extraída
	 * @param token          o token JWT do qual se deseja extrair a informação
	 * @param claimsResolver a função que processa as claims para extrair a
	 *                       informação desejada
	 * @return o valor extraído do token
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extrai todas as claims do token JWT.
	 * 
	 * @param token o token JWT do qual se deseja extrair as claims
	 * @return as claims contidas no token
	 */
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Verifica se o token JWT está expirado.
	 * 
	 * @param token o token JWT a ser verificado
	 * @return true se o token estiver expirado, false caso contrário
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Gera um token JWT para um determinado usuário.
	 * 
	 * @param username o nome de usuário para o qual o token será gerado
	 * @return o token JWT gerado
	 */
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	/**
	 * Cria o token JWT com as claims e o subject fornecidos.
	 * 
	 * @param claims  as claims a serem incluídas no token
	 * @param subject o subject (nome de usuário) para o qual o token será emitido
	 * @return o token JWT criado
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiração de 10 horas
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	/**
	 * Valida se o token JWT é válido com base no nome de usuário e se o token não
	 * está expirado.
	 * 
	 * @param token    o token JWT a ser validado
	 * @param username o nome de usuário esperado
	 * @return true se o token for válido, false caso contrário
	 */
	public Boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
}

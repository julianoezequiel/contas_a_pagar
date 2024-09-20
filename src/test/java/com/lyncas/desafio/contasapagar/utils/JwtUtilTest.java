package com.lyncas.desafio.contasapagar.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
class JwtUtilTest {

	@InjectMocks
	private JwtUtil jwtUtil;

	private String testUsername = "usuario_teste";
	private String testToken;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		testToken = jwtUtil.generateToken(testUsername);
	}

	@Test
	void testExtractUsername() {
		String username = jwtUtil.extractUsername(testToken);
		assertEquals(testUsername, username);
	}

	@Test
	void testExtractExpiration() {
		Date expiration = jwtUtil.extractExpiration(testToken);
		assertNotNull(expiration);
		assertTrue(expiration.after(new Date()));
	}

	@Test
	void testGenerateToken() {
		String generatedToken = jwtUtil.generateToken(testUsername);
		assertNotNull(generatedToken);
		assertFalse(generatedToken.isEmpty());
	}

	@Test
	void testValidateToken_Success() {
		boolean isValid = jwtUtil.validateToken(testToken, testUsername);
		assertTrue(isValid);
	}

	@Test
	void testValidateToken_Failure() {
		String invalidUsername = "usuario_invalido";
		boolean isValid = jwtUtil.validateToken(testToken, invalidUsername);
		assertFalse(isValid);
	}

	@Test
	void testIsTokenExpired_False() {
		boolean isExpired = jwtUtil.extractExpiration(testToken).before(new Date());
		assertFalse(isExpired);
	}

	@Test
	void testIsTokenExpired_True() {
		// Gera um token com expiração já passada
		String expiredToken = Jwts.builder().setSubject(testUsername)
				.setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 11)) // 11 horas atrás
				.setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // Expirado há 1 hora
				.signWith(SignatureAlgorithm.HS256, "secreta").compact();

		// Verifica se uma exceção de expiração do JWT é lançada ao tentar extrair as
		// claims
		assertThrows(ExpiredJwtException.class, () -> {
			jwtUtil.extractAllClaims(expiredToken); // Esse método deve lançar a exceção
		});
	}
}

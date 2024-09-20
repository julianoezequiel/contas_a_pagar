package com.lyncas.desafio.contasapagar.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import com.lyncas.desafio.contasapagar.dto.AuthenticationRequest;
import com.lyncas.desafio.contasapagar.utils.JwtUtil;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
@SpringBootTest
@ActiveProfiles("test")
class AuthenticationControllerTest {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private UserDetailsService userDetailsService;

	@InjectMocks
	private AuthenticationController authenticationController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Inicializa os mocks
	}

	@Test
	void testCreateAuthenticationToken_Success() throws Exception {

		String token_test = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvX2Rlc2FmaW8iLCJleHAiOjE3MjY4ODI3ODQsImlhdCI6MTcyNjg0Njc4NH0.ORUuP5avNxcE8t44rkLAfLvSR-kRZaP2YvyoAOLnlh0";
		String user_test = "usuario_desafio";
		String pass_test = "senha_desafio";

		// Dados de teste
		AuthenticationRequest authRequest = new AuthenticationRequest();
		authRequest.setUsername(user_test);
		authRequest.setPassword(pass_test);

		UserDetails userDetails = mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(user_test);

		// Simula comportamento dos mocks
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
		when(userDetailsService.loadUserByUsername(user_test)).thenReturn(userDetails);
		when(jwtUtil.generateToken(user_test)).thenReturn(token_test);

		// Executa o método e valida o resultado
		String token = authenticationController.createAuthenticationToken(authRequest);
		assertEquals(token_test, token);
	}

	@Test
	void testCreateAuthenticationToken_InvalidCredentials() {
		// Dados de teste
		AuthenticationRequest authRequest = new AuthenticationRequest();
		authRequest.setUsername("invalidUser");
		authRequest.setPassword("invalidPassword");

		// Simula uma exceção ao autenticar
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new RuntimeException("Usuário ou senha inválidos"));

		// Valida se a exceção correta é lançada
		Exception exception = assertThrows(Exception.class, () -> {
			authenticationController.createAuthenticationToken(authRequest);
		});

		assertEquals("Usuário ou senha inválidos", exception.getMessage());
	}
}

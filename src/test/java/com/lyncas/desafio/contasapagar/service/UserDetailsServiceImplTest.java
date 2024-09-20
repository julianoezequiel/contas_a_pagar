package com.lyncas.desafio.contasapagar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplTest {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoadUserByUsername_Success() {
		// Teste se o método retorna o usuário correto
		String username = "usuario_desafio";
		String password = "senha_desafio";

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		assertNotNull(userDetails);
		assertEquals(username, userDetails.getUsername());
		assertEquals(password, userDetails.getPassword());
	}

}

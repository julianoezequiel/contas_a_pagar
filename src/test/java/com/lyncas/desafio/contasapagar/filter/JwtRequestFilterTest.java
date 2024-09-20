package com.lyncas.desafio.contasapagar.filter;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import com.lyncas.desafio.contasapagar.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
@SpringBootTest
@ActiveProfiles("test")
class JwtRequestFilterTest {

	@InjectMocks
	private JwtRequestFilter jwtRequestFilter;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain chain;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testDoFilterInternal() throws Exception {
		// Configura o comportamento do mock conforme necessário
		// Exemplo: when(jwtUtil.extractUsername(anyString())).thenReturn("usuario");

		jwtRequestFilter.doFilterInternal(request, response, chain);

		// Adicione suas asserções aqui
		verify(chain).doFilter(request, response);
	}

}

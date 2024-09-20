package com.lyncas.desafio.contasapagar.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
@SpringBootTest
class SecurityConfigTest {

	@Autowired
	private SecurityConfig securityConfig;

	@Test
	public void testSecurityFilterChain() throws Exception {
		HttpSecurity http = null; // Inicialize ou mocke conforme necessário
		SecurityFilterChain filterChain = securityConfig.securityFilterChain(http);
		assertThat(filterChain).isNotNull();
	}
}

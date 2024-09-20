package com.lyncas.desafio.contasapagar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lyncas.desafio.contasapagar.filter.JwtRequestFilter;

/**
 * Configuração de segurança para a aplicação, utilizando o Spring Security.
 * Define autenticação, autorização e CORS (Cross-Origin Resource Sharing).
 *
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * @author juliano.ezequiel
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	/**
	 * Construtor que injeta o serviço de detalhes do usuário.
	 *
	 * @param userDetailsService serviço que carrega os detalhes do usuário
	 */
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Configura o CORS para permitir requisições de origens diferentes.
	 *
	 * @return uma instância de {@link WebMvcConfigurer} configurada para permitir
	 *         requisições de qualquer origem
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*"); // Atualize com a URL do seu frontend
			}
		};
	}

	/**
	 * Configura a cadeia de filtros de segurança do Spring Security.
	 *
	 * @param http instância de {@link HttpSecurity} para configurar a segurança
	 * @return a cadeia de filtros de segurança
	 * @throws Exception caso ocorra algum erro na configuração
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests(
				authorize -> authorize.requestMatchers("/authenticate").permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Cria e configura o filtro JWT.
	 *
	 * @return uma instância de {@link JwtRequestFilter}
	 */
	@Bean
	public JwtRequestFilter jwtRequestFilter() {
		return new JwtRequestFilter();
	}

	/**
	 * Configura o {@link AuthenticationManager}, associando-o ao
	 * {@link UserDetailsService} e ao codificador de senhas.
	 *
	 * @param http instância de {@link HttpSecurity}
	 * @return uma instância de {@link AuthenticationManager}
	 * @throws Exception caso ocorra algum erro na configuração
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

		return authenticationManagerBuilder.build();
	}

	/**
	 * Configura o codificador de senhas. OBS: NoOpPasswordEncoder não deve ser
	 * utilizado em produção.
	 *
	 * @return uma instância de {@link PasswordEncoder}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); // Use um encoder mais seguro em produção
	}
}

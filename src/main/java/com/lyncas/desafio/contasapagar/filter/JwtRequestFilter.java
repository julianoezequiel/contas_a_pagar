package com.lyncas.desafio.contasapagar.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lyncas.desafio.contasapagar.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro para processar requisições HTTP e validar tokens JWT. Este filtro é
 * executado uma vez por requisição e verifica se o token JWT está presente,
 * válido e se corresponde ao usuário autenticado.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Método que executa a lógica do filtro. Extrai o token JWT do cabeçalho da
	 * requisição, valida-o e, se válido, configura o contexto de segurança com as
	 * informações do usuário.
	 *
	 * @param request  a requisição HTTP
	 * @param response a resposta HTTP
	 * @param chain    a cadeia de filtros
	 * @throws ServletException se ocorrer um erro na execução do filtro
	 * @throws IOException      se ocorrer um erro de I/O
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");
		String username = null;

		if (authorizationHeader != null) {
			if (authorizationHeader.startsWith("Bearer ")) {
				authorizationHeader = authorizationHeader.substring(7);
			}
			try {
				username = jwtUtil.extractUsername(authorizationHeader);
			} catch (ExpiredJwtException e) {
				System.out.println("Token JWT expirado");
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (jwtUtil.validateToken(authorizationHeader, userDetails.getUsername())) {
				// Definir o contexto de segurança com o usuário autenticado
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}

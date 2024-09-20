package com.lyncas.desafio.contasapagar.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementação do serviço {@link UserDetailsService} responsável por carregar
 * os detalhes do usuário para autenticação.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Carrega os detalhes de um usuário com base no nome de usuário fornecido.
	 * Neste exemplo, retorna um usuário fixo.
	 * 
	 * @param username o nome de usuário que está sendo procurado
	 * @return um {@link UserDetails} contendo o nome de usuário, senha e as
	 *         permissões do usuário
	 * @throws UsernameNotFoundException se o nome de usuário não for encontrado
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Aqui você deve buscar o usuário no banco de dados e retornar os detalhes do
		// usuário.
		// Exemplo de retorno de um usuário fixo (deve ser adaptado para buscar da base
		// de dados real).
		return new User("usuario_desafio", "senha_desafio", new ArrayList<>());
	}

}

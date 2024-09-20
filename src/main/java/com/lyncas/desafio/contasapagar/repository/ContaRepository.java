package com.lyncas.desafio.contasapagar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyncas.desafio.contasapagar.model.Conta;

/**
 * Repositório para operações de acesso a dados da entidade {@link Conta}. Esta
 * interface estende JpaRepository, fornecendo métodos para manipulação de
 * contas, incluindo consultas personalizadas.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	/**
	 * Busca uma página de contas que possuem uma data de vencimento entre duas
	 * datas e uma descrição que contém um texto específico.
	 * 
	 * @param inicio    a data de vencimento inicial
	 * @param fim       a data de vencimento final
	 * @param descricao o texto que deve estar contido na descrição da conta
	 * @param pageable  informações de paginação
	 * @return uma página de contas que atendem aos critérios especificados
	 */
	Page<Conta> findByDataVencimentoBetweenAndDescricaoContaining(LocalDate inicio, LocalDate fim, String descricao,
			Pageable pageable);

	/**
	 * Busca todas as contas que foram pagas entre duas datas específicas.
	 * 
	 * @param dataInicio a data inicial do intervalo de busca
	 * @param dataFim    a data final do intervalo de busca
	 * @return uma lista de contas pagas que atendem ao intervalo de datas
	 */
	List<Conta> findByDataPagamentoBetween(LocalDate dataInicio, LocalDate dataFim);

	/**
	 * Busca uma página de contas que possuem uma data de vencimento entre duas
	 * datas específicas.
	 * 
	 * @param dataVencimentoInicio a data inicial do intervalo de vencimento
	 * @param dataVencimentoFim    a data final do intervalo de vencimento
	 * @param pageable             informações de paginação
	 * @return uma página de contas que atendem aos critérios especificados
	 */
	Page<Conta> findByDataVencimentoBetween(LocalDate dataVencimentoInicio, LocalDate dataVencimentoFim,
			Pageable pageable);
}

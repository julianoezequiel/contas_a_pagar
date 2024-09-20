package com.lyncas.desafio.contasapagar.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.lyncas.desafio.contasapagar.model.Conta;

/**
 * Testes unitários para a interface {@link ContaRepository}. Utiliza Mockito
 * para simular o comportamento do repositório e validar as operações de acesso
 * a dados.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@SpringBootTest
@ActiveProfiles("test")
class ContaRepositoryTest {

	@Mock
	private ContaRepository contaRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Testa a busca de contas por data de vencimento e descrição.
	 */
	@Test
	void testFindByDataVencimentoBetweenAndDescricaoContaining() {
		// Configura dados de exemplo
		List<Conta> contas = new ArrayList<>();
		Conta conta = new Conta();
		conta.setDescricao("Conta de luz");
		conta.setDataVencimento(LocalDate.of(2024, 9, 10));
		contas.add(conta);

		Pageable pageable = PageRequest.of(0, 10);
		Page<Conta> pageContas = new PageImpl<>(contas, pageable, contas.size());

		// Simula o comportamento do repositório
		doReturn(pageContas).when(contaRepository).findByDataVencimentoBetweenAndDescricaoContaining(
				any(LocalDate.class), any(LocalDate.class), any(), any());

		// Executa o método a ser testado
		Page<Conta> result = contaRepository.findByDataVencimentoBetweenAndDescricaoContaining(LocalDate.of(2024, 9, 1),
				LocalDate.of(2024, 9, 20), "Conta de luz", pageable);

		// Verifica o resultado
		assertEquals(1, result.getTotalElements());
		assertEquals("Conta de luz", result.getContent().get(0).getDescricao());
	}

	/**
	 * Testa a busca de contas pagas entre duas datas.
	 */
	@Test
	void testFindByDataPagamentoBetween() {
		// Configura dados de exemplo
		List<Conta> contas = new ArrayList<>();
		Conta conta = new Conta();
		conta.setValor(new BigDecimal("100.00"));
		contas.add(conta);

		// Simula o comportamento do repositório
		doReturn(contas).when(contaRepository).findByDataPagamentoBetween(any(LocalDate.class), any(LocalDate.class));

		// Executa o método a ser testado
		List<Conta> result = contaRepository.findByDataPagamentoBetween(LocalDate.of(2024, 9, 1),
				LocalDate.of(2024, 9, 30));

		// Verifica o resultado
		assertEquals(1, result.size());
		assertEquals(new BigDecimal("100.00"), result.get(0).getValor());
	}

	/**
	 * Testa a busca de contas por data de vencimento.
	 */
	@Test
	void testFindByDataVencimentoBetween() {
		// Configura dados de exemplo
		List<Conta> contas = new ArrayList<>();
		Conta conta = new Conta();
		contas.add(conta);

		Pageable pageable = PageRequest.of(0, 10);
		Page<Conta> pageContas = new PageImpl<>(contas, pageable, contas.size());

		// Simula o comportamento do repositório
		doReturn(pageContas).when(contaRepository).findByDataVencimentoBetween(any(LocalDate.class),
				any(LocalDate.class), any(Pageable.class));

		// Executa o método a ser testado
		Page<Conta> result = contaRepository.findByDataVencimentoBetween(LocalDate.of(2024, 9, 1),
				LocalDate.of(2024, 9, 30), pageable);

		// Verifica o resultado
		assertEquals(1, result.getTotalElements());
	}
}

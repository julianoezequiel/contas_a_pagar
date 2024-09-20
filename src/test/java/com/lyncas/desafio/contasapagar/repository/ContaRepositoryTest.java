package com.lyncas.desafio.contasapagar.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.lyncas.desafio.contasapagar.model.Conta;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Evita a substituição do DataSource
class ContaRepositoryTest {

	@Autowired
	private ContaRepository contaRepository;

	@BeforeEach
	void setUp() {
		// Criando dados de exemplo no PostgreSQL
		Conta conta1 = new Conta();
		conta1.setDataVencimento(LocalDate.of(2024, 9, 10));
		conta1.setDataPagamento(LocalDate.of(2024, 9, 11));
		conta1.setValor(new BigDecimal("200.00"));
		conta1.setDescricao("Conta de luz");
		conta1.setSituacao("Paga");

		Conta conta2 = new Conta();
		conta2.setDataVencimento(LocalDate.of(2024, 9, 15));
		conta2.setDataPagamento(LocalDate.of(2024, 9, 16));
		conta2.setValor(new BigDecimal("150.00"));
		conta2.setDescricao("Conta de água teste");
		conta2.setSituacao("Paga");

		contaRepository.save(conta1);
		contaRepository.save(conta2);
	}

	@Test
	void testFindByDataVencimentoBetweenAndDescricaoContaining() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Conta> contas = contaRepository.findByDataVencimentoBetweenAndDescricaoContaining(LocalDate.of(2024, 9, 1),
				LocalDate.of(2024, 9, 20), "Conta de água teste", pageable);

		assertEquals(1, contas.getTotalElements());
		assertEquals("Conta de água teste", contas.getContent().get(0).getDescricao());
	}

	@Test
	void testFindByDataPagamentoBetween() {
		List<Conta> contas = contaRepository.findByDataPagamentoBetween(LocalDate.of(2024, 9, 1),
				LocalDate.of(2024, 9, 30));

		assertFalse(contas.isEmpty(), "A lista de contas não deveria estar vazia");
	}

}

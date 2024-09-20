package com.lyncas.desafio.contasapagar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
class ContaTest {

	@Test
	void testGettersAndSetters() {
		Conta conta = new Conta();

		// Testando o ID
		Long id = 1L;
		conta.setId(id);
		assertEquals(id, conta.getId());

		// Testando a data de vencimento
		LocalDate dataVencimento = LocalDate.of(2024, 9, 20);
		conta.setDataVencimento(dataVencimento);
		assertEquals(dataVencimento, conta.getDataVencimento());

		// Testando a data de pagamento
		LocalDate dataPagamento = LocalDate.of(2024, 9, 25);
		conta.setDataPagamento(dataPagamento);
		assertEquals(dataPagamento, conta.getDataPagamento());

		// Testando o valor
		BigDecimal valor = new BigDecimal("100.00");
		conta.setValor(valor);
		assertEquals(valor, conta.getValor());

		// Testando a descrição
		String descricao = "Conta de energia";
		conta.setDescricao(descricao);
		assertEquals(descricao, conta.getDescricao());

		// Testando a situação
		String situacao = "Paga";
		conta.setSituacao(situacao);
		assertEquals(situacao, conta.getSituacao());
	}
}

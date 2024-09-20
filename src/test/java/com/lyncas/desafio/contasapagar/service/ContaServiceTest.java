package com.lyncas.desafio.contasapagar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.lyncas.desafio.contasapagar.model.Conta;
import com.lyncas.desafio.contasapagar.repository.ContaRepository;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
class ContaServiceTest {

	@InjectMocks
	private ContaService contaService;

	@Mock
	private ContaRepository contaRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCriarConta() {
		Conta conta = new Conta();
		conta.setValor(new BigDecimal("100.00"));

		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta contaSalva = contaService.criarConta(conta);
		assertNotNull(contaSalva);
		assertEquals(new BigDecimal("100.00"), contaSalva.getValor());
	}

	@Test
	void testAtualizarConta() {
		Long id = 1L;
		Conta conta = new Conta();
		conta.setValor(new BigDecimal("200.00"));
		conta.setId(id);

		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta contaAtualizada = contaService.atualizarConta(id, conta);
		assertNotNull(contaAtualizada);
		assertEquals(id, contaAtualizada.getId());
		assertEquals(new BigDecimal("200.00"), contaAtualizada.getValor());
	}

	@Test
	void testAlterarSituacao() {
		Long id = 1L;
		Conta conta = new Conta();
		conta.setId(id);
		conta.setSituacao("PAGA");

		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		contaService.alterarSituacao(id, "PENDENTE");
		assertEquals("PENDENTE", conta.getSituacao());
		verify(contaRepository).save(conta);
	}

	@Test
	void testListarContas() {
		Pageable pageable = Pageable.ofSize(10);
		LocalDate inicio = LocalDate.now();
		LocalDate fim = LocalDate.now().plusDays(30);
		String descricao = "Conta";

		Conta conta = new Conta();
		conta.setDescricao(descricao);
		when(contaRepository.findByDataVencimentoBetweenAndDescricaoContaining(any(), any(), any(), any()))
				.thenReturn(Page.empty());

		Page<Conta> contas = contaService.listarContas(pageable, inicio, fim, descricao);
		assertNotNull(contas);
		assertTrue(contas.isEmpty());
	}

	@Test
	void testObterValorTotalPago() {
		LocalDate inicio = LocalDate.of(2024, 1, 1);
		LocalDate fim = LocalDate.of(2024, 12, 31);
		Conta conta1 = new Conta();
		conta1.setValor(new BigDecimal("100.00"));
		Conta conta2 = new Conta();
		conta2.setValor(new BigDecimal("200.00"));

		when(contaRepository.findByDataPagamentoBetween(inicio, fim)).thenReturn(Arrays.asList(conta1, conta2));

		BigDecimal totalPago = contaService.obterValorTotalPago(inicio, fim);
		assertEquals(new BigDecimal("300.00"), totalPago);
	}

	@Test
	void testImportarContas() throws Exception {
		MultipartFile arquivoMock = mock(MultipartFile.class);
		when(arquivoMock.getInputStream()).thenReturn(new ByteArrayInputStream(
				"data_vencimento,valor,descricao,situacao\n2024-09-20,100.00,Conta Teste,PAGA\n".getBytes()));

		contaService.importarContas(arquivoMock);

		verify(contaRepository, times(1)).save(any(Conta.class));
	}
}

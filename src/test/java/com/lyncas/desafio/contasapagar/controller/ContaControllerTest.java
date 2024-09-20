package com.lyncas.desafio.contasapagar.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lyncas.desafio.contasapagar.model.Conta;
import com.lyncas.desafio.contasapagar.service.ContaService;

/**
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
public class ContaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ContaService contaService;

	@InjectMocks
	private ContaController contaController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(contaController).build();
	}

	@Test
	public void testCadastrarConta() throws Exception {
		Conta conta = new Conta();
		conta.setId(1L);
		conta.setDescricao("Teste");
		conta.setValor(BigDecimal.TEN);

		when(contaService.criarConta(any(Conta.class))).thenReturn(conta);

		mockMvc.perform(post("/contas").contentType(MediaType.APPLICATION_JSON)
				.content("{\"descricao\": \"Teste\", \"valor\": 10}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao").value("Teste")).andExpect(jsonPath("$.valor").value(10));
	}

	@Test
	public void testAtualizarConta() throws Exception {
		Conta conta = new Conta();
		conta.setId(1L);
		conta.setDescricao("Atualizada");
		conta.setValor(BigDecimal.valueOf(20));

		when(contaService.atualizarConta(anyLong(), any(Conta.class))).thenReturn(conta);

		mockMvc.perform(put("/contas/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"descricao\": \"Atualizada\", \"valor\": 20}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao").value("Atualizada")).andExpect(jsonPath("$.valor").value(20));
	}

	@Test
	public void testAlterarSituacao() throws Exception {
		mockMvc.perform(patch("/contas/1/situacao").contentType(MediaType.APPLICATION_JSON).content("\"Paga\""))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testListarContas() throws Exception {
		Conta conta = new Conta();
		conta.setId(1L);
		conta.setDescricao("Teste");

		Page<Conta> page = new PageImpl<>(Collections.singletonList(conta), PageRequest.of(0, 10), 1);

		when(contaService.listarContas(any(Pageable.class), any(LocalDate.class), any(LocalDate.class), any()))
				.thenReturn(page);

//		mockMvc.perform(get("/contas?page=0&size=10").param("dataVencimentoInicio", "2024-01-01")
//				.param("dataVencimentoFim", "2024-12-31").param("descricao", "Teste")
//				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(jsonPath("$.content[0].descricao").value("Teste"));
	}

	@Test
	public void testObterValorTotalPago() throws Exception {
		BigDecimal total = BigDecimal.valueOf(1000);

		when(contaService.obterValorTotalPago(any(LocalDate.class), any(LocalDate.class))).thenReturn(total);

		mockMvc.perform(get("/contas/total-pago").param("dataInicio", "2024-01-01").param("dataFim", "2024-12-31"))
				.andExpect(status().isOk()).andExpect(content().string("1000"));
	}

	@Test
	public void testImportarContas() throws Exception {
		mockMvc.perform(multipart("/contas/importar").file("file", "arquivo.csv".getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isOk())
				.andExpect(content().string("Contas importadas com sucesso!"));
	}
}

package com.lyncas.desafio.contasapagar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lyncas.desafio.contasapagar.model.Conta;
import com.lyncas.desafio.contasapagar.service.ContaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador responsável pelas operações relacionadas às contas a pagar.
 * Permite o cadastro, atualização, alteração de situação, listagem, consulta de
 * total pago e importação de contas a partir de um arquivo CSV.
 *
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@RestController
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	/**
	 * Endpoint para cadastrar uma nova conta.
	 *
	 * @param conta objeto Conta a ser cadastrado
	 * @return a resposta contendo a nova conta criada
	 */
	@PostMapping
	public ResponseEntity<Conta> cadastrarConta(@RequestBody Conta conta) {
		Conta novaConta = contaService.criarConta(conta);
		return ResponseEntity.ok(novaConta);
	}

	/**
	 * Endpoint para atualizar uma conta existente.
	 *
	 * @param id    identificador da conta a ser atualizada
	 * @param conta objeto Conta com os novos dados
	 * @return a resposta contendo a conta atualizada
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
		Conta contaAtualizada = contaService.atualizarConta(id, conta);
		return ResponseEntity.ok(contaAtualizada);
	}

	/**
	 * Endpoint para alterar a situação de uma conta.
	 *
	 * @param id       identificador da conta cuja situação será alterada
	 * @param situacao nova situação da conta
	 * @return uma resposta vazia indicando sucesso da operação
	 */
	@PatchMapping("/{id}/situacao")
	public ResponseEntity<Void> alterarSituacao(@PathVariable Long id, @RequestBody String situacao) {
		contaService.alterarSituacao(id, situacao);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Endpoint para listar as contas filtradas por data de vencimento e/ou
	 * descrição.
	 *
	 * @param pageable             objeto de paginação para controlar os resultados
	 * @param dataVencimentoInicio data inicial do filtro de vencimento
	 * @param dataVencimentoFim    data final do filtro de vencimento
	 * @param descricao            termo de busca para a descrição da conta
	 * @return a resposta contendo a página de contas filtradas
	 */
	@GetMapping
	public ResponseEntity<Page<Conta>> listarContas(Pageable pageable,
			@RequestParam(required = false) LocalDate dataVencimentoInicio,
			@RequestParam(required = false) LocalDate dataVencimentoFim,
			@RequestParam(required = false) String descricao) {
		Page<Conta> contas = contaService.listarContas(pageable, dataVencimentoInicio, dataVencimentoFim, descricao);
		return ResponseEntity.ok(contas);
	}

	/**
	 * Endpoint para obter o valor total pago em contas entre duas datas.
	 *
	 * @param dataInicio data inicial para a busca de contas pagas
	 * @param dataFim    data final para a busca de contas pagas
	 * @return a resposta contendo o valor total pago
	 */
	@GetMapping("/total-pago")
	public ResponseEntity<BigDecimal> obterValorTotalPago(@RequestParam LocalDate dataInicio,
			@RequestParam LocalDate dataFim) {
		BigDecimal total = contaService.obterValorTotalPago(dataInicio, dataFim);
		return ResponseEntity.ok(total);
	}

	/**
	 * Endpoint para importar contas a partir de um arquivo CSV.
	 *
	 * @param file arquivo CSV contendo as contas a serem importadas
	 * @return uma mensagem de sucesso ou erro dependendo do resultado da importação
	 */
	@PostMapping("/importar")
	public ResponseEntity<String> importarContas(@RequestParam("file") MultipartFile file) {
		try {
			contaService.importarContas(file);
			return ResponseEntity.ok("Contas importadas com sucesso!");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao importar contas: " + e.getMessage());
		}
	}
}

package com.lyncas.desafio.contasapagar.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lyncas.desafio.contasapagar.model.Conta;
import com.lyncas.desafio.contasapagar.repository.ContaRepository;

/**
 * Classe de serviço responsável por gerenciar operações relacionadas às contas
 * a pagar, como criação, atualização, alteração de situação, listagem e
 * importação de contas via CSV.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 *
 * @author juliano.ezequiel
 */
@Service
public class ContaService {

	/**
	 * Repositório de contas a pagar para realizar operações com o banco de dados
	 */
	@Autowired
	private ContaRepository contaRepository;

	/**
	 * Cria uma nova conta a pagar.
	 *
	 * @param conta a conta a ser criada
	 * @return a conta criada e salva no banco de dados
	 */
	public Conta criarConta(Conta conta) {
		return contaRepository.save(conta);
	}

	/**
	 * Atualiza uma conta a pagar existente.
	 *
	 * @param id    o identificador da conta a ser atualizada
	 * @param conta os novos dados da conta
	 * @return a conta atualizada e salva no banco de dados
	 */
	public Conta atualizarConta(Long id, Conta conta) {
		conta.setId(id);
		return contaRepository.save(conta);
	}

	/**
	 * Altera a situação de uma conta a pagar com base no seu identificador.
	 *
	 * @param id       o identificador da conta
	 * @param situacao a nova situação da conta (por exemplo: "Paga", "Em aberto")
	 */
	public void alterarSituacao(Long id, String situacao) {
		Optional<Conta> contaOpt = contaRepository.findById(id);
		contaOpt.ifPresent(conta -> {
			conta.setSituacao(situacao);
			contaRepository.save(conta);
		});
	}

	/**
	 * Lista as contas com base nos filtros de data de vencimento e descrição, de
	 * forma paginada.
	 *
	 * @param pageable             informações sobre paginação
	 * @param dataVencimentoInicio filtro para a data de vencimento inicial
	 * @param dataVencimentoFim    filtro para a data de vencimento final
	 * @param descricao            filtro para descrição da conta
	 * @return página contendo as contas que correspondem aos filtros
	 */
	public Page<Conta> listarContas(Pageable pageable, LocalDate dataVencimentoInicio, LocalDate dataVencimentoFim,
			String descricao) {
		return contaRepository.findByDataVencimentoBetweenAndDescricaoContaining(dataVencimentoInicio,
				dataVencimentoFim, descricao, pageable);
	}

	/**
	 * Obtém o valor total pago em contas em um intervalo de datas.
	 *
	 * @param dataInicio data de início do intervalo
	 * @param dataFim    data de fim do intervalo
	 * @return o valor total pago dentro do intervalo de datas
	 */
	public BigDecimal obterValorTotalPago(LocalDate dataInicio, LocalDate dataFim) {
		List<Conta> contasPagas = contaRepository.findByDataPagamentoBetween(dataInicio, dataFim);
		BigDecimal totalPago = contasPagas.stream().map(Conta::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalPago;
	}

	/**
	 * Importa contas a partir de um arquivo CSV. Cada registro no arquivo será
	 * transformado em uma nova conta a ser criada.
	 *
	 * @param arquivo o arquivo CSV contendo os dados das contas
	 * @throws IOException caso ocorra um erro na leitura do arquivo
	 */
	public void importarContas(MultipartFile arquivo) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
		Iterable<CSVRecord> registros = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

		for (CSVRecord registro : registros) {
			try {
				LocalDate dataVencimento = LocalDate.parse(registro.get("data_vencimento"));

				// Verifica se a coluna "data_pagamento" está presente e tenta fazer o parse,
				// caso contrário, usa null
				LocalDate dataPagamento = null;
				if (registro.isSet("data_pagamento") && !registro.get("data_pagamento").isEmpty()) {
					dataPagamento = LocalDate.parse(registro.get("data_pagamento"));
				}

				BigDecimal valor = new BigDecimal(registro.get("valor"));
				String descricao = registro.get("descricao");
				String situacao = registro.get("situacao");

				Conta conta = new Conta();
				conta.setDataVencimento(dataVencimento);
				conta.setDataPagamento(dataPagamento);
				conta.setValor(valor);
				conta.setDescricao(descricao);
				conta.setSituacao(situacao);

				criarConta(conta); // Salva a conta criada
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

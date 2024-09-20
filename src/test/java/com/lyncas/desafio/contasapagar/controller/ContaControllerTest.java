package com.lyncas.desafio.contasapagar.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.lyncas.desafio.contasapagar.dto.ValorTotalPagoResponse;
import com.lyncas.desafio.contasapagar.model.Conta;
import com.lyncas.desafio.contasapagar.service.ContaService;

/**
 * Testes unitários para a classe {@link ContaController}. Utiliza Mockito para
 * simular as interações com o serviço e validar as operações do controlador.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
@SpringBootTest
@ActiveProfiles("test")
class ContaControllerTest {

    @InjectMocks
    private ContaController contaController;

    @Mock
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o cadastro de uma nova conta.
     */
    @Test
    void testCadastrarConta() {
        // Configura dados de exemplo
        Conta conta = new Conta();
        conta.setDescricao("Conta de luz");

        // Simula o comportamento do serviço
        doReturn(conta).when(contaService).criarConta(any(Conta.class));

        // Executa o método a ser testado
        ResponseEntity<Conta> response = contaController.cadastrarConta(conta);

        // Verifica o resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Conta de luz", response.getBody().getDescricao());
    }

    /**
     * Testa a atualização de uma conta existente.
     */
    @Test
    void testAtualizarConta() {
        // Configura dados de exemplo
        Long id = 1L;
        Conta conta = new Conta();
        conta.setDescricao("Conta de luz");

        // Simula o comportamento do serviço
        doReturn(conta).when(contaService).atualizarConta(any(Long.class), any(Conta.class));

        // Executa o método a ser testado
        ResponseEntity<Conta> response = contaController.atualizarConta(id, conta);

        // Verifica o resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Conta de luz", response.getBody().getDescricao());
    }

    /**
     * Testa a alteração da situação de uma conta.
     */
    @Test
    void testAlterarSituacao() {
        // Configura dados de exemplo
        Long id = 1L;
        String situacao = "Paga";

        // Simula o comportamento do serviço (não precisa retornar nada)
        doNothing().when(contaService).alterarSituacao(any(Long.class), any(String.class));

        // Executa o método a ser testado
        ResponseEntity<Void> response = contaController.alterarSituacao(id, situacao);

        // Verifica o resultado
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Testa a listagem de contas filtradas.
     */
    @Test
    void testListarContas() {
        // Configura dados de exemplo
        Pageable pageable = mock(Pageable.class);
        Conta conta = new Conta();
        conta.setDescricao("Conta de luz");
        Page<Conta> pageContas = new PageImpl<>(List.of(conta));

        // Simula o comportamento do serviço
        doReturn(pageContas).when(contaService).listarContas(any(Pageable.class), any(), any(), any());

        // Executa o método a ser testado
        ResponseEntity<Page<Conta>> response = contaController.listarContas(pageable, null, null, null);

        // Verifica o resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
    }

    /**
     * Testa a obtenção do valor total pago.
     */
    @Test
    void testObterValorTotalPago() {
        // Configura dados de exemplo
        LocalDate dataInicio = LocalDate.now().minusDays(30);
        LocalDate dataFim = LocalDate.now();
        BigDecimal total = BigDecimal.valueOf(100.00);
        ValorTotalPagoResponse responseValor = new ValorTotalPagoResponse(total);

        // Simula o comportamento do serviço
        doReturn(total).when(contaService).obterValorTotalPago(dataInicio, dataFim);

        // Executa o método a ser testado
        ResponseEntity<ValorTotalPagoResponse> response = contaController.obterValorTotalPago(dataInicio, dataFim);

        // Verifica o resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(total, response.getBody().getValorTotalPago());
    }

}

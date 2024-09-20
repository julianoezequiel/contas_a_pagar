package com.lyncas.desafio.contasapagar.dto;

import java.math.BigDecimal;

/**
 * Classe de resposta para encapsular o valor total pago em contas.
 * 
 * @version 1.0.0 data 20/09/2024
 * @since 1.0.0 data 20/09/2024
 * 
 * @author juliano.ezequiel
 */
public class ValorTotalPagoResponse {

	/**
	 * O valor total pago em contas entre duas datas.
	 */
	private BigDecimal valorTotalPago;

	/**
	 * Construtor que inicializa o valor total pago.
	 *
	 * @param valorTotalPago o valor total pago em contas
	 */
	public ValorTotalPagoResponse(BigDecimal valorTotalPago) {
		this.valorTotalPago = valorTotalPago;
	}

	/**
	 * Retorna o valor total pago.
	 *
	 * @return o valor total pago
	 */
	public BigDecimal getValorTotalPago() {
		return valorTotalPago;
	}

	/**
	 * Define o valor total pago.
	 *
	 * @param valorTotalPago o valor total pago a ser definido
	 */
	public void setValorTotalPago(BigDecimal valorTotalPago) {
		this.valorTotalPago = valorTotalPago;
	}
}

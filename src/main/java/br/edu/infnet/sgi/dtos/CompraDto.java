package br.edu.infnet.sgi.dtos;

import java.math.BigDecimal;

public class CompraDto {

	public long id;
	public EventoDto evento;
	public int qtdIngressos;
	public UsuarioDto cliente;
	public String data;
	public BigDecimal total;
	public String metodoPagamento;
}

package br.edu.infnet.sgi.dtos;

import java.math.BigDecimal;

public class EventoDto {

	public long id;
	public String nome;
	public String tipoEvento;
	public UsuarioDto organizador;
	public int lotacao;
	public int ingressosVendidos;
	public BigDecimal preco;
	public String endereco;
	public String data;
	public String descricao;
}

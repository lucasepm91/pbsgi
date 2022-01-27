package br.edu.infnet.sgi.exceptions;

public class EntradaInvalidaException extends RuntimeException {		
	
	private static final long serialVersionUID = 1L;

	public EntradaInvalidaException(String erro)
	{
		super(erro);
	}
}

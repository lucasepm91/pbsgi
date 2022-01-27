package br.edu.infnet.sgi.exceptions;

public class NaoEncontradoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NaoEncontradoException(String erro)
	{
		super(erro);
	}
}

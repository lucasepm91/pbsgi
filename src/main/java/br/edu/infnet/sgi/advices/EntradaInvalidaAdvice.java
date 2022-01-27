package br.edu.infnet.sgi.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.infnet.sgi.exceptions.EntradaInvalidaException;

@ControllerAdvice
public class EntradaInvalidaAdvice {

	@ResponseBody
	@ExceptionHandler(EntradaInvalidaException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	String entradaInvalidaHandler(EntradaInvalidaException exception)
	{
		return exception.getMessage();
	}
}

package br.edu.infnet.sgi.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.infnet.sgi.exceptions.NaoEncontradoException;

@ControllerAdvice
public class NaoEncontradoAdvice {

	@ResponseBody
	@ExceptionHandler(NaoEncontradoException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	String naoEncontradoHandler(NaoEncontradoException exception)
	{
		return exception.getMessage();
	}
}

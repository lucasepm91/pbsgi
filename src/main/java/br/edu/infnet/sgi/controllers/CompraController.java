package br.edu.infnet.sgi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.sgi.dtos.CompraDto;
import br.edu.infnet.sgi.services.CompraService;

@RestController
@RequestMapping("/pbsgi")
@CrossOrigin(origins = "http://localhost:4000")
public class CompraController {

	@Autowired
	private CompraService compraService;
	
	@PostMapping("/compra")
	public ResponseEntity<CompraDto> cadastrarCompra(@RequestBody CompraDto novaCompra) {
	    return ResponseEntity.ok(compraService.processarCompra(novaCompra));
	}
	
	@GetMapping("/compra/historico/{id}")
	public ResponseEntity<List<CompraDto>> pesquisarHistorico(@PathVariable long id)
	{
		return ResponseEntity.ok(compraService.obterHistoricoCompras(id));
	}
	
	@DeleteMapping("/compra/{id}")
	public void deletarCompra(@PathVariable long id)
	{
		compraService.removerCompra(id);
	}
}

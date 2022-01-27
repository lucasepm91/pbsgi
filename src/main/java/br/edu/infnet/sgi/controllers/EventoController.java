package br.edu.infnet.sgi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.sgi.dtos.EventoDto;
import br.edu.infnet.sgi.services.EventoService;

@RestController
@RequestMapping("/pbsgi")
@CrossOrigin(origins = "http://localhost:4000")
public class EventoController {

	@Autowired
	private EventoService eventoService;
	
	@PostMapping("/eventos")
	public ResponseEntity<EventoDto> cadastrarEvento(@RequestBody EventoDto novoEvento) {
	    return ResponseEntity.ok(eventoService.criarEvento(novoEvento));
	}	  
	  
	@GetMapping("/eventos/{id}")
	public ResponseEntity<EventoDto> buscarEvento(@PathVariable long id) {
	    
		return ResponseEntity.ok(eventoService.buscarEvento(id));
	}
	  
	@GetMapping("/eventos/nome/{nome}")
	public ResponseEntity<List<EventoDto>> buscarPorNome(@PathVariable String nome) {
	    
		return ResponseEntity.ok(eventoService.buscarEventoPorNome(nome));
	}
	  
	@GetMapping("/eventos/tipo/{tipo}")
	public ResponseEntity<List<EventoDto>> buscarPorTipo(@PathVariable String tipo) {
	    
		return ResponseEntity.ok(eventoService.buscarEventosPorTipo(tipo));
	}
	  
	@GetMapping("/eventos/historico/{id}")
	public ResponseEntity<List<EventoDto>> pesquisarHistorico(@PathVariable long id) {
	    
		return ResponseEntity.ok(eventoService.consultarHistoricoEventos(id));
	}

	@PutMapping("/eventos/{id}")
	public ResponseEntity<EventoDto> atualizarEvento(@RequestBody EventoDto eventoAtualizado, @PathVariable long id) {
	    
		return ResponseEntity.ok(eventoService.atualizarEvento(eventoAtualizado, id));
	}

	@DeleteMapping("/eventos/{id}")
	public void deletarEvento(@PathVariable long id) {
		eventoService.deletarEvento(id);
	}
}

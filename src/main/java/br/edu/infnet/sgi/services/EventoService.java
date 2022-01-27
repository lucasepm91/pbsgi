package br.edu.infnet.sgi.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.sgi.dtos.EventoDto;
import br.edu.infnet.sgi.dtos.UsuarioDto;
import br.edu.infnet.sgi.exceptions.EntradaInvalidaException;
import br.edu.infnet.sgi.exceptions.NaoEncontradoException;
import br.edu.infnet.sgi.models.Evento;
import br.edu.infnet.sgi.models.Usuario;
import br.edu.infnet.sgi.repositories.EventoRepository;
import br.edu.infnet.sgi.repositories.UsuarioRepository;

@Service
public class EventoService {

	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EntityConverterService conversor;
	
	public EventoDto criarEvento(EventoDto novoEvento)
	{
		String erro = validarDadosEvento(novoEvento);
		
		if(erro != null)
		{
			throw new EntradaInvalidaException(erro);
		}
		else
		{
			List<EventoDto> existente = buscarEventoPorNome(novoEvento.nome);
			if(existente != null)
			{
				for(EventoDto ev : existente)
				{
					if(ev.data.equals(novoEvento.data) && ev.endereco.equals(novoEvento.endereco))
						throw new EntradaInvalidaException("Um evento já foi cadastrado com esse nome, data e local");
				}
			}			
		}
		
		Evento evento = conversor.converterDtoParaEvento(novoEvento);
		evento.getOrganizador().setId(novoEvento.organizador.id);
		eventoRepository.save(evento);
		return novoEvento;
	}
	
	public EventoDto buscarEvento(long id)
	{
		Optional<Evento> evento = eventoRepository.findById(id);
		
		if(evento.isPresent())
		{
			return conversor.converterEventoParaDto(evento.get());
		}
		
		return null;
	}
	
	public List<EventoDto> buscarEventoPorNome(String nomeEvento)
	{
		List<Evento> eventos = eventoRepository.findByNome(nomeEvento);
		List<EventoDto> eventosDto = null;
		
		if(eventos != null && eventos.size() > 0)
		{
			eventosDto = new ArrayList<EventoDto>();
			
			for(Evento evento : eventos)
			{
				eventosDto.add(conversor.converterEventoParaDto(evento));
			}
		}		
		
		return eventosDto;
	}
	
	public List<EventoDto> buscarEventosPorTipo(String tipoEvento)
	{
		List<Evento> eventos = eventoRepository.findByTipoEvento(tipoEvento);
		List<EventoDto> eventosDto = null;
		
		if(eventos != null && eventos.size() > 0)
		{
			eventosDto = new ArrayList<EventoDto>();
			for(Evento evento : eventos)
			{
				eventosDto.add(conversor.converterEventoParaDto(evento));
			}
		}		
		
		return eventosDto;
	}
	
	public List<EventoDto> consultarHistoricoEventos(long id)
	{
		List<Evento> eventos = eventoRepository.obterHistoricoEventos(id);
		List<EventoDto> eventosDto = null;
		
		if(eventos != null && eventos.size() > 0)
		{
			eventosDto = new ArrayList<EventoDto>();
			
			for(Evento evento : eventos)
			{
				eventosDto.add(conversor.converterEventoParaDto(evento));
			}
		}
		
		return eventosDto;
	}
	
	public EventoDto atualizarEvento(EventoDto eventoAtualizado, long id)
	{		
		String erro = validarDadosEvento(eventoAtualizado);
		Evento evento = null;
		
		if(erro != null)
		{			
			throw new EntradaInvalidaException(erro);
		}
		else
		{
			Optional<Evento> existente = eventoRepository.findById(id);
			if(!existente.isPresent())
				throw new NaoEncontradoException("Não foi possível encontrar o evento com os parâmetros informados");
			else
				evento = existente.get();
		}
		
		evento.setId(id);
		evento.setNome(eventoAtualizado.nome);
		evento.setDescricao(eventoAtualizado.descricao);
		evento.setEndereco(eventoAtualizado.endereco);
		evento.setTipoEvento(eventoAtualizado.tipoEvento);
		evento.setIngressosVendidos(eventoAtualizado.ingressosVendidos);
		evento.setLotacao(eventoAtualizado.lotacao);
		evento.setPreco(eventoAtualizado.preco);
		
		Optional<Usuario> organizador = usuarioRepository.findById(eventoAtualizado.organizador.id);
		evento.setOrganizador(organizador.get());
		
		eventoRepository.save(evento);
		
		return eventoAtualizado;
	}
	
	public void deletarEvento(long id)
	{
		eventoRepository.deleteById(id);
	}
	
	private String validarDadosEvento(EventoDto evento) {
		String nome = evento.nome;
		String tipoEvento = evento.tipoEvento;
		String descricao = evento.descricao;
		int lotacao = evento.lotacao;
		int ingressosVendidos = evento.ingressosVendidos;
		String endereco = evento.endereco;
		UsuarioDto organizador = evento.organizador;
		BigDecimal preco = evento.preco;
		String data = evento.data;
		
		if(nome == null || nome.length() < 2 || nome.length() > 80)
			return "O nome do evento não pode ser nulo e deve possuir de 2 a 80 caracteres";
		
		if(tipoEvento == null || (!tipoEvento.equals("filme") && !tipoEvento.equals("show") && !tipoEvento.equals("peca")))
			return "O tipo do evento não pode ser nulo e deve ser filme, show ou peca";
		
		if(descricao == null || descricao.length() < 2 || descricao.length() > 300)
			return "A descrição do evento não pode ser nula e deve possuir de 2 a 300 caracteres";
		
		if(lotacao <= 0)
			return "Lotação do evento não pode ser menor ou igual a zero";
		
		if(ingressosVendidos < 0)
			return "Quantidade de ingressos vendidos para o evento não pode ser negativa";
		
		if(endereco == null || endereco.length() < 5 || endereco.length() > 100)
			return "O endereço do evento não pode ser nulo e deve possuir de 5 a 100 caracteres";
		
		if(data == null)
			return "A data do evento não pode ser nula";
		else
		{
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				dateFormat.setLenient(false);				
				dateFormat.parse(data);
				
	        } catch (ParseException e) {
	            return "A data do evento deve estar no formato dd/MM/yyyy HH:mm";
	        }
		}
		
		if(organizador == null)
			return "O organizador do evento não pode ser nulo";
		
		if(preco.compareTo(BigDecimal.ZERO) < 0)
			return "O preço do ingresso não pode ser negativo";
		
		return null;
	}
}

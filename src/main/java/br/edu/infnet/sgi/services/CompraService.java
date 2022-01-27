package br.edu.infnet.sgi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.sgi.dtos.CompraDto;
import br.edu.infnet.sgi.models.Compra;
import br.edu.infnet.sgi.models.Evento;
import br.edu.infnet.sgi.repositories.CompraRepository;
import br.edu.infnet.sgi.repositories.EventoRepository;

@Service
public class CompraService {
		
	@Autowired
	CompraRepository compraRepository;
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	EntityConverterService conversor;
	
	public CompraDto processarCompra(CompraDto compraDto)
	{		
		Compra compra = conversor.converterDtoParaCompra(compraDto);
		
		compra.getCliente().setId(compraDto.cliente.id);
		
		Evento evento = eventoRepository.findById(compraDto.evento.id).get();
		int ingressosVendidos = evento.getIngressosVendidos() + compra.getQtdIngressos();
		evento.setIngressosVendidos(ingressosVendidos);
		
		compra.setEvento(evento);
		
		compraRepository.save(compra);
		eventoRepository.save(evento);
		
		return compraDto;
	}
	
	public List<CompraDto> obterHistoricoCompras(long id)
	{
		List<Compra> compras = compraRepository.obterHistoricoCompras(id);
		ArrayList<CompraDto> comprasDto = null;
		
		if(compras != null && compras.size() > 0)
		{
			comprasDto = new ArrayList<CompraDto>();
			
			for(Compra compra : compras)
			{
				comprasDto.add(conversor.converterCompraParaDto(compra));
			}
		}		
		
		return comprasDto;
	}
	
	public void removerCompra(long id)
	{
		Optional<Compra> compra = compraRepository.findById(id);
		
		if(compra.isPresent())
		{
			Evento evento = compra.get().getEvento();
			int qtdVendidos = evento.getIngressosVendidos();
			
			evento.setIngressosVendidos(qtdVendidos - compra.get().getQtdIngressos());
			
			compraRepository.deleteById(id);
			eventoRepository.save(evento);
		}
	}
}

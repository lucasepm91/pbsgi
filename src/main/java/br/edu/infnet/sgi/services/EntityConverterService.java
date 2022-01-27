package br.edu.infnet.sgi.services;

import org.springframework.stereotype.Service;

import br.edu.infnet.sgi.dtos.CompraDto;
import br.edu.infnet.sgi.dtos.EventoDto;
import br.edu.infnet.sgi.dtos.UsuarioDto;
import br.edu.infnet.sgi.models.Compra;
import br.edu.infnet.sgi.models.Evento;
import br.edu.infnet.sgi.models.Usuario;

@Service
public class EntityConverterService {

	public UsuarioDto converterUsuarioParaDto(Usuario usuario)
	{
		UsuarioDto usuarioDto = new UsuarioDto();
		
		usuarioDto.id = usuario.getId();
		usuarioDto.nome = usuario.getNome();
		usuarioDto.cpfCnpj = usuario.getCpfCnpj();
		usuarioDto.email = usuario.getEmail();
		usuarioDto.endereco = usuario.getEndereco();
		usuarioDto.tipoUsuario = usuario.getTipoUsuario();
		usuarioDto.saldoCarteira = usuario.getSaldoCarteira();
		
		return usuarioDto;
	}
	
	public Usuario converterDtoParaUsuario(UsuarioDto usuarioDto)
	{
		Usuario usuario = new Usuario();
		
		usuario.setNome(usuarioDto.nome);
		usuario.setCpfCnpj(usuarioDto.cpfCnpj);
		usuario.setEmail(usuarioDto.email);
		usuario.setEndereco(usuarioDto.endereco);
		usuario.setTipoUsuario(usuarioDto.tipoUsuario);
		usuario.setSaldoCarteira(usuarioDto.saldoCarteira);
		
		return usuario;
	}
	
	public EventoDto converterEventoParaDto(Evento evento)
	{
		EventoDto eventoDto = new EventoDto();
		
		eventoDto.id = evento.getId();
		eventoDto.nome = evento.getNome();
		eventoDto.descricao = evento.getDescricao();
		eventoDto.endereco = evento.getEndereco();
		eventoDto.tipoEvento = evento.getTipoEvento();
		eventoDto.ingressosVendidos = evento.getIngressosVendidos();
		eventoDto.lotacao = evento.getLotacao();
		eventoDto.preco = evento.getPreco();
		eventoDto.data = evento.getData();
		eventoDto.organizador = converterUsuarioParaDto(evento.getOrganizador());
		
		return eventoDto;
	}
	
	public Evento converterDtoParaEvento(EventoDto eventoDto)
	{
		Evento evento = new Evento();
		
		evento.setNome(eventoDto.nome);
		evento.setDescricao(eventoDto.descricao);
		evento.setEndereco(eventoDto.endereco);
		evento.setTipoEvento(eventoDto.tipoEvento);
		evento.setIngressosVendidos(eventoDto.ingressosVendidos);
		evento.setLotacao(eventoDto.lotacao);
		evento.setPreco(eventoDto.preco);
		evento.setData(eventoDto.data);
		evento.setOrganizador(converterDtoParaUsuario(eventoDto.organizador));
		
		return evento;
	}
	
	public CompraDto converterCompraParaDto(Compra compra)
	{
		CompraDto compraDto = new CompraDto();
		
		compraDto.id = compra.getId();
		compraDto.qtdIngressos = compra.getQtdIngressos();
		compraDto.data = compra.getData();
		compraDto.total = compra.getTotal();
		compraDto.metodoPagamento = compra.getMetodoPagamento();
		compraDto.cliente = converterUsuarioParaDto(compra.getCliente());
		compraDto.evento = converterEventoParaDto(compra.getEvento());
		
		return compraDto;
	}
	
	public Compra converterDtoParaCompra(CompraDto compraDto)
	{
		Compra compra = new Compra();
		
		compra.setQtdIngressos(compraDto.qtdIngressos);
		compra.setData(compraDto.data);
		compra.setMetodoPagamento(compraDto.metodoPagamento);
		compra.setTotal(compraDto.total);
		compra.setCliente(converterDtoParaUsuario(compraDto.cliente));
		compra.setEvento(converterDtoParaEvento(compraDto.evento));
		
		return compra;
	}
}

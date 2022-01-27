package br.edu.infnet.sgi.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.infnet.sgi.dtos.UsuarioDto;
import br.edu.infnet.sgi.exceptions.EntradaInvalidaException;
import br.edu.infnet.sgi.exceptions.NaoEncontradoException;
import br.edu.infnet.sgi.models.Usuario;
import br.edu.infnet.sgi.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EntityConverterService conversor;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	public UsuarioDto criarUsuario(UsuarioDto novoUsuario)
	{		
		String erro = validarUsuario(novoUsuario);
		
		if(erro != null)
		{
			throw new EntradaInvalidaException(erro);
		}
		else
		{
			UsuarioDto existente = buscarUsuarioPorEmail(novoUsuario.email);
			if(existente != null)
				throw new EntradaInvalidaException("Um usuário já foi cadastrado com esse email");
		}
		
		Usuario usuario = conversor.converterDtoParaUsuario(novoUsuario);
		usuario.setPassword(passwordEncoder.encode(novoUsuario.password));
		
		usuarioRepository.save(usuario);
		
		return novoUsuario;
	}
	
	public UsuarioDto buscarUsuario(long id)
	{
		Optional<Usuario> usuario = usuarioRepository.findById(id);	
		
		if(usuario.isPresent())
		{
			UsuarioDto usuarioDto = conversor.converterUsuarioParaDto(usuario.get());			
			return usuarioDto;
		}
		
		return null;
		
	}
	
	public UsuarioDto buscarUsuarioPorEmail(String email)
	{
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(usuario.isPresent())
		{
			UsuarioDto usuarioDto = conversor.converterUsuarioParaDto(usuario.get());
			return usuarioDto;
		}
		
		return null;
	}
	
	public UsuarioDto atualizarUsuario(UsuarioDto usuarioAtualizado, long id)
	{		
		String erro = validarUsuario(usuarioAtualizado);
		Usuario usuario = null;
		
		if(erro != null)
		{
			throw new EntradaInvalidaException(erro);
		}
		else
		{
			Optional<Usuario> existente = usuarioRepository.findById(usuarioAtualizado.id);
			if(!existente.isPresent())
				throw new NaoEncontradoException("Não foi possível encontrar o usuário com os parâmetros informados");
			else
				usuario = existente.get();
		}		
		
		usuario.setId(id);
		usuario.setNome(usuarioAtualizado.nome);
		usuario.setCpfCnpj(usuarioAtualizado.cpfCnpj);
		usuario.setEmail(usuarioAtualizado.email);
		usuario.setEndereco(usuarioAtualizado.endereco);
		usuario.setTipoUsuario(usuarioAtualizado.tipoUsuario);
		usuario.setSaldoCarteira(usuarioAtualizado.saldoCarteira);
		
		usuarioRepository.save(usuario);
		return usuarioAtualizado;
	}
	
	public void deletarUsuario(long id)
	{
		usuarioRepository.deleteById(id);
	}
	
	private String validarUsuario(UsuarioDto usuario)
	{
		if(usuario.nome == null || usuario.nome.length() < 2 || usuario.nome.length() > 120)
			return "Nome do usuário não pode ser nulo e deve ter entre 2 e 120 caracteres";
		
		if(usuario.cpfCnpj == null || (usuario.cpfCnpj.length() != 11 && usuario.cpfCnpj.length() != 14))
			return "Cpf ou Cnpj não pode ser nulo e deve ter 11 ou 14 caracteres";
		
		if(usuario.email == null || usuario.email.length() < 3 || usuario.email.length() > 100 || !usuario.email.contains("@"))
			return "Email não pode ser nulo, deve ser válido e deve ter entre 3 e 100 caracteres";
		
		if(usuario.endereco == null || usuario.endereco.length() < 2 || usuario.endereco.length() > 100)
			return "Endereço não pode ser nulo e deve ter entre 2 e 100 caracteres";
		
		if(usuario.tipoUsuario == null || (!usuario.tipoUsuario.equals("cliente") && !usuario.tipoUsuario.equals("organizador")))
			return "Tipo de usuário não pode ser nulo e deve ser cliente ou organizador";
		
		if(usuario.saldoCarteira.compareTo(BigDecimal.ZERO) == -1)
			return "Saldo carteira não pode ser negativo";
		
		if(usuario.password == null || usuario.password.length() < 4 || usuario.nome.length() > 20)
			return "Senha não pode ser nula e deve ter entre 4 e 20 caracteres";
		
		return null;
	}
}

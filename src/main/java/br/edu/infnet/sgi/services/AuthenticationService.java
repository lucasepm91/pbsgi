package br.edu.infnet.sgi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.infnet.sgi.models.Usuario;
import br.edu.infnet.sgi.repositories.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = repository.findByEmail(username);
		
		if(usuario.isPresent())
			return usuario.get();
		
		throw new UsernameNotFoundException("User not found");
	}	

}
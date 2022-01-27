package br.edu.infnet.sgi.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.infnet.sgi.models.Usuario;
import br.edu.infnet.sgi.repositories.UsuarioRepository;
import br.edu.infnet.sgi.services.TokenService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {	
	
	private final TokenService tokenService;	
	private final UsuarioRepository repository;
	
	public TokenAuthenticationFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenFromHeader = getTokenFromHeader(request);
		boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
		if(tokenValid) {
			this.authenticate(tokenFromHeader);
		}

		filterChain.doFilter(request, response);
	}

	private void authenticate(String tokenFromHeader) {
		long id = tokenService.getTokenId(tokenFromHeader);
		
		Optional<Usuario> optionalUser = repository.findById(id);
		
		if(optionalUser.isPresent()) {
			
			Usuario usuario = optionalUser.get();
			
			HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		    authorities.add(new SimpleGrantedAuthority("ADMIN")); 
			
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}

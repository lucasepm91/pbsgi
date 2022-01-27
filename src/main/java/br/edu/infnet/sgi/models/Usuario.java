package br.edu.infnet.sgi.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "tipo_usuario")
	private String tipoUsuario;
	
	@Column(name = "saldo_carteira")
	private BigDecimal saldoCarteira;
	
	@Column(name = "password")
	private String password;	

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,
	        orphanRemoval = true)	
	private Set<Compra> compra;
	
	@OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL,
	        orphanRemoval = true)	
	private Set<Evento> evento;	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {				
		this.cpfCnpj = cpfCnpj;		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {		
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public BigDecimal getSaldoCarteira() {
		return saldoCarteira;
	}

	public void setSaldoCarteira(BigDecimal saldoCarteira) {
		this.saldoCarteira = saldoCarteira;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

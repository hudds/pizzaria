package br.com.pizzaria.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "TB_USUARIOS")
public class Usuario implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, name="EMAIL")
	private String email;

	@Column(unique = true, name="NOME_DE_USUARIO")
	private String nomeDeUsuario;

	@Column(name="SENHA")
	private String senha;

	@OneToMany
	@JoinTable(name="TB_USUARIO_PEDIDOS",
	joinColumns = @JoinColumn(name="USUARIO_ID"),
	inverseJoinColumns = @JoinColumn(name="PEDIDO_ID"))
	private List<Pedido> pedidos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TB_USUARIOS_ROLES", 
	joinColumns = @JoinColumn(name = "USUARIO_ID"),
	inverseJoinColumns = @JoinColumn(name= "AUTHORITY"))
	private Set<Role> roles;

	@Column(name="NOME")
	private String nome;
	
	@OneToOne
	@JoinColumn(name="ENDERECO_ID")
	private Endereco endereco;
	
	@Column(name = "TELEFONE")
	private String telefone;
	
	@Column(name = "CELULAR")
	private String celular;
	
	
	public Usuario() {
		this.roles =  new HashSet<Role>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}

	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		if (roles ==  null) {
			this.roles = new HashSet<Role>();
			return;
		}
		this.roles = roles;
	}
	
	public boolean addRole(Role role) {
		return this.roles.add(role);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.nomeDeUsuario;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void addPedido(Pedido pedido) {
		if(this.pedidos == null) {
			pedidos = new ArrayList<Pedido>();
		}
		pedidos.add(pedido);
		
	}


}

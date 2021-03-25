package org.ishare.app.domains;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Rol {

	
	//==================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	@OneToMany(mappedBy = "rol",cascade = CascadeType.ALL)
	private Collection<Entidad> entidades;
	
	
	public Rol() {
		this.entidades=new ArrayList<>();
	}
	
	public Rol(String nombre) {
		this.nombre=nombre;
		this.entidades=new ArrayList<>();
	}
	
	public Collection<Entidad> getEntidades(){
		return entidades;
	}
	
	public void setEntidades(Collection<Entidad> entidades) {
		this.entidades=entidades;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}

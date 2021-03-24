package org.ishare.app.domains;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Ubicacion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUbicacion;
	
	@Column(unique=true)
	private String direccion;
	
	@Column
	private Integer plazasTotales;
	
	@OneToMany(mappedBy = "iniciaEn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Alquiler> iniciadosEn;
	
	@OneToMany(mappedBy = "finalizaEn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Alquiler> finalizadosEn;
	
	public Ubicacion() {
		
	}
	
	public Ubicacion(String direccion,Integer plazasTotales) {
		super();
		this.direccion=direccion;
		this.plazasTotales=plazasTotales;
	}

	
	public Collection<Alquiler> getIniciadosEn() {
		return iniciadosEn;
	}

	public void setIniciadosEn(Collection<Alquiler> iniciadosEn) {
		this.iniciadosEn = iniciadosEn;
	}
	
	public Collection<Alquiler> getFinalizadosEn() {
		return finalizadosEn;
	}

	public void setFinalizadosEn(Collection<Alquiler> finalizadosEn) {
		this.finalizadosEn = finalizadosEn;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getPlazasTotales() {
		return plazasTotales;
	}

	public void setPlazasTotales(Integer plazasTotales) {
		this.plazasTotales = plazasTotales;
	}
	
}

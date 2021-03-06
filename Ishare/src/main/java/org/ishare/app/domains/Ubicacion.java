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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Ubicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUbicacion;

	@Column(unique = true)
	private String direccion;

	@Column
	private Integer plazasTotales;
	@JsonManagedReference
	@OneToMany(mappedBy = "iniciaEn", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Collection<Alquiler> iniciadosEn;
	@JsonManagedReference
	@OneToMany(mappedBy = "finalizaEn", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Collection<Alquiler> finalizadosEn;
	@JsonBackReference
	@OneToMany(mappedBy = "ubicacion", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Collection<Coche> cochesAlquilados;

	public Ubicacion() {

	}

	public Ubicacion(final String direccion, final Integer plazasTotales) {
		super();
		this.direccion = direccion;
		this.plazasTotales = plazasTotales;
	}

	public Collection<Coche> getCochesAlquilados() {
		return cochesAlquilados;
	}

	public void setCochesAlquilados(final Collection<Coche> cochesAlquilados) {
		this.cochesAlquilados = cochesAlquilados;
	}

	public Collection<Alquiler> getIniciadosEn() {
		return iniciadosEn;
	}

	public void setIniciadosEn(final Collection<Alquiler> iniciadosEn) {
		this.iniciadosEn = iniciadosEn;
	}

	public Collection<Alquiler> getFinalizadosEn() {
		return finalizadosEn;
	}

	public void setFinalizadosEn(final Collection<Alquiler> finalizadosEn) {
		this.finalizadosEn = finalizadosEn;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(final Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	public Integer getPlazasTotales() {
		return plazasTotales;
	}

	public void setPlazasTotales(final Integer plazasTotales) {
		this.plazasTotales = plazasTotales;
	}

}

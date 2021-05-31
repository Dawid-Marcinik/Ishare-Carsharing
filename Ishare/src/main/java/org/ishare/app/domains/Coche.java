package org.ishare.app.domains;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Coche {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String matricula;
	@Column
	private Integer autonomiaRestante;
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Modelo modelo;
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ubicacion ubicacion;
	@JsonManagedReference
	@OneToMany(mappedBy = "coche", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Alquiler> alquileres;

	// CONSTRUCTORES
	public Coche() {
	}

	public Coche(final String matricula, final Integer autonomiaRestante, final Modelo modelo,
			final Ubicacion ubicacion) {
		super();
		this.matricula = matricula;
		this.autonomiaRestante = autonomiaRestante;
		this.modelo = modelo;
		this.ubicacion = ubicacion;
	}

	// GETTERS Y SETTERS
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Integer getAutonomiaRestante() {
		return autonomiaRestante;
	}

	public void setAutonomiaRestante(final Integer autonomiaRestante) {
		this.autonomiaRestante = autonomiaRestante;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(final Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(final List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(final Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(final String matricula) {
		this.matricula = matricula;
	}

}

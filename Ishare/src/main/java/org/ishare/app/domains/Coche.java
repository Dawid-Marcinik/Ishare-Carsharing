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

@Entity
public class Coche {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String matricula;
	@Column
	private Integer autonomiaRestante;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Modelo modelo;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ubicacion ubicacion;
	@OneToMany(mappedBy = "coche", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Alquiler> alquileres;
	
	//CONSTRUCTORES
	public Coche() {
	}

	public Coche(String matricula,Integer autonomiaRestante, Modelo modelo, Ubicacion ubicacion) {
		super();
		this.matricula = matricula;
		this.autonomiaRestante = autonomiaRestante;
		this.modelo = modelo;
		this.ubicacion = ubicacion;
	}

	//GETTERS Y SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAutonomiaRestante() {
		return autonomiaRestante;
	}

	public void setAutonomiaRestante(Integer autonomiaRestante) {
		this.autonomiaRestante = autonomiaRestante;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}

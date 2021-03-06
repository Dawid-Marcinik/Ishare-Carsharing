package org.ishare.app.domains;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Modelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nombre;
	@Column
	private Integer numeroPasajeros;
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Tipo tipo;
	@Column
	private Integer autonomiaTotal;
	@Column
	private Float tarifa;
	@Lob
    @Column(name = "imagen")
    private byte[] imagen;
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Marca marca;
	@JsonBackReference
	@OneToMany(mappedBy = "modelo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Coche> coches;
	
	//CONSTRUCTORES
	public Modelo() {
	}

	public Modelo(String nombre, Integer numeroPasajeros, Tipo tipo, Integer autonomiaTotal, Float tarifa, byte[] imagen,
			Marca marca) {
		super();
		this.nombre = nombre;
		this.numeroPasajeros = numeroPasajeros;
		this.tipo = tipo;
		this.autonomiaTotal = autonomiaTotal;
		this.tarifa = tarifa;
		this.marca = marca;
		this.imagen = imagen;
	}
	
	//GETTERS Y SETTERS
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

	public Integer getNumeroPasajeros() {
		return numeroPasajeros;
	}

	public void setNumeroPasajeros(Integer numeroPasajeros) {
		this.numeroPasajeros = numeroPasajeros;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Integer getAutonomiaTotal() {
		return autonomiaTotal;
	}

	public void setAutonomiaTotal(Integer autonomiaTotal) {
		this.autonomiaTotal = autonomiaTotal;
	}

	public Float getTarifa() {
		return tarifa;
	}

	public void setTarifa(Float tarifa) {
		this.tarifa = tarifa;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Coche> getCoches() {
		return coches;
	}

	public void setCoches(List<Coche> coches) {
		this.coches = coches;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
}

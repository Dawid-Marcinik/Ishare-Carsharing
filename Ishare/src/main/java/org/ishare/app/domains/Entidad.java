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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Entidad {

//Atributos
//=================================================================	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(unique = true)
	protected String nombreUsuario;

	protected String contrasena;

	protected String localidad;

	protected String direccion;

	protected Integer codigoPostal;

	@Column(unique = true)
	protected Integer telefono;

	@Column(unique = true)
	protected String email;
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.PERSIST)
	protected Rol rol;

	protected Float saldo;
	@JsonManagedReference
	@OneToMany(mappedBy = "entidad", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	protected List<Alquiler> alquileres;

	// Constructores
	// ===========================================================================

	public Entidad() {

	}

	public Entidad(final String nombreUsuario, final String contrasena, final String localidad, final String direccion,
			final Integer codigoPostal, final Integer telefono, final String email, final Rol rol, final Float saldo) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.localidad = localidad;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.email = email;
		this.rol = rol;
		this.saldo = saldo;
	}

	// GETTERS Y SETTERS
	// =========================================================================

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(final String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(final String contrasena) {
		this.contrasena = contrasena;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(final String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(final Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(final Integer telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(final Rol rol) {
		this.rol = rol;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(final Float saldo) {
		this.saldo = saldo;
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(final List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	
	public boolean isAdmin() {
		return (this.getRol()!= null && this.getRol().getNombre().equals("Admin"));
	}



}

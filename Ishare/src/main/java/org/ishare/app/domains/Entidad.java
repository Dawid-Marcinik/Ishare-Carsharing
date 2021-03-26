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
	

	@ManyToOne(cascade = CascadeType.PERSIST)
	protected Rol rol;

	
	protected Float saldo;
	
	@OneToMany(mappedBy = "entidad",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	protected List<Alquiler>alquileres;
	
	//Constructores
	//===========================================================================

	public Entidad() {
		
	}

	
	
	public Entidad(String nombreUsuario, String contrasena, String localidad, String direccion, Integer codigoPostal,

			Integer telefono, String email, Rol rol, Float saldo) {

		super();
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.localidad = localidad;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.email = email;
		this.saldo = saldo;
	}



	//GETTERS Y SETTERS
	//=========================================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}


	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}



	public List<Alquiler> getAlquileres() {
		return alquileres;
	}



	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}
	
	

}

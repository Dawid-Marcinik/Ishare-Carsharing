package org.ishare.app.domains;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Particular extends Entidad {
	
	//Atributos
	//=================================================================	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String dni;
	
	private String nombre;
	
	private String apellidos;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaNacimiento;
	
	//Constructores
	//===========================================================================

	public Particular() {
		
	}

	
	
	public Particular(String nombreUsuario, String contrasena, String localidad, String direccion, Integer codigoPostal,
			Integer telefono, String email, Rol rol, Float saldo,String dni, String nombre, String apellidos, LocalDate fechaNacimiento) {
		super(nombreUsuario, contrasena, localidad, direccion, codigoPostal, telefono, email, rol, saldo);
		
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}



	//GETTERS Y SETTERS
	//=========================================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}



	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	
	
	

}

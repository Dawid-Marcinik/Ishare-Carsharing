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

	// Atributos
	// =================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String dni;

	private String nombre;

	private String apellidos;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaNacimiento;

	// Constructores
	// ===========================================================================

	public Particular() {

	}

	public Particular(final String nombreUsuario, final String contrasena, final String localidad,
			final String direccion, final Integer codigoPostal, final Integer telefono, final String email,
			final Rol rol, final Float saldo, final String dni, final String nombre, final String apellidos,
			final LocalDate fechaNacimiento) {
		super(nombreUsuario, contrasena, localidad, direccion, codigoPostal, telefono, email, rol, saldo);

		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Particular(final String nombreUsuario, final String contrasena, final String localidad,
			final String direccion, final Integer codigoPostal, final Integer telefono, final String email,
			final String dni, final String nombre, final String apellidos, final LocalDate fechaNacimiento) {
		super(nombreUsuario, contrasena, localidad, direccion, codigoPostal, telefono, email);

		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}

	// GETTERS Y SETTERS
	// =========================================================================

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(final String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(final LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}

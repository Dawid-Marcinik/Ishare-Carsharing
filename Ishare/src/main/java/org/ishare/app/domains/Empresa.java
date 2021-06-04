package org.ishare.app.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empresa extends Entidad {

	// Atributos
	// =================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String cif;

	@Column(unique = true)
	private String razonSocial;

	// Constructores
	// ===========================================================================

	public Empresa() {

	}

	public Empresa(final String nombreUsuario, final String contrasena, final String localidad, final String direccion,
			final Integer codigoPostal, final Integer telefono, final String email, final Rol rol, final Float saldo,
			final String cif, final String razonSocial) {
		super(nombreUsuario, contrasena, localidad, direccion, codigoPostal, telefono, email, rol, saldo);
		// TODO Auto-generated constructor stub
		this.cif = cif;
		this.razonSocial = razonSocial;

	}

	public Empresa(final String nombreUsuario, final String contrasena, final String localidad, final String direccion,
			final Integer codigoPostal, final Integer telefono, final String email, final String cif,
			final String razonSocial) {
		super(nombreUsuario, contrasena, localidad, direccion, codigoPostal, telefono, email);
		// TODO Auto-generated constructor stub
		this.cif = cif;
		this.razonSocial = razonSocial;

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

	public String getCif() {
		return cif;
	}

	public void setCif(final String cif) {
		this.cif = cif;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(final String razonSocial) {
		this.razonSocial = razonSocial;
	}

}

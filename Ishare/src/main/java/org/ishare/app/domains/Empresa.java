package org.ishare.app.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empresa extends Entidad {
	
	//Atributos
	//=================================================================	
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String cif;
	
	@Column(unique = true)
	private String razonSocial;
	
	
	//Constructores
	//===========================================================================

	public Empresa() {
		
	}


	public Empresa(String nombreUsuario, String contrasena, String localidad, String direccion, Integer codigoPostal,
			Integer telefono, String email, Rol rol, Float saldo,String cif, String razonSocial) {
		super(nombreUsuario, contrasena, localidad, direccion, codigoPostal, telefono, email, rol, saldo);
		// TODO Auto-generated constructor stub
		this.cif = cif;
		this.razonSocial = razonSocial;
		
	}



	

	//GETTERS Y SETTERS
	//=========================================================================

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	

}

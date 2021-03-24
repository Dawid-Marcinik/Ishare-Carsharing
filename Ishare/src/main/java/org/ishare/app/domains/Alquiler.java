package org.ishare.app.domains;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class Alquiler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAlquiler;
	
	@Column()
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaInicio;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaFin;
	private Integer puntuacion;
	
	@ManyToOne(cascade = CascadeType.PERSIST,optional=true)
	private Ubicacion iniciaEn;
	
	@ManyToOne(cascade = CascadeType.PERSIST,optional=true)
	private Ubicacion finalizaEn;
	
	public Alquiler() {

	}
	public Alquiler(LocalDate fechaInicio, LocalDate fechaFin, Integer puntuacion) {
		super();
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.puntuacion=puntuacion;
	}
	
	public Ubicacion getIniciaEn() {
		return iniciaEn;
	}

	public void setIniciaEn(Ubicacion iniciaEn) {
		this.iniciaEn=iniciaEn;
	}
	
	public Ubicacion getFinalizaEn() {
		return finalizaEn;
	}

	public void setFinalizaEn(Ubicacion finalizaEn) {
		this.finalizaEn=finalizaEn;
	}

	public Long getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(Long idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	

}

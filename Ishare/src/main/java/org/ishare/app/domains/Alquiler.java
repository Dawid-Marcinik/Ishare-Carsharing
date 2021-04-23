package org.ishare.app.domains;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Alquiler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAlquiler;

	@Column()
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Integer puntuacion;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ubicacion iniciaEn;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ubicacion finalizaEn;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Coche coche;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Entidad entidad;

	public Alquiler() {

	}

	public Alquiler(final LocalDateTime fechaInicio, final LocalDateTime fechaFin, final Integer puntuacion) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.puntuacion = puntuacion;
	}

	public Coche getCoche() {
		return coche;
	}

	public void setCoche(final Coche coche) {
		this.coche = coche;
	}

	public Ubicacion getIniciaEn() {
		return iniciaEn;
	}

	public void setIniciaEn(final Ubicacion iniciaEn) {
		this.iniciaEn = iniciaEn;
	}

	public Ubicacion getFinalizaEn() {
		return finalizaEn;
	}

	public void setFinalizaEn(final Ubicacion finalizaEn) {
		this.finalizaEn = finalizaEn;
	}

	public Long getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(final Long idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(final LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(final LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(final Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}

}

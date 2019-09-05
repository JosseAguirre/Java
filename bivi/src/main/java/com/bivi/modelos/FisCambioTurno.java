package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the fis_cambio_turno database table.
 * 
 */
@Entity
@Table(name="fis_cambio_turno", schema = "bivi")
@NamedQuery(name="FisCambioTurno.findAll", query="SELECT f FROM FisCambioTurno f")
public class FisCambioTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cambio_turno")
	private Integer idCambioTurno;

	@Column(name="fecha_cambio")
	private Timestamp fechaCambio;

	private String observaciones;

	//uni-directional many-to-one association to AdmPuesto
	@ManyToOne
	@JoinColumn(name="id_puesto")
	private AdmPuesto idPuesto;

	//uni-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario_entrega")
	private AdmUsuario idUsuarioEntrega;

	//uni-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario_recibe")
	private AdmUsuario idUsuarioRecibe;

	public FisCambioTurno() {
	}

	public Integer getIdCambioTurno() {
		return this.idCambioTurno;
	}

	public void setIdCambioTurno(Integer idCambioTurno) {
		this.idCambioTurno = idCambioTurno;
	}

	public Timestamp getFechaCambio() {
		return this.fechaCambio;
	}

	public void setFechaCambio(Timestamp fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public AdmPuesto getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto(AdmPuesto idPuesto) {
		this.idPuesto = idPuesto;
	}

	public AdmUsuario getIdUsuarioEntrega() {
		return idUsuarioEntrega;
	}

	public void setIdUsuarioEntrega(AdmUsuario idUsuarioEntrega) {
		this.idUsuarioEntrega = idUsuarioEntrega;
	}

	public AdmUsuario getIdUsuarioRecibe() {
		return idUsuarioRecibe;
	}

	public void setIdUsuarioRecibe(AdmUsuario idUsuarioRecibe) {
		this.idUsuarioRecibe = idUsuarioRecibe;
	}

	
}
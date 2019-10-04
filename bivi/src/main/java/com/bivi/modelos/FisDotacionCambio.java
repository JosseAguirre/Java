package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_dotacion_cambio database table.
 * 
 */
@Entity
@Table(name="fis_dotacion_cambio", schema = "bivi")
@NamedQuery(name="FisDotacionCambio.findAll", query="SELECT f FROM FisDotacionCambio f")
public class FisDotacionCambio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dotacion_cambio")
	private Integer idDotacionCambio;

	//uni-directional many-to-one association to FisDotacion
	@ManyToOne
	@JoinColumn(name="id_dotacion")
	private FisDotacion idDotacion;

	//uni-directional many-to-one association to FisCambioTurno
	@ManyToOne
	@JoinColumn(name="id_cambio_turno")
	private FisCambioTurno idCambioTurno;

	public FisDotacionCambio() {
	}

	public Integer getIdDotacionCambio() {
		return this.idDotacionCambio;
	}

	public void setIdDotacionCambio(Integer idDotacionCambio) {
		this.idDotacionCambio = idDotacionCambio;
	}

	public FisDotacion getIdDotacion() {
		return this.idDotacion;
	}

	public void setIdDotacion(FisDotacion idDotacion) {
		this.idDotacion = idDotacion;
	}

	public FisCambioTurno getIdCambioTurno() {
		return this.idCambioTurno;
	}

	public void setIdCambioTurno(FisCambioTurno idCambioTurno) {
		this.idCambioTurno = idCambioTurno;
	}

}
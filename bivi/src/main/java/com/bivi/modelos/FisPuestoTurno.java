package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_puesto_turno database table.
 * 
 */
@Entity
@Table(name="fis_puesto_turno", schema = "bivi")
@NamedQuery(name="FisPuestoTurno.findAll", query="SELECT f FROM FisPuestoTurno f")
public class FisPuestoTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_puesto_turno")
	private Integer idPuestoTurno;
	
	@ManyToOne
	@JoinColumn(name="id_puesto")
	private AdmPuesto idPuesto;

	//bi-directional many-to-one association to FisTurno
	@ManyToOne
	@JoinColumn(name="id_turno")
	private FisTurno idTurno;

	public FisPuestoTurno() {
	}

	public Integer getIdPuestoTurno() {
		return this.idPuestoTurno;
	}

	public void setIdPuestoTurno(Integer idPuestoTurno) {
		this.idPuestoTurno = idPuestoTurno;
	}

	public AdmPuesto getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto(AdmPuesto idPuesto) {
		this.idPuesto = idPuesto;
	}

	public FisTurno getFisTurno() {
		return this.idTurno;
	}

	public void setFisTurno(FisTurno idTurno) {
		this.idTurno = idTurno;
	}

}
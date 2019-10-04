package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_turno_dia database table.
 * 
 */
@Entity
@Table(name="fis_turno_dia", schema = "bivi")
@NamedQuery(name="FisTurnoDia.findAll", query="SELECT f FROM FisTurnoDia f")
public class FisTurnoDia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_turno_dia")
	private Integer idTurnoDia;

	//uni-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_catalogo_dia")
	private AdmDetalleCatalogo idDia;

	//bi-directional many-to-one association to FisTurno
	@ManyToOne
	@JoinColumn(name="id_turno")
	private FisTurno idTurno;


	public Integer getIdTurnoDia() {
		return this.idTurnoDia;
	}

	public void setIdTurnoDia(Integer idTurnoDia) {
		this.idTurnoDia = idTurnoDia;
	}

	public AdmDetalleCatalogo getIdDia() {
		return this.idDia;
	}

	public void setIdDia(AdmDetalleCatalogo idDia) {
		this.idDia = idDia;
	}
	
	public FisTurno getFisTurno() {
		return this.idTurno;
	}

	public void setFisTurno(FisTurno idTurno) {
		this.idTurno = idTurno;
	}

}
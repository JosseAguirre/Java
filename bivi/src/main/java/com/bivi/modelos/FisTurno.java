package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fis_turno database table.
 * 
 */
@Entity
@Table(name="fis_turno" , schema = "bivi")
@NamedQuery(name="FisTurno.findAll", query="SELECT f FROM FisTurno f")
public class FisTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_turno")
	private Integer idTurno;

	private String nombre;
	
	private String hora_desde;
	
	private String hora_hasta;

	//bi-directional many-to-one association to FisPuestoTurno
	@OneToMany(mappedBy="idTurno")
	private List<FisPuestoTurno> idPuestoTurnos;

	//bi-directional many-to-one association to FisTurnoDia
	@OneToMany(mappedBy="idTurno")
	private List<FisTurnoDia> idTurnoDias;

	public FisTurno() {
	}

	public Integer getIdTurno() {
		return this.idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getHoraDesde() {
		return this.hora_desde;
	}

	public void setHoraDesde(String hora_desde) {
		this.hora_desde = hora_desde;
	}
	
	public String getHoraHasta() {
		return this.hora_hasta;
	}

	public void setHoraHasta(String hora_hasta) {
		this.hora_hasta = hora_hasta;
	}

	public List<FisPuestoTurno> getFisPuestoTurnos() {
		return this.idPuestoTurnos;
	}

	public void setFisPuestoTurnos(List<FisPuestoTurno> idPuestoTurnos) {
		this.idPuestoTurnos = idPuestoTurnos;
	}

	public FisPuestoTurno addFisPuestoTurno(FisPuestoTurno idPuestoTurnos) {
		getFisPuestoTurnos().add(idPuestoTurnos);
		idPuestoTurnos.setFisTurno(this);

		return idPuestoTurnos;
	}

	public FisPuestoTurno removeFisPuestoTurno(FisPuestoTurno idPuestoTurnos) {
		getFisPuestoTurnos().remove(idPuestoTurnos);
		idPuestoTurnos.setFisTurno(null);

		return idPuestoTurnos;
	}

	public List<FisTurnoDia> getFisTurnoDias() {
		return this.idTurnoDias;
	}

	public void setFisTurnoDias(List<FisTurnoDia> idTurnoDias) {
		this.idTurnoDias = idTurnoDias;
	}

	public FisTurnoDia addFisTurnoDia(FisTurnoDia idTurnoDias) {
		getFisTurnoDias().add(idTurnoDias);
		idTurnoDias.setFisTurno(this);

		return idTurnoDias;
	}

	public FisTurnoDia removeFisTurnoDia(FisTurnoDia idTurnoDias) {
		getFisTurnoDias().remove(idTurnoDias);
		idTurnoDias.setFisTurno(null);

		return idTurnoDias;
	}

}
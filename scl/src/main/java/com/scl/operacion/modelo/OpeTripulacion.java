package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;



import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ope_tripulacion database table.
 * 
 */
@Entity
@Table(name="ope_tripulacion", schema = "java")
@NamedQuery(name="OpeTripulacion.findAll", query="SELECT o FROM OpeTripulacion o")
public class OpeTripulacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tripulacion")
	private Integer idTripulacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_operacion")
	private Date fechaOperacion;
	
	@Column(name="dia_operacion")
	private String diaOperacion;



	private Boolean estado;

	@Column(name="fecha_asignacion")
	private Timestamp fechaAsignacion;

	//bi-directional many-to-one association to OpeVehiculo
	@ManyToOne
	@JoinColumn(name="id_vehiculo")
	private OpeVehiculo idVehiculo;

	//bi-directional many-to-one association to OpeCircuito
	@ManyToOne
	@JoinColumn(name="id_circuito")
	private OpeCircuito idCircuito;

	//bi-directional many-to-one association to OpeEquipoMovil
	@ManyToOne
	@JoinColumn(name="id_equipo_movil")
	private OpeEquipoMovil idEquipoMovil;

	public OpeTripulacion() {
	}

	public Integer getIdTripulacion() {
		return this.idTripulacion;
	}

	public void setIdTripulacion(Integer idTripulacion) {
		this.idTripulacion = idTripulacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Timestamp getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Timestamp fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public OpeVehiculo getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(OpeVehiculo idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public OpeCircuito getIdCircuito() {
		return this.idCircuito;
	}

	public void setIdCircuito(OpeCircuito idCircuito) {
		this.idCircuito = idCircuito;
	}

	public OpeEquipoMovil getIdEquipoMovil() {
		return this.idEquipoMovil;
	}

	public void setIdEquipoMovil(OpeEquipoMovil idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getDiaOperacion() {
		return diaOperacion;
	}

	public void setDiaOperacion(String diaOperacion) {
		this.diaOperacion = diaOperacion;
	}
		
	
	}
	


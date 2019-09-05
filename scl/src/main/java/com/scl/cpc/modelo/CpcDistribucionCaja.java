package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.operacion.modelo.OpeTransaccion;

import java.sql.Timestamp;


/**
 * The persistent class for the cpc_distribucion_caja database table.
 * 
 */
@Entity
@Table(name="cpc_distribucion_caja",schema = "java")
@NamedQuery(name="CpcDistribucionCaja.findAll", query="SELECT c FROM CpcDistribucionCaja c")
public class CpcDistribucionCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_distribucion_caja")
	private Integer idDistribucionCaja;

	private Integer estado;

	@Column(name="fecha_asigancion")
	private Timestamp fechaAsigancion;

	@Column(name="fecha_fin_caja")
	private Timestamp fechaFinCaja;

	@Column(name="fecha_inicio_caja")
	private Timestamp fechaInicioCaja;

	

	//uni-directional many-to-one association to CpcCaja
	@ManyToOne
	@JoinColumn(name="id_caja")
	private CpcCaja id_caja;
	
	//uni-directional many-to-one association to OpeTransaccion
		@ManyToOne
		@JoinColumn(name="id_transaccion")
		private OpeTransaccion idTransaccion;
		
		//uni-directional many-to-one association to AdmEmpleado
		@ManyToOne
		@JoinColumn(name="id_empleado")
		private AdmEmpleado idEmpleado;

	public CpcDistribucionCaja() {
	}

	public Integer getIdDistribucionCaja() {
		return this.idDistribucionCaja;
	}

	public void setIdDistribucionCaja(Integer idDistribucionCaja) {
		this.idDistribucionCaja = idDistribucionCaja;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFechaAsigancion() {
		return this.fechaAsigancion;
	}

	public void setFechaAsigancion(Timestamp fechaAsigancion) {
		this.fechaAsigancion = fechaAsigancion;
	}

	public Timestamp getFechaFinCaja() {
		return this.fechaFinCaja;
	}

	public void setFechaFinCaja(Timestamp fechaFinCaja) {
		this.fechaFinCaja = fechaFinCaja;
	}

	public Timestamp getFechaInicioCaja() {
		return this.fechaInicioCaja;
	}

	public void setFechaInicioCaja(Timestamp fechaInicioCaja) {
		this.fechaInicioCaja = fechaInicioCaja;
	}

	

	

	public OpeTransaccion getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(OpeTransaccion idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public AdmEmpleado getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public CpcCaja getId_caja() {
		return this.id_caja;
	}

	public void setId_caja(CpcCaja id_caja) {
		this.id_caja = id_caja;
	}

}
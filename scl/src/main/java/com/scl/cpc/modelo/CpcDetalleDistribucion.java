package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.operacion.modelo.OpeDetalleTransaccion;


/**
 * The persistent class for the cpc_detalle_distribucion database table.
 * 
 */
@Entity
@Table(name="cpc_detalle_distribucion", schema = "java")
@NamedQuery(name="CpcDetalleDistribucion.findAll", query="SELECT c FROM CpcDetalleDistribucion c")
public class CpcDetalleDistribucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_distribucion")
	private Integer idDetalleDistribucion;

	private Integer estado;

	@Column(name="total_efectivo")
	private float totalEfectivo;

	//uni-directional many-to-one association to CpcDistribucionCaja
	@ManyToOne
	@JoinColumn(name="id_distribucion_caja")
	private CpcDistribucionCaja id_distribucion_caja;
	
	//uni-directional many-to-one association to OpedetalleTransaccion
		@ManyToOne
		@JoinColumn(name="id_detalle_transaccion")
	private OpeDetalleTransaccion idDetalleTransaccion;
	
	

	public CpcDetalleDistribucion() {
	}

	public Integer getIdDetalleDistribucion() {
		return this.idDetalleDistribucion;
	}

	public void setIdDetalleDistribucion(Integer idDetalleDistribucion) {
		this.idDetalleDistribucion = idDetalleDistribucion;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public float getTotalEfectivo() {
		return this.totalEfectivo;
	}

	public void setTotalEfectivo(float totalEfectivo) {
		this.totalEfectivo = totalEfectivo;
	}

	public CpcDistribucionCaja getId_distribucion_caja() {
		return this.id_distribucion_caja;
	}

	public void setId_distribucion_caja(CpcDistribucionCaja id_distribucion_caja) {
		this.id_distribucion_caja = id_distribucion_caja;
	}

	public OpeDetalleTransaccion getIdDetalleTransaccion() {
		return idDetalleTransaccion;
	}

	public void setIdDetalleTransaccion(OpeDetalleTransaccion idDetalleTransaccion) {
		this.idDetalleTransaccion = idDetalleTransaccion;
	}


	

}
package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_detalle_especie database table.
 * 
 */
@Entity
@Table(name="cpc_detalle_especie", schema = "java")
@NamedQuery(name="CpcDetalleEspecie.findAll", query="SELECT c FROM CpcDetalleEspecie c")
public class CpcDetalleEspecie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_especie")
	private Integer idDetalleEspecie;

	private Integer cantidad;

	private float subtotal;

	private float total;

	//uni-directional many-to-one association to CpcDetalleDistribucion
	@ManyToOne
	@JoinColumn(name="id_detalle_distribucion")
	private CpcDetalleDistribucion idDetalleDistribucion;

	//uni-directional many-to-one association to CpcEspecie
	@ManyToOne
	@JoinColumn(name="id_especie")
	private CpcEspecie idEspecie;

	public CpcDetalleEspecie() {
	}

	public Integer getIdDetalleEspecie() {
		return this.idDetalleEspecie;
	}

	public void setIdDetalleEspecie(Integer idDetalleEspecie) {
		this.idDetalleEspecie = idDetalleEspecie;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public float getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}



	public CpcDetalleDistribucion getIdDetalleDistribucion() {
		return idDetalleDistribucion;
	}

	public void setIdDetalleDistribucion(CpcDetalleDistribucion idDetalleDistribucion) {
		this.idDetalleDistribucion = idDetalleDistribucion;
	}

	public CpcEspecie getIdEspecie() {
		return idEspecie;
	}

	public void setIdEspecie(CpcEspecie idEspecie) {
		this.idEspecie = idEspecie;
	}

	
}
package com.scl.sac.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sac_detalle_solicitud_servicio database table.
 * 
 */
@Entity
@Table(name="sac_detalle_solicitud_servicio", schema = "java")
@NamedQuery(name="SacDetalleSolicitudServicio.findAll", query="SELECT s FROM SacDetalleSolicitudServicio s")
public class SacDetalleSolicitudServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_solicutud_servicio")
	private Integer idDetalleSolicutudServicio;

	private Integer cantidad;

	@Column(name="id_especie")
	private Integer idEspecie;

	private float subtotal;

	//uni-directional many-to-one association to SacSolicitudServicio
	@ManyToOne
	@JoinColumn(name="id_solicitud_servicio")
	private SacSolicitudServicio idSolicitudServicio;

	public SacDetalleSolicitudServicio() {
	}

	public Integer getIdDetalleSolicutudServicio() {
		return this.idDetalleSolicutudServicio;
	}

	public void setIdDetalleSolicutudServicio(Integer idDetalleSolicutudServicio) {
		this.idDetalleSolicutudServicio = idDetalleSolicutudServicio;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIdEspecie() {
		return this.idEspecie;
	}

	public void setIdEspecie(Integer idEspecie) {
		this.idEspecie = idEspecie;
	}

	public float getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public SacSolicitudServicio getIdSolicitudServicio() {
		return this.idSolicitudServicio;
	}

	public void setIdSolicitudServicio(SacSolicitudServicio idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
	}

}
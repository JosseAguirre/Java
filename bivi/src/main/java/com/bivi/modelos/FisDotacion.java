package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_dotacion database table.
 * 
 */
@Entity
@Table(name="fis_dotacion", schema = "bivi")
@NamedQuery(name="FisDotacion.findAll", query="SELECT f FROM FisDotacion f")
public class FisDotacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dotacion")
	private Integer idDotacion;

	private Integer cantidad;

	private String descripcion;

	private String estado;

	private String observacion;

	@Column(name="url_imagen")
	private String urlImagen;

	//uni-directional many-to-one association to AdmPuesto
	@ManyToOne
	@JoinColumn(name="id_puesto")
	private AdmPuesto idPuesto;

	

	public FisDotacion() {
	}

	public Integer getIdDotacion() {
		return this.idDotacion;
	}

	public void setIdDotacion(Integer idDotacion) {
		this.idDotacion = idDotacion;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getUrlImagen() {
		return this.urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public AdmPuesto getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto(AdmPuesto idPuesto) {
		this.idPuesto = idPuesto;
	}

	

}
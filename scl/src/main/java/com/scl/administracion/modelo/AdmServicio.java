package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adm_servicio database table.
 * 
 */
@Entity
@Table(name="adm_servicio", schema = "java")
@NamedQuery(name="AdmServicio.findAll", query="SELECT a FROM AdmServicio a")
public class AdmServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_servicio")
	private Integer idServicio;

	private String descripcion;

	private String nombre;


	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_estado_dc")
	private AdmDetalleCatalogo idEstadoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_linea_negocio")
	private AdmDetalleCatalogo idLineaNegocio;

	//bi-directional many-to-one association to AdmTipoServicio
	@ManyToOne
	@JoinColumn(name="id_tipo_servicio")
	private AdmTipoServicio idTipoServicio;

	public AdmServicio() {
	}

	public Integer getIdServicio() {
		return this.idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public AdmDetalleCatalogo getIdEstadoDc() {
		return this.idEstadoDc;
	}

	public void setIdEstadoDc(AdmDetalleCatalogo idEstadoDc) {
		this.idEstadoDc = idEstadoDc;
	}

	public AdmDetalleCatalogo getIdLineaNegocio() {
		return this.idLineaNegocio;
	}

	public void setIdLineaNegocio(AdmDetalleCatalogo idLineaNegocio) {
		this.idLineaNegocio = idLineaNegocio;
	}

	public AdmTipoServicio getIdTipoServicio() {
		return this.idTipoServicio;
	}

	public void setIdTipoServicio(AdmTipoServicio idTipoServicio) {
		this.idTipoServicio = idTipoServicio;
	}

}
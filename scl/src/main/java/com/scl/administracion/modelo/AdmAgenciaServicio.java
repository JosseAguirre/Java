package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_agencia_servicio database table.
 * 
 */
@Entity
@Table(name="adm_agencia_servicio")
@NamedQuery(name="AdmAgenciaServicio.findAll", query="SELECT a FROM AdmAgenciaServicio a")
public class AdmAgenciaServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_agencia_servicio")
	private Integer idAgenciaServicio;

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	//bi-directional many-to-one association to AdmServicio
	@ManyToOne
	@JoinColumn(name="id_servicio")
	private AdmServicio idServicio;

	public AdmAgenciaServicio() {
	}

	public Integer getIdAgenciaServicio() {
		return this.idAgenciaServicio;
	}

	public void setIdAgenciaServicio(Integer idAgenciaServicio) {
		this.idAgenciaServicio = idAgenciaServicio;
	}

	public AdmAgencia getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public AdmServicio getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(AdmServicio idServicio) {
		this.idServicio = idServicio;
	}

	
}
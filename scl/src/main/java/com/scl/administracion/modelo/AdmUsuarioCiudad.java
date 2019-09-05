package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_usuario_ciudad database table.
 * 
 */
@Entity
@Table(name="adm_usuario_ciudad", schema = "java")
@NamedQuery(name="AdmUsuarioCiudad.findAll", query="SELECT a FROM AdmUsuarioCiudad a")
public class AdmUsuarioCiudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario_ciudad")
	private Integer idUsuarioCiudad;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;

	//bi-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AdmUsuario idUsuario;

	public AdmUsuarioCiudad() {
	}

	public Integer getIdUsuarioCiudad() {
		return this.idUsuarioCiudad;
	}

	public void setIdUsuarioCiudad(Integer idUsuarioCiudad) {
		this.idUsuarioCiudad = idUsuarioCiudad;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public AdmUsuario getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(AdmUsuario idUsuario) {
		this.idUsuario = idUsuario;
	}

}
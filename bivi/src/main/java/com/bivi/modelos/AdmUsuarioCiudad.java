package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_usuario_ciudad database table.
 * 
 */
@Entity
@Table(name="adm_usuario_ciudad", schema = "bivi")
@NamedQuery(name="AdmUsuarioCiudad.findAll", query="SELECT a FROM AdmUsuarioCiudad a")
public class AdmUsuarioCiudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario_ciudad")
	private Integer idUsuarioCiudad;

	//uni-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idDetalleCatalogo;

	//uni-directional many-to-one association to AdmUsuario
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

	public AdmDetalleCatalogo getIdDetalleCatalogo() {
		return this.idDetalleCatalogo;
	}

	public void setIdDetalleCatalogo(AdmDetalleCatalogo idDetalleCatalogo) {
		this.idDetalleCatalogo = idDetalleCatalogo;
	}

	public AdmUsuario getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(AdmUsuario idUsuario) {
		this.idUsuario = idUsuario;
	}

}
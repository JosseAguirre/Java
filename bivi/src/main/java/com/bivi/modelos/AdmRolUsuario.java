package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_rol_usuario database table.
 * 
 */
@Entity
@Table(name="adm_rol_usuario", schema = "bivi")
@NamedQuery(name="AdmRolUsuario.findAll", query="SELECT a FROM AdmRolUsuario a")
public class AdmRolUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rol_usuario")
	private Integer idRolUsuario;

	//uni-directional many-to-one association to AdmRol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private AdmRol idRol;

	//uni-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AdmUsuario idUsuario;

	public AdmRolUsuario() {
	}

	public Integer getIdRolUsuario() {
		return this.idRolUsuario;
	}

	public void setIdRolUsuario(Integer idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public AdmRol getIdRol() {
		return this.idRol;
	}

	public void setIdRol(AdmRol idRol) {
		this.idRol = idRol;
	}

	public AdmUsuario getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(AdmUsuario idUsuario) {
		this.idUsuario = idUsuario;
	}

}
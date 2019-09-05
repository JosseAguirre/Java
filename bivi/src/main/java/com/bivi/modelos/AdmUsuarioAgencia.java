package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_usuario_agencia database table.
 * 
 */
@Entity
@Table(name="adm_usuario_agencia", schema = "bivi")
@NamedQuery(name="AdmUsuarioAgencia.findAll", query="SELECT a FROM AdmUsuarioAgencia a")
public class AdmUsuarioAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario_agencia")
	private Integer idUsuarioAgencia;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	//uni-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AdmUsuario idUsuario;

	public AdmUsuarioAgencia() {
	}

	public Integer getIdUsuarioAgencia() {
		return this.idUsuarioAgencia;
	}

	public void setIdUsuarioAgencia(Integer idUsuarioAgencia) {
		this.idUsuarioAgencia = idUsuarioAgencia;
	}

	public AdmAgencia getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public AdmUsuario getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(AdmUsuario idUsuario) {
		this.idUsuario = idUsuario;
	}

}
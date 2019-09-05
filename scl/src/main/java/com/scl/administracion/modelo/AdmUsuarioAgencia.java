package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_usuario_ciudad database table.
 * 
 */
@Entity
@Table(name="adm_usuario_agencia", schema = "java")
@NamedQuery(name="AdmUsuarioAgencia.findAll", query="SELECT a FROM AdmUsuarioAgencia a")
public class AdmUsuarioAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario_agencia")
	private Integer idUsuarioAgencia;

	//bi-directional many-to-one association to AdmUsuario 
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AdmUsuario idUsuario;

	//bi-directional many-to-one association to AdmUsuarioAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;
	

	public Integer getIdUsuarioAgencia() {
		return idUsuarioAgencia;
	}

	public void setIdUsuarioAgencia(Integer idUsuarioAgencia) {
		this.idUsuarioAgencia = idUsuarioAgencia;
	}

	public AdmUsuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(AdmUsuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public AdmAgencia getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	
	
}
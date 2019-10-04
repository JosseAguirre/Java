package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;

import com.bivi.modelos.AdmAgencia;
import com.bivi.modelos.AdmPuesto;
import com.bivi.modelos.AdmUsuario;

import java.util.Date;


/**
 * The persistent class for the fis_guardia_agencia database table.
 * 
 */
@Entity
@Table(name="fis_guardia_agencia", schema = "bivi")
@NamedQuery(name="FisGuardiaAgencia.findAll", query="SELECT f FROM FisGuardiaAgencia f")
public class FisGuardiaAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_guardia_agencia")
	private Integer idGuardiaAgencia;

	private Integer activo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_asignacion")
	private Date fechaAsignacion;

	@Column(name="sys_delete")
	private Integer sysDelete;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	//uni-directional many-to-one association to AdmPuesto
	@ManyToOne
	@JoinColumn(name="id_puesto")
	private AdmPuesto idPuesto;

	//uni-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AdmUsuario idUsuario;

	//uni-directional many-to-one association to AdmUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario_asignado_por")
	private AdmUsuario idUsuarioAsignadoPor;

	public FisGuardiaAgencia() {
	}

	public Integer getIdGuardiaAgencia() {
		return this.idGuardiaAgencia;
	}

	public void setIdGuardiaAgencia(Integer idGuardiaAgencia) {
		this.idGuardiaAgencia = idGuardiaAgencia;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Date getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Integer getSysDelete() {
		return this.sysDelete;
	}

	public void setSysDelete(Integer sysDelete) {
		this.sysDelete = sysDelete;
	}

	public AdmAgencia getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public AdmPuesto getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto(AdmPuesto idPuesto) {
		this.idPuesto = idPuesto;
	}

	public AdmUsuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(AdmUsuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public AdmUsuario getIdUsuarioAsignadoPor() {
		return this.idUsuarioAsignadoPor;
	}

	public void setIdUsuarioAsignadoPor(AdmUsuario idUsuarioAsignadoPor) {
		this.idUsuarioAsignadoPor = idUsuarioAsignadoPor;
	}

}
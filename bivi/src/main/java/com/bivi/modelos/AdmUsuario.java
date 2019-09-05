package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the adm_usuario database table.
 * 
 */
@Entity
@Table(name="adm_usuario", schema = "bivi")
@NamedQuery(name="AdmUsuario.findAll", query="SELECT a FROM AdmUsuario a")
public class AdmUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario")
	private Integer idUsuario;

	private Integer activo;

	private String clave;

	@Column(name="dias_validez")
	private Integer diasValidez;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Column(name="sys_delete")
	private Integer sysDelete;

	private String usuario;

	//uni-directional many-to-one association to AdmEmpleado
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	public AdmUsuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getDiasValidez() {
		return this.diasValidez;
	}

	public void setDiasValidez(Integer diasValidez) {
		this.diasValidez = diasValidez;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Integer getSysDelete() {
		return this.sysDelete;
	}

	public void setSysDelete(Integer sysDelete) {
		this.sysDelete = sysDelete;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public AdmEmpleado getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

}
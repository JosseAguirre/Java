package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the adm_usuario database table.
 * 
 */
@Entity
@Table(name="adm_usuario", schema = "java")
//@NamedQuery(name="AdmUsuario.findAll", query="SELECT a FROM AdmUsuario a")
public class AdmUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario")
	private Integer idUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_caducidad")
	private Date fechaCaducidad;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ultimo_acceso")
	private Date fechaUltimoAcceso;

	private String password;

	private String usuario;

	//bi-directional many-to-one association to AdmRolUsuario
	@OneToMany(mappedBy="idUsuario")
	private List<AdmRolUsuario> admRolUsuarios;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_estado_dc")
	private AdmDetalleCatalogo idEstadoDc;

	//bi-directional many-to-one association to AdmEmpleado
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	//bi-directional many-to-one association to AdmUsuarioCiudad
	@OneToMany(mappedBy="idUsuario")
	private List<AdmUsuarioCiudad> admUsuarioCiudads;

	public AdmUsuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaCaducidad() {
		return this.fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
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

	public Date getFechaUltimoAcceso() {
		return this.fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<AdmRolUsuario> getAdmRolUsuarios() {
		return this.admRolUsuarios;
	}

	public void setAdmRolUsuarios(List<AdmRolUsuario> admRolUsuarios) {
		this.admRolUsuarios = admRolUsuarios;
	}

	public AdmRolUsuario addAdmRolUsuario(AdmRolUsuario admRolUsuario) {
		getAdmRolUsuarios().add(admRolUsuario);
		admRolUsuario.setIdUsuario(this);

		return admRolUsuario;
	}

	public AdmRolUsuario removeAdmRolUsuario(AdmRolUsuario admRolUsuario) {
		getAdmRolUsuarios().remove(admRolUsuario);
		admRolUsuario.setIdUsuario(null);

		return admRolUsuario;
	}

	public AdmDetalleCatalogo getIdEstadoDc() {
		return this.idEstadoDc;
	}

	public void setIdEstadoDc(AdmDetalleCatalogo idEstadoDc) {
		this.idEstadoDc = idEstadoDc;
	}

	public AdmEmpleado getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public List<AdmUsuarioCiudad> getAdmUsuarioCiudads() {
		return this.admUsuarioCiudads;
	}

	public void setAdmUsuarioCiudads(List<AdmUsuarioCiudad> admUsuarioCiudads) {
		this.admUsuarioCiudads = admUsuarioCiudads;
	}

	public AdmUsuarioCiudad addAdmUsuarioCiudad(AdmUsuarioCiudad admUsuarioCiudad) {
		getAdmUsuarioCiudads().add(admUsuarioCiudad);
		admUsuarioCiudad.setIdUsuario(this);

		return admUsuarioCiudad;
	}

	public AdmUsuarioCiudad removeAdmUsuarioCiudad(AdmUsuarioCiudad admUsuarioCiudad) {
		getAdmUsuarioCiudads().remove(admUsuarioCiudad);
		admUsuarioCiudad.setIdUsuario(null);

		return admUsuarioCiudad;
	}

}
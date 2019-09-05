package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_responsables_dependencia database table.
 * 
 */
@Entity
@Table(name="adm_responsables_dependencia", schema = "bivi")
@NamedQuery(name="AdmResponsablesDependencia.findAll", query="SELECT a FROM AdmResponsablesDependencia a")
public class AdmResponsablesDependencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_responsable_dependencia")
	private Integer idResponsableDependencia;

	private Integer activo;

	private String apellidos;

	@Column(name="correo_electronico")
	private String correoElectronico;

	private String nombres;

	@Column(name="telefono_celular")
	private String telefonoCelular;

	@Column(name="telefono_fijo")
	private String telefonoFijo;

	//uni-directional many-to-one association to AdmDependencia
	@ManyToOne
	@JoinColumn(name="id_dependencia")
	private AdmDependencia idDependencia;

	public AdmResponsablesDependencia() {
	}

	public Integer getIdResponsableDependencia() {
		return this.idResponsableDependencia;
	}

	public void setIdResponsableDependencia(Integer idResponsableDependencia) {
		this.idResponsableDependencia = idResponsableDependencia;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefonoCelular() {
		return this.telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	public String getTelefonoFijo() {
		return this.telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public AdmDependencia getIdDependencia() {
		return this.idDependencia;
	}

	public void setIdDependencia(AdmDependencia idDependencia) {
		this.idDependencia = idDependencia;
	}

}
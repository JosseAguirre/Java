package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adm_rol database table.
 * 
 */
@Entity
@Table(name="adm_rol", schema = "java")
@NamedQuery(name="AdmRol.findAll", query="SELECT a FROM AdmRol a")
public class AdmRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rol")
	private Integer idRol;

	private String descripcion;

	@Column(name="id_padre")
	private Integer idPadre;

	private String nombre;

	//bi-directional many-to-one association to AdmRolMenu
	@OneToMany(mappedBy="idRol")
	private List<AdmRolMenu> admRolMenus;

	//bi-directional many-to-one association to AdmRolUsuario
	@OneToMany(mappedBy="idRol")
	private List<AdmRolUsuario> admRolUsuarios;

	public AdmRol() {
	}

	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdPadre() {
		return this.idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AdmRolMenu> getAdmRolMenus() {
		return this.admRolMenus;
	}

	public void setAdmRolMenus(List<AdmRolMenu> admRolMenus) {
		this.admRolMenus = admRolMenus;
	}

	public AdmRolMenu addAdmRolMenus(AdmRolMenu admRolMenus) {
		getAdmRolMenus().add(admRolMenus);
		admRolMenus.setIdRol(this);

		return admRolMenus;
	}

	public AdmRolMenu removeAdmRolMenus(AdmRolMenu admRolMenus) {
		getAdmRolMenus().remove(admRolMenus);
		admRolMenus.setIdRol(null);

		return admRolMenus;
	}

	public List<AdmRolUsuario> getAdmRolUsuarios() {
		return this.admRolUsuarios;
	}

	public void setAdmRolUsuarios(List<AdmRolUsuario> admRolUsuarios) {
		this.admRolUsuarios = admRolUsuarios;
	}

	public AdmRolUsuario addAdmRolUsuario(AdmRolUsuario admRolUsuario) {
		getAdmRolUsuarios().add(admRolUsuario);
		admRolUsuario.setIdRol(this);

		return admRolUsuario;
	}

	public AdmRolUsuario removeAdmRolUsuario(AdmRolUsuario admRolUsuario) {
		getAdmRolUsuarios().remove(admRolUsuario);
		admRolUsuario.setIdRol(null);

		return admRolUsuario;
	}

}
package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adm_menu database table.
 * 
 */
@Entity
@Table(name="adm_menu", schema = "java")
@NamedQuery(name="AdmMenu.findAll", query="SELECT a FROM AdmMenu a")
public class AdmMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_menu")
	private Integer idMenu;

	@Column(name="id_padre")
	private Integer idPadre;

	private String nombre;

	@Column(name="url_pagina")
	private String urlPagina;
	
	@Column(name="icono")
	private String icono;

	//bi-directional many-to-one association to AdmRolMenu
	@OneToMany(mappedBy="idMenu")
	private List<AdmRolMenu> admRolMenus;

	public AdmMenu() {
	}

	public Integer getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
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

	public String getUrlPagina() {
		return this.urlPagina;
	}

	public void setUrlPagina(String urlPagina) {
		this.urlPagina = urlPagina;
	}

	public List<AdmRolMenu> getAdmRolMenus() {
		return this.admRolMenus;
	}

	public void setAdmRolMenus(List<AdmRolMenu> admRolMenus) {
		this.admRolMenus = admRolMenus;
	}

	public AdmRolMenu addAdmRolMenus(AdmRolMenu admRolMenus) {
		getAdmRolMenus().add(admRolMenus);
		admRolMenus.setIdMenu(this);

		return admRolMenus;
	}

	public AdmRolMenu removeAdmRolMenus(AdmRolMenu admRolMenus) {
		getAdmRolMenus().remove(admRolMenus);
		admRolMenus.setIdMenu(null);

		return admRolMenus;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	

}
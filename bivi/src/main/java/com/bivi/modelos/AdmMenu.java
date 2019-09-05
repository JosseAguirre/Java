package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_menu database table.
 * 
 */
@Entity
@Table(name="adm_menu", schema = "bivi")
@NamedQuery(name="AdmMenu.findAll", query="SELECT a FROM AdmMenu a")
public class AdmMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_menu")
	private Integer idMenu;

	private String icono;

	private String icono2;

	@Column(name="id_padre")
	private Integer idPadre;

	private String nombre;

	private Integer orden;

	@Column(name="url_pagina")
	private String urlPagina;

	public AdmMenu() {
	}

	public Integer getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	public String getIcono() {
		return this.icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getIcono2() {
		return this.icono2;
	}

	public void setIcono2(String icono2) {
		this.icono2 = icono2;
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

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getUrlPagina() {
		return this.urlPagina;
	}

	public void setUrlPagina(String urlPagina) {
		this.urlPagina = urlPagina;
	}

}
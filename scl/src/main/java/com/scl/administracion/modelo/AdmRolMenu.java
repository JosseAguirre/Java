package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_rol_menu database table.
 * 
 */
@Entity
@Table(name="adm_rol_menu", schema = "java")
@NamedQuery(name="AdmRolMenu.findAll", query="SELECT a FROM AdmRolMenu a")
public class AdmRolMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rol_menu")
	private Integer idRolMenu;

	//bi-directional many-to-one association to AdmMenu
	@ManyToOne
	@JoinColumn(name="id_menu")
	private AdmMenu idMenu;

	//bi-directional many-to-one association to AdmRol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private AdmRol idRol;

	public AdmRolMenu() {
	}

	public Integer getIdRolMenu() {
		return this.idRolMenu;
	}

	public void setIdRolMenu(Integer idRolMenu) {
		this.idRolMenu = idRolMenu;
	}

	public AdmMenu getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(AdmMenu idMenu) {
		this.idMenu = idMenu;
	}

	public AdmRol getIdRol() {
		return this.idRol;
	}

	public void setIdRol(AdmRol idRol) {
		this.idRol = idRol;
	}

}
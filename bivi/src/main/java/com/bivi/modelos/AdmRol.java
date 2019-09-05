package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_rol database table.
 * 
 */
@Entity
@Table(name="adm_rol", schema = "bivi")
@NamedQuery(name="AdmRol.findAll", query="SELECT a FROM AdmRol a")
public class AdmRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rol")
	private Integer idRol;

	private String descripcion;

	private String nombre;

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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
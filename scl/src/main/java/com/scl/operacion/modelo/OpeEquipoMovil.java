package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.administracion.modelo.AdmDetalleCatalogo;


/**
 * The persistent class for the ope_equipo_movil database table.
 * 
 */
@Entity
@Table(name="ope_equipo_movil",  schema = "java")
@NamedQuery(name="OpeEquipoMovil.findAll", query="SELECT o FROM OpeEquipoMovil o")
public class OpeEquipoMovil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_equipo_movil")
	private Integer idEquipoMovil;

	private String nombre;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo id_ciudad_dc;

	public OpeEquipoMovil() {
	}

	public Integer getIdEquipoMovil() {
		return this.idEquipoMovil;
	}

	public void setIdEquipoMovil(Integer idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public AdmDetalleCatalogo getId_ciudad_dc() {
		return this.id_ciudad_dc;
	}

	public void setId_ciudad_dc(AdmDetalleCatalogo id_ciudad_dc) {
		this.id_ciudad_dc = id_ciudad_dc;
	}

}
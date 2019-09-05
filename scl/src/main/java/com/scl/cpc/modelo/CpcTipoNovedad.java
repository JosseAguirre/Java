package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_tipo_novedad database table.
 * 
 */
@Entity
@Table(name="cpc_tipo_novedad",schema = "java")
@NamedQuery(name="CpcTipoNovedad.findAll", query="SELECT c FROM CpcTipoNovedad c")
public class CpcTipoNovedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_novedad")
	private Integer idTipoNovedad;

	private String nombre;

	public CpcTipoNovedad() {
	}

	public Integer getIdTipoNovedad() {
		return this.idTipoNovedad;
	}

	public void setIdTipoNovedad(Integer idTipoNovedad) {
		this.idTipoNovedad = idTipoNovedad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
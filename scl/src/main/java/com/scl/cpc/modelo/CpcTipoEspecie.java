package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_tipo_especie database table.
 * 
 */
@Entity
@Table(name="cpc_tipo_especie",schema = "java")
@NamedQuery(name="CpcTipoEspecie.findAll", query="SELECT c FROM CpcTipoEspecie c")
public class CpcTipoEspecie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_especie")
	private Integer idTipoEspecie;

	private String nombre;

	public CpcTipoEspecie() {
	}

	public Integer getIdTipoEspecie() {
		return this.idTipoEspecie;
	}

	public void setIdTipoEspecie(Integer idTipoEspecie) {
		this.idTipoEspecie = idTipoEspecie;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
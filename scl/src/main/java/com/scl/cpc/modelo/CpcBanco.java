package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_banco database table.
 * 
 */
@Entity
@Table(name="cpc_banco", schema = "java")
@NamedQuery(name="CpcBanco.findAll", query="SELECT c FROM CpcBanco c")
public class CpcBanco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_banco")
	private Integer idBanco;

	private String nombre;

	public CpcBanco() {
	}

	public Integer getIdBanco() {
		return this.idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
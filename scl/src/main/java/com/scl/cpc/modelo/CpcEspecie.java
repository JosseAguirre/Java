package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_especie database table.
 * 
 */
@Entity
@Table(name="cpc_especie", schema = "java")
@NamedQuery(name="CpcEspecie.findAll", query="SELECT c FROM CpcEspecie c")
public class CpcEspecie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_especie")
	private Integer idEspecie;

	private float valor;

	//uni-directional many-to-one association to CpcTipoEspecie
	@ManyToOne
	@JoinColumn(name="id_tipo_especie")
	private CpcTipoEspecie id_tipo_especie;

	public CpcEspecie() {
	}

	public Integer getIdEspecie() {
		return this.idEspecie;
	}

	public void setIdEspecie(Integer idEspecie) {
		this.idEspecie = idEspecie;
	}

	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public CpcTipoEspecie getId_tipo_especie() {
		return this.id_tipo_especie;
	}

	public void setId_tipo_especie(CpcTipoEspecie id_tipo_especie) {
		this.id_tipo_especie = id_tipo_especie;
	}

}
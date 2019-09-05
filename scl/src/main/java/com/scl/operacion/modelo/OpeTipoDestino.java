package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ope_tipo_destino database table.
 * 
 */
@Entity
@Table(name="ope_tipo_destino", schema = "java")
@NamedQuery(name="OpeTipoDestino.findAll", query="SELECT o FROM OpeTipoDestino o")
public class OpeTipoDestino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_destino")
	private Integer idTipoDestino;

	private String nombre;

	//bi-directional many-to-one association to OpeDestinoProvisional
	@OneToMany(mappedBy="idTipoDestino")
	private List<OpeDestinoProvisional> opeDestinoProvisionals;

	public OpeTipoDestino() {
	}

	public Integer getIdTipoDestino() {
		return this.idTipoDestino;
	}

	public void setIdTipoDestino(Integer idTipoDestino) {
		this.idTipoDestino = idTipoDestino;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<OpeDestinoProvisional> getOpeDestinoProvisionals() {
		return this.opeDestinoProvisionals;
	}

	public void setOpeDestinoProvisionals(List<OpeDestinoProvisional> opeDestinoProvisionals) {
		this.opeDestinoProvisionals = opeDestinoProvisionals;
	}

	public OpeDestinoProvisional addOpeDestinoProvisional(OpeDestinoProvisional opeDestinoProvisional) {
		getOpeDestinoProvisionals().add(opeDestinoProvisional);
		opeDestinoProvisional.setIdTipoDestino(this);

		return opeDestinoProvisional;
	}

	public OpeDestinoProvisional removeOpeDestinoProvisional(OpeDestinoProvisional opeDestinoProvisional) {
		getOpeDestinoProvisionals().remove(opeDestinoProvisional);
		opeDestinoProvisional.setIdTipoDestino(null);

		return opeDestinoProvisional;
	}

}
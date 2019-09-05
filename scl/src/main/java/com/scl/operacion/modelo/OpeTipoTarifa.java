package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ope_tipo_tarifa database table.
 * 
 */
@Entity
@Table(name="ope_tipo_tarifa", schema = "java")
@NamedQuery(name="OpeTipoTarifa.findAll", query="SELECT o FROM OpeTipoTarifa o")
public class OpeTipoTarifa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_tarifa")
	private Integer idTipoTarifa;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to OpeTransaccion
	@OneToMany(mappedBy="idTipoTarifa")
	private List<OpeTransaccion> opeTransaccions;

	public OpeTipoTarifa() {
	}

	public Integer getIdTipoTarifa() {
		return this.idTipoTarifa;
	}

	public void setIdTipoTarifa(Integer idTipoTarifa) {
		this.idTipoTarifa = idTipoTarifa;
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

	public List<OpeTransaccion> getOpeTransaccions() {
		return this.opeTransaccions;
	}

	public void setOpeTransaccions(List<OpeTransaccion> opeTransaccions) {
		this.opeTransaccions = opeTransaccions;
	}

	public OpeTransaccion addOpeTransaccion(OpeTransaccion opeTransaccion) {
		getOpeTransaccions().add(opeTransaccion);
		opeTransaccion.setIdTipoTarifa(this);

		return opeTransaccion;
	}

	public OpeTransaccion removeOpeTransaccion(OpeTransaccion opeTransaccion) {
		getOpeTransaccions().remove(opeTransaccion);
		opeTransaccion.setIdTipoTarifa(null);

		return opeTransaccion;
	}

}
package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ope_estado_transaccion database table.
 * 
 */
@Entity
@Table(name="ope_estado_transaccion", schema = "java")
@NamedQuery(name="OpeEstadoTransaccion.findAll", query="SELECT o FROM OpeEstadoTransaccion o")
public class OpeEstadoTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_estado_transaccion")
	private Integer idEstadoTransaccion;

	private String descripcion;

	private String nombre;

	

	public OpeEstadoTransaccion() {
	}

	public Integer getIdEstadoTransaccion() {
		return this.idEstadoTransaccion;
	}

	public void setIdEstadoTransaccion(Integer idEstadoTransaccion) {
		this.idEstadoTransaccion = idEstadoTransaccion;
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
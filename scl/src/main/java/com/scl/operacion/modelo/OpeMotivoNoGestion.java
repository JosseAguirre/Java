package com.scl.operacion.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the ope_motivo_no_gestion database table.
 * 
 */
@Entity
@Table(name="ope_motivo_no_gestion", schema = "java")
@NamedQuery(name="OpeMotivoNoGestion.findAll", query="SELECT o FROM OpeMotivoNoGestion o")
public class OpeMotivoNoGestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_motivo")
	private Integer idMotivo;

	private String descripcion;

	private String nombre;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name="fecha_actualizacion")
	private String fechaActualizacion;

	public OpeMotivoNoGestion() {
	}

	public Integer getIdMotivo() {
		return this.idMotivo;
	}

	public void setIdMotivo(Integer idMotivo) {
		this.idMotivo = idMotivo;
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
	
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
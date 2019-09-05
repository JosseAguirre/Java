package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_responsable database table.
 * 
 */
@Entity
@Table(name="ope_responsable", schema = "java")
@NamedQuery(name="OpeResponsable.findAll", query="SELECT o FROM OpeResponsable o")
public class OpeResponsable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_responsable")
	private Integer idResponsable;

	private String apellido;

	private String cedula;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String nombre;

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AdmCliente idCliente;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_estado_dc")
	private AdmDetalleCatalogo idEstadoDc;

	



	public OpeResponsable() {
	}

	public Integer getIdResponsable() {
		return this.idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public AdmAgencia getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public AdmCliente getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(AdmCliente idCliente) {
		this.idCliente = idCliente;
	}

	public AdmDetalleCatalogo getIdEstadoDc() {
		return this.idEstadoDc;
	}

	public void setIdEstadoDc(AdmDetalleCatalogo idEstadoDc) {
		this.idEstadoDc = idEstadoDc;
	}

	

	

}
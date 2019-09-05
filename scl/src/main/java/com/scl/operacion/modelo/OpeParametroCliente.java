package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_parametro_cliente database table.
 * 
 */
@Entity
@Table(name="ope_parametro_cliente", schema = "java")
@NamedQuery(name="OpeParametroCliente.findAll", query="SELECT o FROM OpeParametroCliente o")
public class OpeParametroCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_parametro_cliente")
	private Integer idParametroCliente;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Column(name="nombre_contacto")
	private String nombreContacto;

	private Integer plazo;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AdmCliente idCliente;

	//bi-directional many-to-one association to AdmTipoServicio
	@ManyToOne
	@JoinColumn(name="id_tipo_servicio")
	private AdmTipoServicio idTipoServicio;

	public OpeParametroCliente() {
	}

	public Integer getIdParametroCliente() {
		return this.idParametroCliente;
	}

	public void setIdParametroCliente(Integer idParametroCliente) {
		this.idParametroCliente = idParametroCliente;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombreContacto() {
		return this.nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public Integer getPlazo() {
		return this.plazo;
	}

	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	public AdmCliente getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(AdmCliente idCliente) {
		this.idCliente = idCliente;
	}

	public AdmTipoServicio getIdTipoServicio() {
		return this.idTipoServicio;
	}

	public void setIdTipoServicio(AdmTipoServicio idTipoServicio) {
		this.idTipoServicio = idTipoServicio;
	}

}
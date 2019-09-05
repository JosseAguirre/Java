package com.scl.sac.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import com.scl.administracion.modelo.AdmAgencia;


/**
 * The persistent class for the sac_solicitud_servicio database table.
 * 
 */
@Entity
@Table(name="sac_solicitud_servicio", schema = "java")
@NamedQuery(name="SacSolicitudServicio.findAll", query="SELECT s FROM SacSolicitudServicio s")
public class SacSolicitudServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_solicitud_servicio")
	private Integer idSolicitudServicio;

	//bi-directional many-to-one association to AdmAgencia
		@ManyToOne
		@JoinColumn(name="id_agencia_origen")
		private AdmAgencia idAgenciaOrigen;

		//bi-directional many-to-one association to AdmAgencia
		@ManyToOne
		@JoinColumn(name="id_agencia_destino")
		private AdmAgencia idAgenciaDestino;

	@Column(name="id_cliente")
	private Integer idCliente;
	
	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;
	
	@Column(name="id_rutero_carga")
	private Integer idRuteroCarga;
	
	@Column(name="id_rutero_descarga")
	private Integer idRuteroDescarga;
	
	

	private float monto;
	
	@Column(name="novedad_carga")
	private String novedaCarga;
	
	@Column(name="novedad_descarga")
	private String novedaDescarga;
	
	@Column(name="tipo_servicio")
	private String tipoServicio;
	
	@Column(name="hora")
	private String hora;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_operacion")
	private Date fechaOperacion;
	
	
	

	public SacSolicitudServicio() {
	}

	public Integer getIdSolicitudServicio() {
		return this.idSolicitudServicio;
	}

	public void setIdSolicitudServicio(Integer idSolicitudServicio) {
		this.idSolicitudServicio = idSolicitudServicio;
	}

	

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public float getMonto() {
		return this.monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getIdRuteroCarga() {
		return idRuteroCarga;
	}

	public void setIdRuteroCarga(Integer idRuteroCarga) {
		this.idRuteroCarga = idRuteroCarga;
	}

	public Integer getIdRuteroDescarga() {
		return idRuteroDescarga;
	}

	public void setIdRuteroDescarga(Integer idRuteroDescarga) {
		this.idRuteroDescarga = idRuteroDescarga;
	}

	public AdmAgencia getIdAgenciaOrigen() {
		return idAgenciaOrigen;
	}

	public void setIdAgenciaOrigen(AdmAgencia idAgenciaOrigen) {
		this.idAgenciaOrigen = idAgenciaOrigen;
	}

	public AdmAgencia getIdAgenciaDestino() {
		return idAgenciaDestino;
	}

	public void setIdAgenciaDestino(AdmAgencia idAgenciaDestino) {
		this.idAgenciaDestino = idAgenciaDestino;
	}

	public String getNovedaCarga() {
		return novedaCarga;
	}

	public void setNovedaCarga(String novedaCarga) {
		this.novedaCarga = novedaCarga;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getNovedaDescarga() {
		return novedaDescarga;
	}

	public void setNovedaDescarga(String novedaDescarga) {
		this.novedaDescarga = novedaDescarga;
	}
	
	

}
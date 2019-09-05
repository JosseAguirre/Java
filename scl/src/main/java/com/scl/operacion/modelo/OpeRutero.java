package com.scl.operacion.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ope_rutero database table.
 * 
 */
@Entity
@Table(name="ope_rutero", schema = "java")
@NamedQuery(name="OpeRutero.findAll", query="SELECT o FROM OpeRutero o")
public class OpeRutero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rutero")
	private Integer idRutero;

	@Column(name="fecha_asignacion")
	private Timestamp fechaAsignacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_operacion")
	private Date fechaOperacion;

	@Column(name="id_agencia_destino")
	private Integer idAgenciaDestino;

	@Column(name="id_agencia_origen")
	private Integer idAgenciaOrigen;

	@Column(name="id_circuito")
	private Integer idCircuito;

	@Column(name="id_cliente_destino")
	private Integer idClienteDestino;

	@Column(name="id_cliente_origen")
	private Integer idClienteOrigen;
	
	@Column(name="id_equipo_movil")
	private Integer idEquipoMovil;
	
	@Column(name="hora_desde")
	private String horaDesde;

	@Column(name="hora_hasta")
	private String horaHasta;
	
	@Column(name="sysdelete")
	private Integer sysdelete;
	
	@Column(name="estado_cliente")
	private Integer estadoCliente;
	
	@Column(name="id_cliente_factura")
	private Integer idClienteFactura;

	public OpeRutero() {
	}

	public Integer getIdRutero() {
		return this.idRutero;
	}

	public void setIdRutero(Integer idRutero) {
		this.idRutero = idRutero;
	}

	public Timestamp getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Timestamp fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public Integer getIdAgenciaDestino() {
		return this.idAgenciaDestino;
	}

	public void setIdAgenciaDestino(Integer idAgenciaDestino) {
		this.idAgenciaDestino = idAgenciaDestino;
	}

	public Integer getIdAgenciaOrigen() {
		return this.idAgenciaOrigen;
	}

	public void setIdAgenciaOrigen(Integer idAgenciaOrigen) {
		this.idAgenciaOrigen = idAgenciaOrigen;
	}

	public Integer getIdCircuito() {
		return this.idCircuito;
	}

	public void setIdCircuito(Integer idCircuito) {
		this.idCircuito = idCircuito;
	}

	public Integer getIdClienteDestino() {
		return this.idClienteDestino;
	}

	public void setIdClienteDestino(Integer idClienteDestino) {
		this.idClienteDestino = idClienteDestino;
	}

	public Integer getIdClienteOrigen() {
		return this.idClienteOrigen;
	}

	public void setIdClienteOrigen(Integer idClienteOrigen) {
		this.idClienteOrigen = idClienteOrigen;
	}

	public Integer getIdEquipoMovil() {
		return idEquipoMovil;
	}

	public void setIdEquipoMovil(Integer idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}

	public String getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}

	public String getHoraHasta() {
		return horaHasta;
	}

	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}

	public Integer getSysdelete() {
		return sysdelete;
	}

	public void setSysdelete(Integer sysdelete) {
		this.sysdelete = sysdelete;
	}

	public Integer getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliete(Integer estadoCliete) {
		this.estadoCliente = estadoCliete;
	}

	public Integer getIdClienteFactura() {
		return idClienteFactura;
	}

	public void setIdClienteFactura(Integer idClienteFactura) {
		this.idClienteFactura = idClienteFactura;
	}
	
	

}
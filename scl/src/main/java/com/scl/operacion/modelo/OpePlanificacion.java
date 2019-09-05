package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_planificacion database table.
 * 
 */
@Entity
@Table(name="ope_planificacion", schema = "java")
@NamedQuery(name="OpePlanificacion.findAll", query="SELECT o FROM OpePlanificacion o")
public class OpePlanificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_planificacion")
	private Integer idPlanificacion;

	

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_origen")
	private AdmAgencia idAgenciaOrigen;

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_destino")
	private AdmAgencia idAgenciaDestino;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente_origen")
	private AdmCliente idClienteOrigen;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente_destino")
	private AdmCliente idClienteDestino;
	
	@ManyToOne
	@JoinColumn(name="id_circuito")
	private AdmCliente idCircuito;

	public OpePlanificacion() {
	}

	public Integer getIdPlanificacion() {
		return this.idPlanificacion;
	}

	public void setIdPlanificacion(Integer idPlanificacion) {
		this.idPlanificacion = idPlanificacion;
	}

	

	public AdmAgencia getIdAgenciaOrigen() {
		return this.idAgenciaOrigen;
	}

	public void setIdAgenciaOrigen(AdmAgencia idAgenciaOrigen) {
		this.idAgenciaOrigen = idAgenciaOrigen;
	}

	public AdmAgencia getIdAgenciaDestino() {
		return this.idAgenciaDestino;
	}

	public void setIdAgenciaDestino(AdmAgencia idAgenciaDestino) {
		this.idAgenciaDestino = idAgenciaDestino;
	}

	public AdmCliente getIdClienteOrigen() {
		return this.idClienteOrigen;
	}

	public void setIdClienteOrigen(AdmCliente idClienteOrigen) {
		this.idClienteOrigen = idClienteOrigen;
	}

	public AdmCliente getIdClienteDestino() {
		return this.idClienteDestino;
	}

	public void setIdClienteDestino(AdmCliente idClienteDestino) {
		this.idClienteDestino = idClienteDestino;
	}

	public AdmCliente getIdCircuito() {
		return idCircuito;
	}

	public void setIdCircuito(AdmCliente idCircuito) {
		this.idCircuito = idCircuito;
	}
	
	

}
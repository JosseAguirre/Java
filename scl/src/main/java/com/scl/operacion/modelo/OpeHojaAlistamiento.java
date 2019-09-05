package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmDetalleCatalogo;




/**
 * The persistent class for the ope_hoja_alistamiento database table.
 * 
 */
@Entity
@NamedEntityGraph(name = "HojaAlistamiento-Frecuencia", attributeNodes = {
@NamedAttributeNode("idClienteOrigen"),
})
@Table(name="ope_hoja_alistamiento", schema = "java")
@NamedQuery(name="OpeHojaAlistamiento.findAll", query="SELECT o FROM OpeHojaAlistamiento o")
public class OpeHojaAlistamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_hoja_alistamiento")
	private Integer idHojaAlistamiento;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_destino")
	private AdmAgencia idAgenciaDestino;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_origen")
	private AdmAgencia idAgenciaOrigen;

	//uni-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente_destino")
	private AdmCliente idClienteDestino;

	//uni-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente_origen")
	private AdmCliente idClienteOrigen;
	
	@ManyToOne
	@JoinColumn(name="id_ciudad")
	private AdmDetalleCatalogo idCiudad;

	//bi-directional many-to-one association to OpeCircuito
	@ManyToOne
	@JoinColumn(name="id_circuito")
	private OpeCircuito idCircuito;

	public OpeHojaAlistamiento() {
	}

	public Integer getIdHojaAlistamiento() {
		return this.idHojaAlistamiento;
	}

	public void setIdHojaAlistamiento(Integer idHojaAlistamiento) {
		this.idHojaAlistamiento = idHojaAlistamiento;
	}

	public AdmAgencia getIdAgenciaDestino() {
		return this.idAgenciaDestino;
	}

	public void setIdAgenciaDestino(AdmAgencia idAgenciaDestino) {
		this.idAgenciaDestino = idAgenciaDestino;
	}

	public AdmAgencia getIdAgenciaOrigen() {
		return this.idAgenciaOrigen;
	}

	public void setIdAgenciaOrigen(AdmAgencia idAgenciaOrigen) {
		this.idAgenciaOrigen = idAgenciaOrigen;
	}

	public AdmCliente getIdClienteDestino() {
		return this.idClienteDestino;
	}

	public void setIdClienteDestino(AdmCliente idClienteDestino) {
		this.idClienteDestino = idClienteDestino;
	}

	public AdmCliente getIdClienteOrigen() {
		return this.idClienteOrigen;
	}

	public void setIdClienteOrigen(AdmCliente idClienteOrigen) {
		this.idClienteOrigen = idClienteOrigen;
	}

	public AdmDetalleCatalogo getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(AdmDetalleCatalogo idCiudad) {
		this.idCiudad = idCiudad;
	}

	public OpeCircuito getIdCircuito() {
		return idCircuito;
	}

	public void setIdCircuito(OpeCircuito idCircuito) {
		this.idCircuito = idCircuito;
	}

}
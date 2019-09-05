package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ope_detalle_transacion database table.
 * 
 */
@Entity
@Table(name="ope_detalle_transaccion", schema = "java")
@NamedQuery(name="OpeDetalleTransaccion.findAll", query="SELECT o FROM OpeDetalleTransaccion o")
public class OpeDetalleTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_transaccion")
	private Integer idDetalleTransaccion;

	@Column(name="cantidad_cheques")
	private Integer cantidadCheques;

	private float cheque;

	private float efectivo;

	

	
	private String sello;

	private String tula;

	@Column(name="validado_ingreso")
	private Boolean validadoIngreso;

	@Column(name="validado_salida")
	private Boolean validadoSalida;

	//bi-directional many-to-one association to OpeTransaccion
	@ManyToOne
	@JoinColumn(name="id_transaccion")
	private OpeTransaccion idTransaccion;

	public OpeDetalleTransaccion() {
	}

	public Integer getIdDetalleTransaccion() {
		return this.idDetalleTransaccion;
	}

	public void setIdDetalleTransaccion(Integer idDetalleTransaccion) {
		this.idDetalleTransaccion = idDetalleTransaccion;
	}

	public Integer getCantidadCheques() {
		return this.cantidadCheques;
	}

	public void setCantidadCheques(Integer cantidadCheques) {
		this.cantidadCheques = cantidadCheques;
	}

	public float getCheque() {
		return this.cheque;
	}

	public void setCheque(float cheque) {
		this.cheque = cheque;
	}

	public float getEfectivo() {
		return this.efectivo;
	}

	public void setEfectivo(float efectivo) {
		this.efectivo = efectivo;
	}

	

	public String getSello() {
		return this.sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getTula() {
		return this.tula;
	}

	public void setTula(String tula) {
		this.tula = tula;
	}

	public Boolean getValidadoIngreso() {
		return this.validadoIngreso;
	}

	public void setValidadoIngreso(Boolean validadoIngreso) {
		this.validadoIngreso = validadoIngreso;
	}

	public Boolean getValidadoSalida() {
		return this.validadoSalida;
	}

	public void setValidadoSalida(Boolean validadoSalida) {
		this.validadoSalida = validadoSalida;
	}

	public OpeTransaccion getIdTransaccion() {
		return this.idTransaccion;
	}

	public void setIdTransaccion(OpeTransaccion idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

}
package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_papeleta database table.
 * 
 */
@Entity
@Table(name="cpc_papeleta", schema = "java")
@NamedQuery(name="CpcPapeleta.findAll", query="SELECT c FROM CpcPapeleta c")
public class CpcPapeleta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_papeleta")
	private Integer idPapeleta;

	@Column(name="numero_cuenta")
	private String numeroCuenta;

	@Column(name="numero_papeleta")
	private String numeroPapeleta;

	@Column(name="valor_depositado")
	private float valorDepositado;

	@Column(name="valor_novedad")
	private float valorNovedad;

	//uni-directional many-to-one association to CpcBanco
	@ManyToOne
	@JoinColumn(name="id_banco")
	private CpcBanco id_banco;
	
	//uni-directional many-to-one association to CpcDetalleDistribucion
		@ManyToOne
		@JoinColumn(name="id_detalle_distribucion")
		private CpcDetalleDistribucion idDetalleDistribucion;

	public CpcPapeleta() {
	}

	public Integer getIdPapeleta() {
		return this.idPapeleta;
	}

	public void setIdPapeleta(Integer idPapeleta) {
		this.idPapeleta = idPapeleta;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNumeroPapeleta() {
		return this.numeroPapeleta;
	}

	public void setNumeroPapeleta(String numeroPapelea) {
		this.numeroPapeleta = numeroPapelea;
	}

	public float getValorDepositado() {
		return this.valorDepositado;
	}

	public void setValorDepositado(float valorDepositado) {
		this.valorDepositado = valorDepositado;
	}

	public float getValorNovedad() {
		return this.valorNovedad;
	}

	public void setValorNovedad(float valorNovedad) {
		this.valorNovedad = valorNovedad;
	}

	public CpcBanco getId_banco() {
		return this.id_banco;
	}

	public void setId_banco(CpcBanco id_banco) {
		this.id_banco = id_banco;
	}

	public CpcDetalleDistribucion getIdDetalleDistribucion() {
		return idDetalleDistribucion;
	}

	public void setIdDetalleDistribucion(CpcDetalleDistribucion idDetalleDistribucion) {
		this.idDetalleDistribucion = idDetalleDistribucion;
	}

	
}
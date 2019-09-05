package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cpc_novedad database table.
 * 
 */
@Entity
@Table(name="cpc_novedad", schema = "java")
@NamedQuery(name="CpcNovedad.findAll", query="SELECT c FROM CpcNovedad c")
public class CpcNovedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_novedad")
	private Integer idNovedad;

	private float valor;

	//uni-directional many-to-one association to CpcPapeleta
	@ManyToOne
	@JoinColumn(name="id_papeleta")
	private CpcPapeleta idPapeleta;

	//uni-directional many-to-one association to CpcTipoNovedad
	@ManyToOne
	@JoinColumn(name="id_tipo_novedad")
	private CpcTipoNovedad idTipoNovedad;
	
	//uni-directional many-to-one association to CpcDetalleDistribucion
		@ManyToOne
		@JoinColumn(name="id_detalle_distribucion")
		private CpcDetalleDistribucion idDetalleDistribucion;

	public CpcNovedad() {
	}

	public Integer getIdNovedad() {
		return this.idNovedad;
	}

	public void setIdNovedad(Integer idNovedad) {
		this.idNovedad = idNovedad;
	}


	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}




	public CpcPapeleta getIdPapeleta() {
		return idPapeleta;
	}

	public void setIdPapeleta(CpcPapeleta idPapeleta) {
		this.idPapeleta = idPapeleta;
	}

	public CpcTipoNovedad getIdTipoNovedad() {
		return idTipoNovedad;
	}

	public void setIdTipoNovedad(CpcTipoNovedad idTipoNovedad) {
		this.idTipoNovedad = idTipoNovedad;
	}

	public CpcDetalleDistribucion getIdDetalleDistribucion() {
		return idDetalleDistribucion;
	}

	public void setIdDetalleDistribucion(CpcDetalleDistribucion idDetalleDistribucion) {
		this.idDetalleDistribucion = idDetalleDistribucion;
	}

	
	
}
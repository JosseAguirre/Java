package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.administracion.modelo.*;


/**
 * The persistent class for the ope_destino_provisional database table.
 * 
 */
@Entity
@Table(name="ope_destino_provisional", schema = "java")
@NamedQuery(name="OpeDestinoProvisional.findAll", query="SELECT o FROM OpeDestinoProvisional o")
public class OpeDestinoProvisional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_destino_provisional")
	private Integer idDestinoProvisional;

	@Column(name="codigo_barras")
	private String codigoBarras;

	private String nombre;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;

	//bi-directional many-to-one association to OpeTipoDestino
	@ManyToOne
	@JoinColumn(name="id_tipo_destino")
	private OpeTipoDestino idTipoDestino;

	public OpeDestinoProvisional() {
	}

	public Integer getIdDestinoProvisional() {
		return this.idDestinoProvisional;
	}

	public void setIdDestinoProvisional(Integer idDestinoProvisional) {
		this.idDestinoProvisional = idDestinoProvisional;
	}

	public String getCodigoBarras() {
		return this.codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public OpeTipoDestino getIdTipoDestino() {
		return this.idTipoDestino;
	}

	public void setIdTipoDestino(OpeTipoDestino idTipoDestino) {
		this.idTipoDestino = idTipoDestino;
	}

}
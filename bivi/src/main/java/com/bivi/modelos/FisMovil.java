package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_movil database table.
 * 
 */
@Entity
@Table(name="fis_movil", schema = "bivi")
@NamedQuery(name="FisMovil.findAll", query="SELECT f FROM FisMovil f")
public class FisMovil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_movil")
	private Integer idMovil;

	private String descripcion;

	@Column(name="id_android")
	private String idAndroid;

	
	@Column(name="nominativo")
	private String nominativo;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;
	
	//uni-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;
	
	

	public FisMovil() {
	}

	public Integer getIdMovil() {
		return this.idMovil;
	}

	public void setIdMovil(Integer idMovil) {
		this.idMovil = idMovil;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdAndroid() {
		return this.idAndroid;
	}

	public void setIdAndroid(String idAndroid) {
		this.idAndroid = idAndroid;
	}



	public String getNominativo() {
		return this.nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	


	public AdmDetalleCatalogo getIdCiudadDc() {
		return idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public AdmAgencia getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}



}
package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_consignas_especificas_puestos database table.
 * 
 */
@Entity
@Table(name="fis_consignas_especificas_puestos", schema = "bivi")
@NamedQuery(name="FisConsignasEspecificasPuesto.findAll", query="SELECT f FROM FisConsignasEspecificasPuesto f")
public class FisConsignasEspecificasPuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_consigna_especifica_puesto")
	private Integer idConsignaEspecificaPuesto;

	private String consigna;

	@Column(name="url_imagen")
	private String urlImagen;

	//uni-directional many-to-one association to AdmPuesto
	@ManyToOne
	@JoinColumn(name="id_puesto")
	private AdmPuesto idPuesto;

	public FisConsignasEspecificasPuesto() {
	}

	public Integer getIdConsignaEspecificaPuesto() {
		return this.idConsignaEspecificaPuesto;
	}

	public void setIdConsignaEspecificaPuesto(Integer idConsignaEspecificaPuesto) {
		this.idConsignaEspecificaPuesto = idConsignaEspecificaPuesto;
	}

	public String getConsigna() {
		return this.consigna;
	}

	public void setConsigna(String consigna) {
		this.consigna = consigna;
	}

	public String getUrlImagen() {
		return this.urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public AdmPuesto getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto(AdmPuesto idPuesto) {
		this.idPuesto = idPuesto;
	}

}
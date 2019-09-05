package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_consignas_especificas_puestos database table.
 * 
 */
@Entity
@Table(name="fis_consignas_generales", schema = "bivi")

public class FisConsignasGenerales implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_consigna_g")
	private Integer idConsignaG;

	private String consigna;

	@Column(name="estado")
	private int estado;

	public Integer getIdConsignaG() {
		return idConsignaG;
	}

	public void setIdConsignaG(Integer idConsignaG) {
		this.idConsignaG = idConsignaG;
	}

	public String getConsigna() {
		return consigna;
	}

	public void setConsigna(String consigna) {
		this.consigna = consigna;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	
	
	
	
	
}
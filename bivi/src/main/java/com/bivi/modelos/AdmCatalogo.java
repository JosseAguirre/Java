package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_catalogo database table.
 * 
 */
@Entity
@Table(name="adm_catalogo", schema = "bivi")
@NamedQuery(name="AdmCatalogo.findAll", query="SELECT a FROM AdmCatalogo a")
public class AdmCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_catalogo")
	private Integer idCatalogo;

	@Column(name="id_catalogo_padre")
	private Integer idCatalogoPadre;

	private String nombre;

	public AdmCatalogo() {
	}

	public Integer getIdCatalogo() {
		return this.idCatalogo;
	}

	public void setIdCatalogo(Integer idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	public Integer getIdCatalogoPadre() {
		return this.idCatalogoPadre;
	}

	public void setIdCatalogoPadre(Integer idCatalogoPadre) {
		this.idCatalogoPadre = idCatalogoPadre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
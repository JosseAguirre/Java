package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adm_tipo_catalogo database table.
 * 
 */
@Entity
@Table(name="adm_tipo_catalogo", schema = "java")
@NamedQuery(name="AdmTipoCatalogo.findAll", query="SELECT a FROM AdmTipoCatalogo a")
public class AdmTipoCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_catalogo")
	private Integer idTipoCatalogo;

	private String nombre;

	//bi-directional many-to-one association to AdmCatalogo
	@OneToMany(mappedBy="idTipoCatalogo")
	private List<AdmCatalogo> admCatalogos;

	public AdmTipoCatalogo() {
	}

	public Integer getIdTipoCatalogo() {
		return this.idTipoCatalogo;
	}

	public void setIdTipoCatalogo(Integer idTipoCatalogo) {
		this.idTipoCatalogo = idTipoCatalogo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AdmCatalogo> getAdmCatalogos() {
		return this.admCatalogos;
	}

	public void setAdmCatalogos(List<AdmCatalogo> admCatalogos) {
		this.admCatalogos = admCatalogos;
	}

	public AdmCatalogo addAdmCatalogo(AdmCatalogo admCatalogo) {
		getAdmCatalogos().add(admCatalogo);
		admCatalogo.setIdTipoCatalogo(this);

		return admCatalogo;
	}

	public AdmCatalogo removeAdmCatalogo(AdmCatalogo admCatalogo) {
		getAdmCatalogos().remove(admCatalogo);
		admCatalogo.setIdTipoCatalogo(null);

		return admCatalogo;
	}

}
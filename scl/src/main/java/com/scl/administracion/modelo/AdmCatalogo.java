package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adm_catalogo database table.
 * 
 */
@Entity
@Table(name="adm_catalogo", schema = "java")
@NamedQuery(name="AdmCatalogo.findAll", query="SELECT a FROM AdmCatalogo a")
public class AdmCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_catalogo")
	private Integer idCatalogo;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to AdmCatalogo
	@ManyToOne
	@JoinColumn(name="id_padre_catalogo")
	private AdmCatalogo idPadreCatalogo;

	//bi-directional many-to-one association to AdmCatalogo
	@OneToMany(mappedBy="idPadreCatalogo")
	private List<AdmCatalogo> admCatalogos;

	//bi-directional many-to-one association to AdmTipoCatalogo
	@ManyToOne
	@JoinColumn(name="id_tipo_catalogo")
	private AdmTipoCatalogo idTipoCatalogo;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@OneToMany(mappedBy="idCatalogo")
	private List<AdmDetalleCatalogo> admDetalleCatalogos;

	public AdmCatalogo() {
	}

	public Integer getIdCatalogo() {
		return this.idCatalogo;
	}

	public void setIdCatalogo(Integer idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public AdmCatalogo getIdPadreCatalogo() {
		return this.idPadreCatalogo;
	}

	public void setIdPadreCatalogo(AdmCatalogo idPadreCatalogo) {
		this.idPadreCatalogo = idPadreCatalogo;
	}

	public List<AdmCatalogo> getAdmCatalogos() {
		return this.admCatalogos;
	}

	public void setAdmCatalogos(List<AdmCatalogo> admCatalogos) {
		this.admCatalogos = admCatalogos;
	}

	public AdmCatalogo addAdmCatalogo(AdmCatalogo admCatalogo) {
		getAdmCatalogos().add(admCatalogo);
		admCatalogo.setIdPadreCatalogo(this);

		return admCatalogo;
	}

	public AdmCatalogo removeAdmCatalogo(AdmCatalogo admCatalogo) {
		getAdmCatalogos().remove(admCatalogo);
		admCatalogo.setIdPadreCatalogo(null);

		return admCatalogo;
	}

	public AdmTipoCatalogo getIdTipoCatalogo() {
		return this.idTipoCatalogo;
	}

	public void setIdTipoCatalogo(AdmTipoCatalogo idTipoCatalogo) {
		this.idTipoCatalogo = idTipoCatalogo;
	}

	public List<AdmDetalleCatalogo> getAdmDetalleCatalogos() {
		return this.admDetalleCatalogos;
	}

	public void setAdmDetalleCatalogos(List<AdmDetalleCatalogo> admDetalleCatalogos) {
		this.admDetalleCatalogos = admDetalleCatalogos;
	}

	public AdmDetalleCatalogo addAdmDetalleCatalogo(AdmDetalleCatalogo admDetalleCatalogo) {
		getAdmDetalleCatalogos().add(admDetalleCatalogo);
		admDetalleCatalogo.setIdCatalogo(this);

		return admDetalleCatalogo;
	}

	public AdmDetalleCatalogo removeAdmDetalleCatalogo(AdmDetalleCatalogo admDetalleCatalogo) {
		getAdmDetalleCatalogos().remove(admDetalleCatalogo);
		admDetalleCatalogo.setIdCatalogo(null);

		return admDetalleCatalogo;
	}

}
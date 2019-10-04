package com.bivi.modelos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;





/**
 * The persistent class for the adm_detalle_catalogo database table.
 * 
 */
@Entity
@Table(name="adm_detalle_catalogo", schema = "bivi")
@NamedQuery(name="AdmDetalleCatalogo.findAll", query="SELECT a FROM AdmDetalleCatalogo a")
public class AdmDetalleCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_catalogo")
	private Integer idDetalleCatalogo;

	private String descripcion;

	@Column(name="id_detalle_catalogo_padre")
	private Integer idDetalleCatalogoPadre;

	//uni-directional many-to-one association to AdmCatalogo
	@ManyToOne
	@JoinColumn(name="id_catalogo")
	private AdmCatalogo idCatalogo;
	
	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idCiudad")
	private List<AdmCliente> admClientes1;
	
	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idTipoClienteCatalogo")
	private List<AdmCliente> admClientes;
	
	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idCiudad")
	private List<AdmAgencia> admAgencias1;
	
	//bi-directional many-to-one association to AdmPuesto
	@OneToMany(mappedBy="idTipoPuestoCatalogo")
	private List<AdmPuesto> admPuesto;
	
	//bi-directional many-to-one association to admDependencia
	@OneToMany(mappedBy="idTipoDetalleCatalogo")
	private List<AdmDependencia> admDependencia;
	
	//bi-directional many-to-one association to FisTurnoDia
	@OneToMany(mappedBy="idDia")
	private List<FisTurnoDia> fisTurnoDia;

	public AdmDetalleCatalogo() {
	}

	public Integer getIdDetalleCatalogo() {
		return this.idDetalleCatalogo;
	}

	public void setIdDetalleCatalogo(Integer idDetalleCatalogo) {
		this.idDetalleCatalogo = idDetalleCatalogo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdDetalleCatalogoPadre() {
		return this.idDetalleCatalogoPadre;
	}

	public void setIdDetalleCatalogoPadre(Integer idDetalleCatalogoPadre) {
		this.idDetalleCatalogoPadre = idDetalleCatalogoPadre;
	}

	public AdmCatalogo getIdCatalogo() {
		return this.idCatalogo;
	}

	public void setIdCatalogo(AdmCatalogo idCatalogo) {
		this.idCatalogo = idCatalogo;
	}
	
	public List<AdmCliente> getAdmClientes1() {
		return this.admClientes1;
	}

	public void setAdmClientes1(List<AdmCliente> admClientes1) {
		this.admClientes1 = admClientes1;
	}
	
	public List<AdmCliente> getAdmClientes() {
		return this.admClientes;
	}

	public void setAdmClientes(List<AdmCliente> admClientes) {
		this.admClientes = admClientes;
	}
	
	public List<AdmAgencia> getAdmAgencias1() {
		return this.admAgencias1;
	}

	public void setAdmAgencias1(List<AdmAgencia> admAgencias1) {
		this.admAgencias1 = admAgencias1;
	}
	
	public List<AdmPuesto> getAdmPuesto() {
		return this.admPuesto;
	}

	public void setAdmPuesto(List<AdmPuesto> admPuesto) {
		this.admPuesto = admPuesto;
	}
	
	public List<AdmDependencia> getAdmDependencia() {
		return this.admDependencia;
	}

	public void setAdmDependencia(List<AdmDependencia> admDependencia) {
		this.admDependencia = admDependencia;
	}
	
	public List<FisTurnoDia> getFisTurnoDia() {
		return this.fisTurnoDia;
	}

	public void setFisTurnoDia(List<FisTurnoDia> fisTurnoDia) {
		this.fisTurnoDia = fisTurnoDia;
	}

}
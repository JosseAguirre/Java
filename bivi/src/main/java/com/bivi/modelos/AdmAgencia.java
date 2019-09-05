package com.bivi.modelos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the adm_agencia database table.
 * 
 */
@Entity
@Table(name="adm_agencia", schema = "bivi")
@NamedQuery(name="AdmAgencia.findAll", query="SELECT a FROM AdmAgencia a")
public class AdmAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_agencia")
	private Integer idAgencia;

	@Column(name="celular_administrador")
	private String celularAdministrador;

	private String direccion;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad")
	private AdmDetalleCatalogo idCiudad;

	private String nombre;

	@Column(name="nombre_administrador")
	private String nombreAdministrador;

	//uni-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AdmCliente idCliente;
	
	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idAgencia")
	private List<AdmPuesto> admPuesto;

	public AdmAgencia() {
	}

	public Integer getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getCelularAdministrador() {
		return this.celularAdministrador;
	}

	public void setCelularAdministrador(String celularAdministrador) {
		this.celularAdministrador = celularAdministrador;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public AdmDetalleCatalogo getIdCiudad() {
		return this.idCiudad;
	}

	public void setIdCiudad(AdmDetalleCatalogo idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreAdministrador() {
		return this.nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public AdmCliente getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(AdmCliente idCliente) {
		this.idCliente = idCliente;
	}
	
	public List<AdmPuesto> getAdmPuesto() {
		return this.admPuesto;
	}

	public void setAdmPuesto(List<AdmPuesto> admPuesto) {
		this.admPuesto = admPuesto;
	}

}
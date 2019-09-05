package com.bivi.modelos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;






/**
 * The persistent class for the adm_cliente database table.
 * 
 */
@Entity
@Table(name="adm_cliente", schema = "bivi")
@NamedQuery(name="AdmCliente.findAll", query="SELECT a FROM AdmCliente a")
public class AdmCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cliente")
	private Integer idCliente;

	private String administrador;

	private String direccion;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad")
	private AdmDetalleCatalogo idCiudad;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente_padre")
	private AdmCliente idClientePadre;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idClientePadre")
	private List<AdmCliente> admClientes;

	private String identificacion;

	@Column(name="nombre_comercial")
	private String nombreComercial;

	@Column(name="razon_social")
	private String razonSocial;

	@Column(name="telefono_administrador")
	private String telefonoAdministrador;
	
	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idCliente")
	private List<AdmAgencia> admAgencias;

	public AdmCliente() {
	}

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getAdministrador() {
		return this.administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
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

	public AdmCliente getIdClientePadre() {
		return this.idClientePadre;
	}

	public void setIdClientePadre(AdmCliente idClientePadre) {
		this.idClientePadre = idClientePadre;
	}

	public List<AdmCliente> getAdmClientes() {
		return this.admClientes;
	}

	public void setAdmClientes(List<AdmCliente> admClientes) {
		this.admClientes = admClientes;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombreComercial() {
		return this.nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTelefonoAdministrador() {
		return this.telefonoAdministrador;
	}

	public void setTelefonoAdministrador(String telefonoAdministrador) {
		this.telefonoAdministrador = telefonoAdministrador;
	}
	
	public List<AdmAgencia> getAdmAgencias() {
		return this.admAgencias;
	}

	public void setAdmAgencias(List<AdmAgencia> admAgencias) {
		this.admAgencias = admAgencias;
	}

	public AdmAgencia addAdmAgencia(AdmAgencia admAgencia) {
		getAdmAgencias().add(admAgencia);
		admAgencia.setIdCliente(this);

		return admAgencia;
	}

	public AdmAgencia removeAdmAgencia(AdmAgencia admAgencia) {
		getAdmAgencias().remove(admAgencia);
		admAgencia.setIdCliente(null);

		return admAgencia;
	}

}
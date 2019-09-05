package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import com.scl.operacion.modelo.*;


/**
 * The persistent class for the adm_tipo_servicio database table.
 * 
 */
@Entity
@Table(name="adm_tipo_servicio", schema = "java")
@NamedQuery(name="AdmTipoServicio.findAll", query="SELECT a FROM AdmTipoServicio a")
public class AdmTipoServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_servicio")
	private Integer idTipoServicio;

	private String nombre;

	//bi-directional many-to-one association to AdmServicio
	@OneToMany(mappedBy="idTipoServicio")
	private List<AdmServicio> admServicios;

	//bi-directional many-to-one association to OpeParametroCliente
	@OneToMany(mappedBy="idTipoServicio")
	private List<OpeParametroCliente> opeParametroClientes;

	public AdmTipoServicio() {
	}

	public Integer getIdTipoServicio() {
		return this.idTipoServicio;
	}

	public void setIdTipoServicio(Integer idTipoServicio) {
		this.idTipoServicio = idTipoServicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AdmServicio> getAdmServicios() {
		return this.admServicios;
	}

	public void setAdmServicios(List<AdmServicio> admServicios) {
		this.admServicios = admServicios;
	}

	public AdmServicio addAdmServicio(AdmServicio admServicio) {
		getAdmServicios().add(admServicio);
		admServicio.setIdTipoServicio(this);

		return admServicio;
	}

	public AdmServicio removeAdmServicio(AdmServicio admServicio) {
		getAdmServicios().remove(admServicio);
		admServicio.setIdTipoServicio(null);

		return admServicio;
	}

	public List<OpeParametroCliente> getOpeParametroClientes() {
		return this.opeParametroClientes;
	}

	public void setOpeParametroClientes(List<OpeParametroCliente> opeParametroClientes) {
		this.opeParametroClientes = opeParametroClientes;
	}

	public OpeParametroCliente addOpeParametroCliente(OpeParametroCliente opeParametroCliente) {
		getOpeParametroClientes().add(opeParametroCliente);
		opeParametroCliente.setIdTipoServicio(this);

		return opeParametroCliente;
	}

	public OpeParametroCliente removeOpeParametroCliente(OpeParametroCliente opeParametroCliente) {
		getOpeParametroClientes().remove(opeParametroCliente);
		opeParametroCliente.setIdTipoServicio(null);

		return opeParametroCliente;
	}

}
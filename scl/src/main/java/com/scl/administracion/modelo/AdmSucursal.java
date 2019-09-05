package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adm_sucursal database table.
 * 
 */
@Entity
@Table(name="adm_sucursal", schema = "java")
@NamedQuery(name="AdmSucursal.findAll", query="SELECT a FROM AdmSucursal a")
public class AdmSucursal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_sucursal")
	private Integer idSucursal;

	private String direccion;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idSucursal")
	private List<AdmEmpleado> admEmpleados;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;

	public AdmSucursal() {
	}

	public Integer getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<AdmEmpleado> getAdmEmpleados() {
		return this.admEmpleados;
	}

	public void setAdmEmpleados(List<AdmEmpleado> admEmpleados) {
		this.admEmpleados = admEmpleados;
	}

	public AdmEmpleado addAdmEmpleado(AdmEmpleado admEmpleado) {
		getAdmEmpleados().add(admEmpleado);
		admEmpleado.setIdSucursal(this);

		return admEmpleado;
	}

	public AdmEmpleado removeAdmEmpleado(AdmEmpleado admEmpleado) {
		getAdmEmpleados().remove(admEmpleado);
		admEmpleado.setIdSucursal(null);

		return admEmpleado;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

}
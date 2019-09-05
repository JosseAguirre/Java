package com.scl.administracion.modelo;

import java.io.Serializable;
import com.scl.operacion.modelo.*;



import javax.persistence.*;

import org.hibernate.annotations.Proxy;

import java.util.List;


/**
 * The persistent class for the adm_detalle_catalogo database table.
 * 
 */
@Entity
@Table(name="adm_detalle_catalogo", schema = "java")
@NamedQuery(name="AdmDetalleCatalogo.findAll", query="SELECT a FROM AdmDetalleCatalogo a")
@Proxy(lazy = false)
public class AdmDetalleCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_catalogo")
	private Integer idDetalleCatalogo;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idCiudadDc")
	private List<AdmAgencia> admAgencias1;

	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idEstadoDc")
	private List<AdmAgencia> admAgencias2;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idCiudadDc")
	private List<AdmCliente> admClientes1;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idActividadDc")
	private List<AdmCliente> admClientes2;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idEstadoDc")
	private List<AdmCliente> admClientes3;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idPrioridadDc")
	private List<AdmCliente> admClientes4;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idTipoClienteDc")
	private List<AdmCliente> admClientes5;

	//bi-directional many-to-one association to AdmCatalogo
	@ManyToOne
	@JoinColumn(name="id_catalogo")
	private AdmCatalogo idCatalogo;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_detalle_catalogo_padre")
	private AdmDetalleCatalogo idDetalleCatalogoPadre;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@OneToMany(mappedBy="idDetalleCatalogoPadre")
	private List<AdmDetalleCatalogo> admDetalleCatalogos;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idCargoDc")
	private List<AdmEmpleado> admEmpleados1;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idCiudadDc")
	private List<AdmEmpleado> admEmpleados2;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idClaseEmpleadoDc")
	private List<AdmEmpleado> admEmpleados3;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idDepartamentoDc")
	private List<AdmEmpleado> admEmpleados4;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idEstadoDc")
	private List<AdmEmpleado> admEmpleados5;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idSexoDc")
	private List<AdmEmpleado> admEmpleados6;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idLineaNegocioDc")
	private List<AdmEmpleado> admEmpleados7;

	//bi-directional many-to-one association to AdmEmpleado
	@OneToMany(mappedBy="idTipoEmpleadoDc")
	private List<AdmEmpleado> admEmpleados8;

	//bi-directional many-to-one association to AdmServicio
	@OneToMany(mappedBy="idEstadoDc")
	private List<AdmServicio> admServicios1;

	//bi-directional many-to-one association to AdmServicio
	@OneToMany(mappedBy="idLineaNegocio")
	private List<AdmServicio> admServicios2;

	//bi-directional many-to-one association to AdmSucursal
	@OneToMany(mappedBy="idCiudadDc")
	private List<AdmSucursal> admSucursals;

	//bi-directional many-to-one association to AdmUsuario
	@OneToMany(mappedBy="idEstadoDc")
	private List<AdmUsuario> admUsuarios;

	//bi-directional many-to-one association to AdmUsuarioCiudad
	@OneToMany(mappedBy="idCiudadDc")
	private List<AdmUsuarioCiudad> admUsuarioCiudads;

	//bi-directional many-to-one association to OpeDestinoProvisional
	@OneToMany(mappedBy="idCiudadDc")
	private List<OpeDestinoProvisional> opeDestinoProvisionals;

	//bi-directional many-to-one association to OpeResponsable
	@OneToMany(mappedBy="idEstadoDc")
	private List<OpeResponsable> opeResponsables;

	//bi-directional many-to-one association to OpeVehiculo
	//@OneToMany(mappedBy="idCiudadDc")
	//private List<OpeVehiculo> opeVehiculos;

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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AdmAgencia> getAdmAgencias1() {
		return this.admAgencias1;
	}

	public void setAdmAgencias1(List<AdmAgencia> admAgencias1) {
		this.admAgencias1 = admAgencias1;
	}

	public AdmAgencia addAdmAgencias1(AdmAgencia admAgencias1) {
		getAdmAgencias1().add(admAgencias1);
		admAgencias1.setIdCiudadDc(this);

		return admAgencias1;
	}

	public AdmAgencia removeAdmAgencias1(AdmAgencia admAgencias1) {
		getAdmAgencias1().remove(admAgencias1);
		admAgencias1.setIdCiudadDc(null);

		return admAgencias1;
	}

	public List<AdmAgencia> getAdmAgencias2() {
		return this.admAgencias2;
	}

	public void setAdmAgencias2(List<AdmAgencia> admAgencias2) {
		this.admAgencias2 = admAgencias2;
	}

	public AdmAgencia addAdmAgencias2(AdmAgencia admAgencias2) {
		getAdmAgencias2().add(admAgencias2);
		admAgencias2.setIdEstadoDc(this);

		return admAgencias2;
	}

	public AdmAgencia removeAdmAgencias2(AdmAgencia admAgencias2) {
		getAdmAgencias2().remove(admAgencias2);
		admAgencias2.setIdEstadoDc(null);

		return admAgencias2;
	}

	public List<AdmCliente> getAdmClientes1() {
		return this.admClientes1;
	}

	public void setAdmClientes1(List<AdmCliente> admClientes1) {
		this.admClientes1 = admClientes1;
	}

	public AdmCliente addAdmClientes1(AdmCliente admClientes1) {
		getAdmClientes1().add(admClientes1);
		admClientes1.setIdCiudadDc(this);

		return admClientes1;
	}

	public AdmCliente removeAdmClientes1(AdmCliente admClientes1) {
		getAdmClientes1().remove(admClientes1);
		admClientes1.setIdCiudadDc(null);

		return admClientes1;
	}

	public List<AdmCliente> getAdmClientes2() {
		return this.admClientes2;
	}

	public void setAdmClientes2(List<AdmCliente> admClientes2) {
		this.admClientes2 = admClientes2;
	}

	public AdmCliente addAdmClientes2(AdmCliente admClientes2) {
		getAdmClientes2().add(admClientes2);
		admClientes2.setIdActividadDc(this);

		return admClientes2;
	}

	public AdmCliente removeAdmClientes2(AdmCliente admClientes2) {
		getAdmClientes2().remove(admClientes2);
		admClientes2.setIdActividadDc(null);

		return admClientes2;
	}

	public List<AdmCliente> getAdmClientes3() {
		return this.admClientes3;
	}

	public void setAdmClientes3(List<AdmCliente> admClientes3) {
		this.admClientes3 = admClientes3;
	}

	public AdmCliente addAdmClientes3(AdmCliente admClientes3) {
		getAdmClientes3().add(admClientes3);
		admClientes3.setIdEstadoDc(this);

		return admClientes3;
	}

	public AdmCliente removeAdmClientes3(AdmCliente admClientes3) {
		getAdmClientes3().remove(admClientes3);
		admClientes3.setIdEstadoDc(null);

		return admClientes3;
	}

	public List<AdmCliente> getAdmClientes4() {
		return this.admClientes4;
	}

	public void setAdmClientes4(List<AdmCliente> admClientes4) {
		this.admClientes4 = admClientes4;
	}

	public AdmCliente addAdmClientes4(AdmCliente admClientes4) {
		getAdmClientes4().add(admClientes4);
		admClientes4.setIdPrioridadDc(this);

		return admClientes4;
	}

	public AdmCliente removeAdmClientes4(AdmCliente admClientes4) {
		getAdmClientes4().remove(admClientes4);
		admClientes4.setIdPrioridadDc(null);

		return admClientes4;
	}

	public List<AdmCliente> getAdmClientes5() {
		return this.admClientes5;
	}

	public void setAdmClientes5(List<AdmCliente> admClientes5) {
		this.admClientes5 = admClientes5;
	}

	public AdmCliente addAdmClientes5(AdmCliente admClientes5) {
		getAdmClientes5().add(admClientes5);
		admClientes5.setIdTipoClienteDc(this);

		return admClientes5;
	}

	public AdmCliente removeAdmClientes5(AdmCliente admClientes5) {
		getAdmClientes5().remove(admClientes5);
		admClientes5.setIdTipoClienteDc(null);

		return admClientes5;
	}

	public AdmCatalogo getIdCatalogo() {
		return this.idCatalogo;
	}

	public void setIdCatalogo(AdmCatalogo idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	public AdmDetalleCatalogo getIdDetalleCatalogoPadre() {
		return this.idDetalleCatalogoPadre;
	}

	public void setIdDetalleCatalogoPadre(AdmDetalleCatalogo idDetalleCatalogoPadre) {
		this.idDetalleCatalogoPadre = idDetalleCatalogoPadre;
	}

	public List<AdmDetalleCatalogo> getAdmDetalleCatalogos() {
		return this.admDetalleCatalogos;
	}

	public void setAdmDetalleCatalogos(List<AdmDetalleCatalogo> admDetalleCatalogos) {
		this.admDetalleCatalogos = admDetalleCatalogos;
	}

	public AdmDetalleCatalogo addAdmDetalleCatalogo(AdmDetalleCatalogo admDetalleCatalogo) {
		getAdmDetalleCatalogos().add(admDetalleCatalogo);
		admDetalleCatalogo.setIdDetalleCatalogoPadre(this);

		return admDetalleCatalogo;
	}

	public AdmDetalleCatalogo removeAdmDetalleCatalogo(AdmDetalleCatalogo admDetalleCatalogo) {
		getAdmDetalleCatalogos().remove(admDetalleCatalogo);
		admDetalleCatalogo.setIdDetalleCatalogoPadre(null);

		return admDetalleCatalogo;
	}

	public List<AdmEmpleado> getAdmEmpleados1() {
		return this.admEmpleados1;
	}

	public void setAdmEmpleados1(List<AdmEmpleado> admEmpleados1) {
		this.admEmpleados1 = admEmpleados1;
	}

	public AdmEmpleado addAdmEmpleados1(AdmEmpleado admEmpleados1) {
		getAdmEmpleados1().add(admEmpleados1);
		admEmpleados1.setIdCargoDc(this);

		return admEmpleados1;
	}

	public AdmEmpleado removeAdmEmpleados1(AdmEmpleado admEmpleados1) {
		getAdmEmpleados1().remove(admEmpleados1);
		admEmpleados1.setIdCargoDc(null);

		return admEmpleados1;
	}

	public List<AdmEmpleado> getAdmEmpleados2() {
		return this.admEmpleados2;
	}

	public void setAdmEmpleados2(List<AdmEmpleado> admEmpleados2) {
		this.admEmpleados2 = admEmpleados2;
	}

	public AdmEmpleado addAdmEmpleados2(AdmEmpleado admEmpleados2) {
		getAdmEmpleados2().add(admEmpleados2);
		admEmpleados2.setIdCiudadDc(this);

		return admEmpleados2;
	}

	public AdmEmpleado removeAdmEmpleados2(AdmEmpleado admEmpleados2) {
		getAdmEmpleados2().remove(admEmpleados2);
		admEmpleados2.setIdCiudadDc(null);

		return admEmpleados2;
	}

	public List<AdmEmpleado> getAdmEmpleados3() {
		return this.admEmpleados3;
	}

	public void setAdmEmpleados3(List<AdmEmpleado> admEmpleados3) {
		this.admEmpleados3 = admEmpleados3;
	}

	public AdmEmpleado addAdmEmpleados3(AdmEmpleado admEmpleados3) {
		getAdmEmpleados3().add(admEmpleados3);
		admEmpleados3.setIdClaseEmpleadoDc(this);

		return admEmpleados3;
	}

	public AdmEmpleado removeAdmEmpleados3(AdmEmpleado admEmpleados3) {
		getAdmEmpleados3().remove(admEmpleados3);
		admEmpleados3.setIdClaseEmpleadoDc(null);

		return admEmpleados3;
	}

	public List<AdmEmpleado> getAdmEmpleados4() {
		return this.admEmpleados4;
	}

	public void setAdmEmpleados4(List<AdmEmpleado> admEmpleados4) {
		this.admEmpleados4 = admEmpleados4;
	}

	public AdmEmpleado addAdmEmpleados4(AdmEmpleado admEmpleados4) {
		getAdmEmpleados4().add(admEmpleados4);
		admEmpleados4.setIdDepartamentoDc(this);

		return admEmpleados4;
	}

	public AdmEmpleado removeAdmEmpleados4(AdmEmpleado admEmpleados4) {
		getAdmEmpleados4().remove(admEmpleados4);
		admEmpleados4.setIdDepartamentoDc(null);

		return admEmpleados4;
	}

	public List<AdmEmpleado> getAdmEmpleados5() {
		return this.admEmpleados5;
	}

	public void setAdmEmpleados5(List<AdmEmpleado> admEmpleados5) {
		this.admEmpleados5 = admEmpleados5;
	}

	public AdmEmpleado addAdmEmpleados5(AdmEmpleado admEmpleados5) {
		getAdmEmpleados5().add(admEmpleados5);
		admEmpleados5.setIdEstadoDc(this);

		return admEmpleados5;
	}

	public AdmEmpleado removeAdmEmpleados5(AdmEmpleado admEmpleados5) {
		getAdmEmpleados5().remove(admEmpleados5);
		admEmpleados5.setIdEstadoDc(null);

		return admEmpleados5;
	}

	public List<AdmEmpleado> getAdmEmpleados6() {
		return this.admEmpleados6;
	}

	public void setAdmEmpleados6(List<AdmEmpleado> admEmpleados6) {
		this.admEmpleados6 = admEmpleados6;
	}

	public AdmEmpleado addAdmEmpleados6(AdmEmpleado admEmpleados6) {
		getAdmEmpleados6().add(admEmpleados6);
		admEmpleados6.setIdSexoDc(this);

		return admEmpleados6;
	}

	public AdmEmpleado removeAdmEmpleados6(AdmEmpleado admEmpleados6) {
		getAdmEmpleados6().remove(admEmpleados6);
		admEmpleados6.setIdSexoDc(null);

		return admEmpleados6;
	}

	public List<AdmEmpleado> getAdmEmpleados7() {
		return this.admEmpleados7;
	}

	public void setAdmEmpleados7(List<AdmEmpleado> admEmpleados7) {
		this.admEmpleados7 = admEmpleados7;
	}

	public AdmEmpleado addAdmEmpleados7(AdmEmpleado admEmpleados7) {
		getAdmEmpleados7().add(admEmpleados7);
		admEmpleados7.setIdLineaNegocioDc(this);

		return admEmpleados7;
	}

	public AdmEmpleado removeAdmEmpleados7(AdmEmpleado admEmpleados7) {
		getAdmEmpleados7().remove(admEmpleados7);
		admEmpleados7.setIdLineaNegocioDc(null);

		return admEmpleados7;
	}

	public List<AdmEmpleado> getAdmEmpleados8() {
		return this.admEmpleados8;
	}

	public void setAdmEmpleados8(List<AdmEmpleado> admEmpleados8) {
		this.admEmpleados8 = admEmpleados8;
	}

	public AdmEmpleado addAdmEmpleados8(AdmEmpleado admEmpleados8) {
		getAdmEmpleados8().add(admEmpleados8);
		admEmpleados8.setIdTipoEmpleadoDc(this);

		return admEmpleados8;
	}

	public AdmEmpleado removeAdmEmpleados8(AdmEmpleado admEmpleados8) {
		getAdmEmpleados8().remove(admEmpleados8);
		admEmpleados8.setIdTipoEmpleadoDc(null);

		return admEmpleados8;
	}

	public List<AdmServicio> getAdmServicios1() {
		return this.admServicios1;
	}

	public void setAdmServicios1(List<AdmServicio> admServicios1) {
		this.admServicios1 = admServicios1;
	}

	public AdmServicio addAdmServicios1(AdmServicio admServicios1) {
		getAdmServicios1().add(admServicios1);
		admServicios1.setIdEstadoDc(this);

		return admServicios1;
	}

	public AdmServicio removeAdmServicios1(AdmServicio admServicios1) {
		getAdmServicios1().remove(admServicios1);
		admServicios1.setIdEstadoDc(null);

		return admServicios1;
	}

	public List<AdmServicio> getAdmServicios2() {
		return this.admServicios2;
	}

	public void setAdmServicios2(List<AdmServicio> admServicios2) {
		this.admServicios2 = admServicios2;
	}

	public AdmServicio addAdmServicios2(AdmServicio admServicios2) {
		getAdmServicios2().add(admServicios2);
		admServicios2.setIdLineaNegocio(this);

		return admServicios2;
	}

	public AdmServicio removeAdmServicios2(AdmServicio admServicios2) {
		getAdmServicios2().remove(admServicios2);
		admServicios2.setIdLineaNegocio(null);

		return admServicios2;
	}

	public List<AdmSucursal> getAdmSucursals() {
		return this.admSucursals;
	}

	public void setAdmSucursals(List<AdmSucursal> admSucursals) {
		this.admSucursals = admSucursals;
	}

	public AdmSucursal addAdmSucursal(AdmSucursal admSucursal) {
		getAdmSucursals().add(admSucursal);
		admSucursal.setIdCiudadDc(this);

		return admSucursal;
	}

	public AdmSucursal removeAdmSucursal(AdmSucursal admSucursal) {
		getAdmSucursals().remove(admSucursal);
		admSucursal.setIdCiudadDc(null);

		return admSucursal;
	}

	public List<AdmUsuario> getAdmUsuarios() {
		return this.admUsuarios;
	}

	public void setAdmUsuarios(List<AdmUsuario> admUsuarios) {
		this.admUsuarios = admUsuarios;
	}

	public AdmUsuario addAdmUsuario(AdmUsuario admUsuario) {
		getAdmUsuarios().add(admUsuario);
		admUsuario.setIdEstadoDc(this);

		return admUsuario;
	}

	public AdmUsuario removeAdmUsuario(AdmUsuario admUsuario) {
		getAdmUsuarios().remove(admUsuario);
		admUsuario.setIdEstadoDc(null);

		return admUsuario;
	}

	public List<AdmUsuarioCiudad> getAdmUsuarioCiudads() {
		return this.admUsuarioCiudads;
	}

	public void setAdmUsuarioCiudads(List<AdmUsuarioCiudad> admUsuarioCiudads) {
		this.admUsuarioCiudads = admUsuarioCiudads;
	}

	public AdmUsuarioCiudad addAdmUsuarioCiudad(AdmUsuarioCiudad admUsuarioCiudad) {
		getAdmUsuarioCiudads().add(admUsuarioCiudad);
		admUsuarioCiudad.setIdCiudadDc(this);

		return admUsuarioCiudad;
	}

	public AdmUsuarioCiudad removeAdmUsuarioCiudad(AdmUsuarioCiudad admUsuarioCiudad) {
		getAdmUsuarioCiudads().remove(admUsuarioCiudad);
		admUsuarioCiudad.setIdCiudadDc(null);

		return admUsuarioCiudad;
	}

	public List<OpeDestinoProvisional> getOpeDestinoProvisionals() {
		return this.opeDestinoProvisionals;
	}

	public void setOpeDestinoProvisionals(List<OpeDestinoProvisional> opeDestinoProvisionals) {
		this.opeDestinoProvisionals = opeDestinoProvisionals;
	}

	public OpeDestinoProvisional addOpeDestinoProvisional(OpeDestinoProvisional opeDestinoProvisional) {
		getOpeDestinoProvisionals().add(opeDestinoProvisional);
		opeDestinoProvisional.setIdCiudadDc(this);

		return opeDestinoProvisional;
	}

	public OpeDestinoProvisional removeOpeDestinoProvisional(OpeDestinoProvisional opeDestinoProvisional) {
		getOpeDestinoProvisionals().remove(opeDestinoProvisional);
		opeDestinoProvisional.setIdCiudadDc(null);

		return opeDestinoProvisional;
	}

	public List<OpeResponsable> getOpeResponsables() {
		return this.opeResponsables;
	}

	public void setOpeResponsables(List<OpeResponsable> opeResponsables) {
		this.opeResponsables = opeResponsables;
	}

	public OpeResponsable addOpeResponsable(OpeResponsable opeResponsable) {
		getOpeResponsables().add(opeResponsable);
		opeResponsable.setIdEstadoDc(this);

		return opeResponsable;
	}

	public OpeResponsable removeOpeResponsable(OpeResponsable opeResponsable) {
		getOpeResponsables().remove(opeResponsable);
		opeResponsable.setIdEstadoDc(null);

		return opeResponsable;
	}
/*
	public List<OpeVehiculo> getOpeVehiculos() {
		return this.opeVehiculos;
	}

	public void setOpeVehiculos(List<OpeVehiculo> opeVehiculos) {
		this.opeVehiculos = opeVehiculos;
	}

	public OpeVehiculo addOpeVehiculo(OpeVehiculo opeVehiculo) {
		getOpeVehiculos().add(opeVehiculo);
		opeVehiculo.setIdCiudadDc(this);

		return opeVehiculo;
	}

	public OpeVehiculo removeOpeVehiculo(OpeVehiculo opeVehiculo) {
		getOpeVehiculos().remove(opeVehiculo);
		opeVehiculo.setIdCiudadDc(null);

		return opeVehiculo;
	}*/


}
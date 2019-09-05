package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.operacion.modelo.*;

import java.util.List;


/**
 * The persistent class for the adm_cliente database table.
 * 
 */
@Entity
@Table(name="adm_cliente", schema = "java")
@NamedQuery(name="AdmCliente.findAll", query="SELECT a FROM AdmCliente a")
public class AdmCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cliente")
	private Integer idCliente;

	@Column(name="correo_electronico")
	private String correoElectronico;

	private String direccion;

	@Column(name="nombre_comercial")
	private String nombreComercial;

	@Column(name="razon_social")
	private String razonSocial;

	private String ruc;

	private String telefono;

	@Column(name="url_logo")
	private String urlLogo;

	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idCliente")
	private List<AdmAgencia> admAgencias;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente_padre")
	private AdmCliente idClientePadre;

	//bi-directional many-to-one association to AdmCliente
	@OneToMany(mappedBy="idClientePadre")
	private List<AdmCliente> admClientes;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_actividad_dc")
	private AdmDetalleCatalogo idActividadDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_estado_dc")
	private AdmDetalleCatalogo idEstadoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_prioridad_dc")
	private AdmDetalleCatalogo idPrioridadDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_tipo_cliente_dc")
	private AdmDetalleCatalogo idTipoClienteDc;

	//bi-directional many-to-one association to AdmClienteEmpleado
	@OneToMany(mappedBy="idCliente")
	private List<AdmClienteEmpleado> admClienteEmpleados;

	//bi-directional many-to-one association to OpeParametroCliente
	@OneToMany(mappedBy="idCliente")
	private List<OpeParametroCliente> opeParametroClientes;

	//bi-directional many-to-one association to OpePlanificacion
	@OneToMany(mappedBy="idClienteOrigen")
	private List<OpePlanificacion> opePlanificacions1;
	
	//bi-directional many-to-one association to OpeHojaDetalleAlistamiento
	@OneToMany(mappedBy="idClienteOrigen")
	private List<OpePlanificacion> ClientesOrigen;

	//bi-directional many-to-one association to OpePlanificacion
	@OneToMany(mappedBy="idClienteDestino")
	private List<OpePlanificacion> opePlanificacions2;

	//bi-directional many-to-one association to OpeResponsable
	@OneToMany(mappedBy="idCliente")
	private List<OpeResponsable> opeResponsables;

	//bi-directional many-to-one association to OpeTransaccion
	@OneToMany(mappedBy="idCliente")
	private List<OpeTransaccion> opeTransaccions;

	public AdmCliente() {
	}

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUrlLogo() {
		return this.urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
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

	public AdmCliente addAdmCliente(AdmCliente admCliente) {
		getAdmClientes().add(admCliente);
		admCliente.setIdClientePadre(this);

		return admCliente;
	}

	public AdmCliente removeAdmCliente(AdmCliente admCliente) {
		getAdmClientes().remove(admCliente);
		admCliente.setIdClientePadre(null);

		return admCliente;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public AdmDetalleCatalogo getIdActividadDc() {
		return this.idActividadDc;
	}

	public void setIdActividadDc(AdmDetalleCatalogo idActividadDc) {
		this.idActividadDc = idActividadDc;
	}

	public AdmDetalleCatalogo getIdEstadoDc() {
		return this.idEstadoDc;
	}

	public void setIdEstadoDc(AdmDetalleCatalogo idEstadoDc) {
		this.idEstadoDc = idEstadoDc;
	}

	public AdmDetalleCatalogo getIdPrioridadDc() {
		return this.idPrioridadDc;
	}

	public void setIdPrioridadDc(AdmDetalleCatalogo idPrioridadDc) {
		this.idPrioridadDc = idPrioridadDc;
	}

	public AdmDetalleCatalogo getIdTipoClienteDc() {
		return this.idTipoClienteDc;
	}

	public void setIdTipoClienteDc(AdmDetalleCatalogo idTipoClienteDc) {
		this.idTipoClienteDc = idTipoClienteDc;
	}

	public List<AdmClienteEmpleado> getAdmClienteEmpleados() {
		return this.admClienteEmpleados;
	}

	public void setAdmClienteEmpleados(List<AdmClienteEmpleado> admClienteEmpleados) {
		this.admClienteEmpleados = admClienteEmpleados;
	}

	public AdmClienteEmpleado addAdmClienteEmpleado(AdmClienteEmpleado admClienteEmpleado) {
		getAdmClienteEmpleados().add(admClienteEmpleado);
		admClienteEmpleado.setIdCliente(this);

		return admClienteEmpleado;
	}

	public AdmClienteEmpleado removeAdmClienteEmpleado(AdmClienteEmpleado admClienteEmpleado) {
		getAdmClienteEmpleados().remove(admClienteEmpleado);
		admClienteEmpleado.setIdCliente(null);

		return admClienteEmpleado;
	}

	public List<OpeParametroCliente> getOpeParametroClientes() {
		return this.opeParametroClientes;
	}

	public void setOpeParametroClientes(List<OpeParametroCliente> opeParametroClientes) {
		this.opeParametroClientes = opeParametroClientes;
	}

	public OpeParametroCliente addOpeParametroCliente(OpeParametroCliente opeParametroCliente) {
		getOpeParametroClientes().add(opeParametroCliente);
		opeParametroCliente.setIdCliente(this);

		return opeParametroCliente;
	}

	public OpeParametroCliente removeOpeParametroCliente(OpeParametroCliente opeParametroCliente) {
		getOpeParametroClientes().remove(opeParametroCliente);
		opeParametroCliente.setIdCliente(null);

		return opeParametroCliente;
	}

	public List<OpePlanificacion> getOpePlanificacions1() {
		return this.opePlanificacions1;
	}

	public void setOpePlanificacions1(List<OpePlanificacion> opePlanificacions1) {
		this.opePlanificacions1 = opePlanificacions1;
	}

	public OpePlanificacion addOpePlanificacions1(OpePlanificacion opePlanificacions1) {
		getOpePlanificacions1().add(opePlanificacions1);
		opePlanificacions1.setIdClienteOrigen(this);

		return opePlanificacions1;
	}

	public OpePlanificacion removeOpePlanificacions1(OpePlanificacion opePlanificacions1) {
		getOpePlanificacions1().remove(opePlanificacions1);
		opePlanificacions1.setIdClienteOrigen(null);

		return opePlanificacions1;
	}

	public List<OpePlanificacion> getOpePlanificacions2() {
		return this.opePlanificacions2;
	}

	public void setOpePlanificacions2(List<OpePlanificacion> opePlanificacions2) {
		this.opePlanificacions2 = opePlanificacions2;
	}

	public OpePlanificacion addOpePlanificacions2(OpePlanificacion opePlanificacions2) {
		getOpePlanificacions2().add(opePlanificacions2);
		opePlanificacions2.setIdClienteDestino(this);

		return opePlanificacions2;
	}

	public OpePlanificacion removeOpePlanificacions2(OpePlanificacion opePlanificacions2) {
		getOpePlanificacions2().remove(opePlanificacions2);
		opePlanificacions2.setIdClienteDestino(null);

		return opePlanificacions2;
	}

	public List<OpeResponsable> getOpeResponsables() {
		return this.opeResponsables;
	}

	public void setOpeResponsables(List<OpeResponsable> opeResponsables) {
		this.opeResponsables = opeResponsables;
	}

	public OpeResponsable addOpeResponsable(OpeResponsable opeResponsable) {
		getOpeResponsables().add(opeResponsable);
		opeResponsable.setIdCliente(this);

		return opeResponsable;
	}

	public OpeResponsable removeOpeResponsable(OpeResponsable opeResponsable) {
		getOpeResponsables().remove(opeResponsable);
		opeResponsable.setIdCliente(null);

		return opeResponsable;
	}

	public List<OpeTransaccion> getOpeTransaccions() {
		return this.opeTransaccions;
	}

	public void setOpeTransaccions(List<OpeTransaccion> opeTransaccions) {
		this.opeTransaccions = opeTransaccions;
	}

	public OpeTransaccion addOpeTransaccion(OpeTransaccion opeTransaccion) {
		getOpeTransaccions().add(opeTransaccion);
		opeTransaccion.setIdCliente(this);

		return opeTransaccion;
	}

	public OpeTransaccion removeOpeTransaccion(OpeTransaccion opeTransaccion) {
		getOpeTransaccions().remove(opeTransaccion);
		opeTransaccion.setIdCliente(null);

		return opeTransaccion;
	}

	public List<OpePlanificacion> getClientesOrigen() {
		return ClientesOrigen;
	}

	public void setClientesOrigen(List<OpePlanificacion> clientesOrigen) {
		ClientesOrigen = clientesOrigen;
	}

	
	
}
package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.scl.operacion.modelo.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the adm_empleado database table.
 * 
 */
@Entity
@Table(name="adm_empleado", schema = "java")
@NamedQuery(name="AdmEmpleado.findAll", query="SELECT a FROM AdmEmpleado a")
public class AdmEmpleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_empleado")
	private Integer idEmpleado;

	private String apellidos;

	@Column(name="correo_electronico")
	private String correoElectronico;

	private String direccion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_salida")
	private Date fechaSalida;

	private String identificacion;

	private String nombres;

	private String telefono;

	@Column(name="tipo_sanguineo")
	private String tipoSanguineo;

	@Column(name="url_firma")
	private String urlFirma;

	@Column(name="url_foto")
	private String urlFoto;

	//bi-directional many-to-one association to AdmClienteEmpleado
	@OneToMany(mappedBy="idEmpleado")
	
	private List<AdmClienteEmpleado> admClienteEmpleados;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_cargo_dc")
	private AdmDetalleCatalogo idCargoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_clase_empleado_dc")
	private AdmDetalleCatalogo idClaseEmpleadoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_departamento_dc")
	private AdmDetalleCatalogo idDepartamentoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_estado_dc")
	private AdmDetalleCatalogo idEstadoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_sexo_dc")
	private AdmDetalleCatalogo idSexoDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_linea_negocio_dc")
	private AdmDetalleCatalogo idLineaNegocioDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_tipo_empleado_dc")
	private AdmDetalleCatalogo idTipoEmpleadoDc;

	//bi-directional many-to-one association to AdmSucursal
	@ManyToOne
	@JoinColumn(name="id_sucursal")
	private AdmSucursal idSucursal;

	

	//bi-directional many-to-one association to AdmUsuario
	@OneToMany(mappedBy="idEmpleado")
	private List<AdmUsuario> admUsuarios;

	//bi-directional many-to-one association to OpeDetalleTripulacion
	@OneToMany(mappedBy="idEmpleado")
	private List<OpeDetalleTripulacion> opeDetalleTripulacions;

	public AdmEmpleado() {
	}

	public Integer getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimeinto) {
		this.fechaNacimiento = fechaNacimeinto;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaSalida() {
		return this.fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoSanguineo() {
		return this.tipoSanguineo;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public String getUrlFirma() {
		return this.urlFirma;
	}

	public void setUrlFirma(String urlFirma) {
		this.urlFirma = urlFirma;
	}

	public String getUrlFoto() {
		return this.urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public List<AdmClienteEmpleado> getAdmClienteEmpleados() {
		return this.admClienteEmpleados;
	}

	public void setAdmClienteEmpleados(List<AdmClienteEmpleado> admClienteEmpleados) {
		this.admClienteEmpleados = admClienteEmpleados;
	}

	public AdmClienteEmpleado addAdmClienteEmpleado(AdmClienteEmpleado admClienteEmpleado) {
		getAdmClienteEmpleados().add(admClienteEmpleado);
		admClienteEmpleado.setIdEmpleado(this);

		return admClienteEmpleado;
	}

	public AdmClienteEmpleado removeAdmClienteEmpleado(AdmClienteEmpleado admClienteEmpleado) {
		getAdmClienteEmpleados().remove(admClienteEmpleado);
		admClienteEmpleado.setIdEmpleado(null);

		return admClienteEmpleado;
	}

	public AdmDetalleCatalogo getIdCargoDc() {
		return this.idCargoDc;
	}

	public void setIdCargoDc(AdmDetalleCatalogo idCargoDc) {
		this.idCargoDc = idCargoDc;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public AdmDetalleCatalogo getIdClaseEmpleadoDc() {
		return this.idClaseEmpleadoDc;
	}

	public void setIdClaseEmpleadoDc(AdmDetalleCatalogo idClaseEmpleadoDc) {
		this.idClaseEmpleadoDc = idClaseEmpleadoDc;
	}

	public AdmDetalleCatalogo getIdDepartamentoDc() {
		return this.idDepartamentoDc;
	}

	public void setIdDepartamentoDc(AdmDetalleCatalogo idDepartamentoDc) {
		this.idDepartamentoDc = idDepartamentoDc;
	}

	public AdmDetalleCatalogo getIdEstadoDc() {
		return this.idEstadoDc;
	}

	public void setIdEstadoDc(AdmDetalleCatalogo idEstadoDc) {
		this.idEstadoDc = idEstadoDc;
	}

	public AdmDetalleCatalogo getIdSexoDc() {
		return this.idSexoDc;
	}

	public void setIdSexoDc(AdmDetalleCatalogo idSexoDc) {
		this.idSexoDc = idSexoDc;
	}

	public AdmDetalleCatalogo getIdLineaNegocioDc() {
		return this.idLineaNegocioDc;
	}

	public void setIdLineaNegocioDc(AdmDetalleCatalogo idLineaNegocioDc) {
		this.idLineaNegocioDc = idLineaNegocioDc;
	}

	public AdmDetalleCatalogo getIdTipoEmpleadoDc() {
		return this.idTipoEmpleadoDc;
	}

	public void setIdTipoEmpleadoDc(AdmDetalleCatalogo idTipoEmpleadoDc) {
		this.idTipoEmpleadoDc = idTipoEmpleadoDc;
	}

	public AdmSucursal getIdSucursal() {
		return this.idSucursal;
	}

	public void setIdSucursal(AdmSucursal idSucursal) {
		this.idSucursal = idSucursal;
	}

	

	public List<AdmUsuario> getAdmUsuarios() {
		return this.admUsuarios;
	}

	public void setAdmUsuarios(List<AdmUsuario> admUsuarios) {
		this.admUsuarios = admUsuarios;
	}

	public AdmUsuario addAdmUsuario(AdmUsuario admUsuario) {
		getAdmUsuarios().add(admUsuario);
		admUsuario.setIdEmpleado(this);

		return admUsuario;
	}

	public AdmUsuario removeAdmUsuario(AdmUsuario admUsuario) {
		getAdmUsuarios().remove(admUsuario);
		admUsuario.setIdEmpleado(null);

		return admUsuario;
	}

	public List<OpeDetalleTripulacion> getOpeDetalleTripulacions() {
		return this.opeDetalleTripulacions;
	}

	public void setOpeDetalleTripulacions(List<OpeDetalleTripulacion> opeDetalleTripulacions) {
		this.opeDetalleTripulacions = opeDetalleTripulacions;
	}

	public OpeDetalleTripulacion addOpeDetalleTripulacion(OpeDetalleTripulacion opeDetalleTripulacion) {
		getOpeDetalleTripulacions().add(opeDetalleTripulacion);
		opeDetalleTripulacion.setIdEmpleado(this);

		return opeDetalleTripulacion;
	}

	public OpeDetalleTripulacion removeOpeDetalleTripulacion(OpeDetalleTripulacion opeDetalleTripulacion) {
		getOpeDetalleTripulacions().remove(opeDetalleTripulacion);
		opeDetalleTripulacion.setIdEmpleado(null);

		return opeDetalleTripulacion;
	}

}
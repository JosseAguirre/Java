package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import com.scl.operacion.modelo.*;

/**
 * The persistent class for the adm_agencia database table.
 * 
 */
@Entity
@Table(name="adm_agencia", schema = "java")
@NamedQuery(name="AdmAgencia.findAll", query="SELECT a FROM AdmAgencia a")
public class AdmAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_agencia")
	private Integer idAgencia;

	@Column(name="codigo_barra")
	private String codigoBarra;

	private String direccion;

	private Long latitud;

	private Long longitud;

	private String nombre;

	private String nominativo;

	private String telefono;

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_padre")
	private AdmAgencia idAgenciaPadre;

	//bi-directional many-to-one association to AdmAgencia
	@OneToMany(mappedBy="idAgenciaPadre")
	private List<AdmAgencia> admAgencias;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AdmCliente idCliente;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_estado_dc")
	private AdmDetalleCatalogo idEstadoDc;

	
	

	//bi-directional many-to-one association to OpePlanificacion
	@OneToMany(mappedBy="idAgenciaOrigen")
	private List<OpePlanificacion> opePlanificacions1;

	//bi-directional many-to-one association to OpePlanificacion
	@OneToMany(mappedBy="idAgenciaDestino")
	private List<OpePlanificacion> opePlanificacions2;

	//bi-directional many-to-one association to OpeResponsable
	@OneToMany(mappedBy="idAgencia")
	private List<OpeResponsable> opeResponsables;

	//bi-directional many-to-one association to OpeTransaccion
	@OneToMany(mappedBy="idAgenciaOrigen")
	private List<OpeTransaccion> opeTransaccions1;

	//bi-directional many-to-one association to OpeTransaccion
	@OneToMany(mappedBy="idAgenciaDestino")
	private List<OpeTransaccion> opeTransaccions2;

	public AdmAgencia() {
	}

	public Integer getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getCodigoBarra() {
		return this.codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public Long getLatitud() {
		return latitud;
	}

	public void setLatitud(Long latitud) {
		this.latitud = latitud;
	}

	public Long getLongitud() {
		return longitud;
	}

	public void setLongitud(Long longitud) {
		this.longitud = longitud;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNominativo() {
		return this.nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public AdmAgencia getIdAgenciaPadre() {
		return this.idAgenciaPadre;
	}

	public void setIdAgenciaPadre(AdmAgencia idAgenciaPadre) {
		this.idAgenciaPadre = idAgenciaPadre;
	}

	public List<AdmAgencia> getAdmAgencias() {
		return this.admAgencias;
	}

	public void setAdmAgencias(List<AdmAgencia> admAgencias) {
		this.admAgencias = admAgencias;
	}

	public AdmAgencia addAdmAgencia(AdmAgencia admAgencia) {
		getAdmAgencias().add(admAgencia);
		admAgencia.setIdAgenciaPadre(this);

		return admAgencia;
	}

	public AdmAgencia removeAdmAgencia(AdmAgencia admAgencia) {
		getAdmAgencias().remove(admAgencia);
		admAgencia.setIdAgenciaPadre(null);

		return admAgencia;
	}

	public AdmCliente getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(AdmCliente idCliente) {
		this.idCliente = idCliente;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public AdmDetalleCatalogo getIdEstadoDc() {
		return this.idEstadoDc;
	}

	public void setIdEstadoDc(AdmDetalleCatalogo idEstadoDc) {
		this.idEstadoDc = idEstadoDc;
	}

	

	

	public List<OpePlanificacion> getOpePlanificacions1() {
		return this.opePlanificacions1;
	}

	public void setOpePlanificacions1(List<OpePlanificacion> opePlanificacions1) {
		this.opePlanificacions1 = opePlanificacions1;
	}

	public OpePlanificacion addOpePlanificacions1(OpePlanificacion opePlanificacions1) {
		getOpePlanificacions1().add(opePlanificacions1);
		opePlanificacions1.setIdAgenciaOrigen(this);

		return opePlanificacions1;
	}

	public OpePlanificacion removeOpePlanificacions1(OpePlanificacion opePlanificacions1) {
		getOpePlanificacions1().remove(opePlanificacions1);
		opePlanificacions1.setIdAgenciaOrigen(null);

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
		opePlanificacions2.setIdAgenciaDestino(this);

		return opePlanificacions2;
	}

	public OpePlanificacion removeOpePlanificacions2(OpePlanificacion opePlanificacions2) {
		getOpePlanificacions2().remove(opePlanificacions2);
		opePlanificacions2.setIdAgenciaDestino(null);

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
		opeResponsable.setIdAgencia(this);

		return opeResponsable;
	}

	public OpeResponsable removeOpeResponsable(OpeResponsable opeResponsable) {
		getOpeResponsables().remove(opeResponsable);
		opeResponsable.setIdAgencia(null);

		return opeResponsable;
	}

	public List<OpeTransaccion> getOpeTransaccions1() {
		return this.opeTransaccions1;
	}

	public void setOpeTransaccions1(List<OpeTransaccion> opeTransaccions1) {
		this.opeTransaccions1 = opeTransaccions1;
	}

	public OpeTransaccion addOpeTransaccions1(OpeTransaccion opeTransaccions1) {
		getOpeTransaccions1().add(opeTransaccions1);
		opeTransaccions1.setIdAgenciaOrigen(this);

		return opeTransaccions1;
	}

	public OpeTransaccion removeOpeTransaccions1(OpeTransaccion opeTransaccions1) {
		getOpeTransaccions1().remove(opeTransaccions1);
		opeTransaccions1.setIdAgenciaOrigen(null);

		return opeTransaccions1;
	}

	public List<OpeTransaccion> getOpeTransaccions2() {
		return this.opeTransaccions2;
	}

	public void setOpeTransaccions2(List<OpeTransaccion> opeTransaccions2) {
		this.opeTransaccions2 = opeTransaccions2;
	}

	public OpeTransaccion addOpeTransaccions2(OpeTransaccion opeTransaccions2) {
		getOpeTransaccions2().add(opeTransaccions2);
		opeTransaccions2.setIdAgenciaDestino(this);

		return opeTransaccions2;
	}

	public OpeTransaccion removeOpeTransaccions2(OpeTransaccion opeTransaccions2) {
		getOpeTransaccions2().remove(opeTransaccions2);
		opeTransaccions2.setIdAgenciaDestino(null);

		return opeTransaccions2;
	}

}
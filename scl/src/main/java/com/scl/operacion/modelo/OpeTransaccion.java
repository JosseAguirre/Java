package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_transaccion database table.
 * 
 */
@Entity
@Table(name="ope_transaccion", schema = "java")
@NamedQuery(name="OpeTransaccion.findAll", query="SELECT o FROM OpeTransaccion o")
public class OpeTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_transaccion")
	private Integer idTransaccion;

	private Integer descargado;

	@Column(name="fecha_descargado")
	private Timestamp fechaDescargado;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_sincronizado")
	private Timestamp fechaSincronizado;

	@Column(name="id_ubicacion_actual")
	private Integer idUbicacionActual;

	@Column(name="numero_recibo")
	private String numeroRecibo;
	
	@Column(name="id_rutero")
	private Integer idRutero;

	@Column(name="operacion_cerrada")
	private Integer operacionCerrada;

	private Integer sincronizado;

	@Column(name="total_cheque")
	private float totalCheque;

	@Column(name="total_efectivo")
	private float totalEfectivo;

	@Column(name="total_paquetes")
	private Integer totalPaquetes;

	@Column(name="total_transaccion")
	private float totalTransaccion;
	
	@Column(name="generado_por")
	private String generadoPor;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_operacion")
	private Date fechaOperacion;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="estado_transaccion")
	private Integer estadoTransaccion;
	
	@Column(name="id_responsable_recepcion")
	private Integer idResponsableRecepcion;
	
	@Column(name="id_responsable_envio")
	private Integer idResponsableEnvio;
	
	@Column(name="id_tipo_moneda")
	private Integer idTipoMoneda;
	
	@Column(name="id_equipo_movil")
	private Integer idEquipoMovil;
	
	@Column(name="id_circuito")
	private Integer idCircuito;


	//bi-directional many-to-one association to OpeDetalleTransacion
	@OneToMany(mappedBy="idTransaccion")
	private List<OpeDetalleTransaccion> opeDetalleTransacions;

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_origen")
	private AdmAgencia idAgenciaOrigen;

	//bi-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia_destino")
	private AdmAgencia idAgenciaDestino;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AdmCliente idCliente;
	
	
	

	


	

	//bi-directional many-to-one association to OpeTipoTarifa
	@ManyToOne
	@JoinColumn(name="id_tipo_tarifa")
	private OpeTipoTarifa idTipoTarifa;

	//bi-directional many-to-one association to OpeVehiculo
	@ManyToOne
	@JoinColumn(name="id_vehiculo")
	private OpeVehiculo idVehiculo;

	public OpeTransaccion() {
	}

	public Integer getIdTransaccion() {
		return this.idTransaccion;
	}

	public void setIdTransaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Integer getDescargado() {
		return this.descargado;
	}

	public void setDescargado(Integer descargado) {
		this.descargado = descargado;
	}

	public Timestamp getFechaDescargado() {
		return this.fechaDescargado;
	}

	public void setFechaDescargado(Timestamp fechaDescargado) {
		this.fechaDescargado = fechaDescargado;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaSincronizado() {
		return this.fechaSincronizado;
	}

	public void setFechaSincronizado(Timestamp fechaSincronizado) {
		this.fechaSincronizado = fechaSincronizado;
	}

	public Integer getIdUbicacionActual() {
		return this.idUbicacionActual;
	}

	public void setIdUbicacionActual(Integer idUbicacionActual) {
		this.idUbicacionActual = idUbicacionActual;
	}

	public String getNumeroRecibo() {
		return this.numeroRecibo;
	}

	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	public Integer getOperacionCerrada() {
		return this.operacionCerrada;
	}

	public void setOperacionCerrada(Integer operacionCerrada) {
		this.operacionCerrada = operacionCerrada;
	}

	public Integer getSincronizado() {
		return this.sincronizado;
	}

	public void setSincronizado(Integer sincronizado) {
		this.sincronizado = sincronizado;
	}

	public float getTotalCheque() {
		return this.totalCheque;
	}

	public void setTotalCheque(float totalCheque) {
		this.totalCheque = totalCheque;
	}

	public float getTotalEfectivo() {
		return this.totalEfectivo;
	}

	public void setTotalEfectivo(float totalEfectivo) {
		this.totalEfectivo = totalEfectivo;
	}

	public Integer getTotalPaquetes() {
		return this.totalPaquetes;
	}

	public void setTotalPaquetes(Integer totalPaquetes) {
		this.totalPaquetes = totalPaquetes;
	}

	public float getTotalTransaccion() {
		return this.totalTransaccion;
	}

	public void setTotalTransaccion(float totalTransaccion) {
		this.totalTransaccion = totalTransaccion;
	}

	public List<OpeDetalleTransaccion> getOpeDetalleTransacions() {
		return this.opeDetalleTransacions;
	}

	public void setOpeDetalleTransacions(List<OpeDetalleTransaccion> opeDetalleTransacions) {
		this.opeDetalleTransacions = opeDetalleTransacions;
	}

	public OpeDetalleTransaccion addOpeDetalleTransacion(OpeDetalleTransaccion opeDetalleTransacion) {
		getOpeDetalleTransacions().add(opeDetalleTransacion);
		opeDetalleTransacion.setIdTransaccion(this);

		return opeDetalleTransacion;
	}

	public OpeDetalleTransaccion removeOpeDetalleTransacion(OpeDetalleTransaccion opeDetalleTransacion) {
		getOpeDetalleTransacions().remove(opeDetalleTransacion);
		opeDetalleTransacion.setIdTransaccion(null);

		return opeDetalleTransacion;
	}

	public AdmAgencia getIdAgenciaOrigen() {
		return this.idAgenciaOrigen;
	}

	public void setIdAgenciaOrigen(AdmAgencia idAgenciaOrigen) {
		this.idAgenciaOrigen = idAgenciaOrigen;
	}

	public AdmAgencia getIdAgenciaDestino() {
		return this.idAgenciaDestino;
	}

	public void setIdAgenciaDestino(AdmAgencia idAgenciaDestino) {
		this.idAgenciaDestino = idAgenciaDestino;
	}

	public AdmCliente getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(AdmCliente idCliente) {
		this.idCliente = idCliente;
	}



	

	

	public Integer getIdRutero() {
		return idRutero;
	}

	public void setIdRutero(Integer idRutero) {
		this.idRutero = idRutero;
	}

	public OpeTipoTarifa getIdTipoTarifa() {
		return this.idTipoTarifa;
	}

	public void setIdTipoTarifa(OpeTipoTarifa idTipoTarifa) {
		this.idTipoTarifa = idTipoTarifa;
	}

	public OpeVehiculo getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(OpeVehiculo idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getGeneradoPor() {
		return generadoPor;
	}

	public void setGeneradoPor(String generadoPor) {
		this.generadoPor = generadoPor;
	}



	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getEstadoTransaccion() {
		return estadoTransaccion;
	}

	public void setEstadoTransaccion(Integer estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public Integer getIdResponsableRecepcion() {
		return idResponsableRecepcion;
	}

	public void setIdResponsableRecepcion(Integer idResponsableRecepcion) {
		this.idResponsableRecepcion = idResponsableRecepcion;
	}

	public Integer getIdResponsableEnvio() {
		return idResponsableEnvio;
	}

	public void setIdResponsableEnvio(Integer idResponsablEnvio) {
		this.idResponsableEnvio = idResponsablEnvio;
	}

	public Integer getIdTipoMoneda() {
		return idTipoMoneda;
	}

	public void setIdTipoMoneda(Integer idTipoMoneda) {
		this.idTipoMoneda = idTipoMoneda;
	}

	public Integer getIdEquipoMovil() {
		return idEquipoMovil;
	}

	public void setIdEquipoMovil(Integer idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}

	public Integer getIdCircuito() {
		return idCircuito;
	}

	public void setIdCircuito(Integer idCircuito) {
		this.idCircuito = idCircuito;
	}


}
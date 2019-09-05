package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.administracion.modelo.AdmEmpleado;

import java.sql.Timestamp;



@Entity
@Table(name="ope_validacion_detalle" , schema = "java")
@NamedQuery(name="OpeValidacionDetalle.findAll", query="SELECT o FROM OpeValidacionDetalle o")
public class OpeValidacionDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_validacion_detalle")
	private Integer idValidacionDetalle;

	@Column(name="fecha_validacion_ingreso")
	private Timestamp fechaValidacionIngreso;

	@Column(name="fecha_validacion_salida")
	private Timestamp fechaValidacionSalida;

	//bi-directional many-to-one association to AdmEmpleado
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	//bi-directional many-to-one association to OpeDetalleTransaccion
	@ManyToOne
	@JoinColumn(name="id_detalle_transaccion")
	private OpeDetalleTransaccion idDetalleTransaccion;
	
	@Column(name="observacion")
	private String observacion;
	
	

	public OpeValidacionDetalle() {
	}

	public Integer getIdValidacionDetalle() {
		return this.idValidacionDetalle;
	}

	public void setIdValidacionDetalle(Integer idValidacionDetalle) {
		this.idValidacionDetalle = idValidacionDetalle;
	}

	public Timestamp getFechaValidacionIngreso() {
		return this.fechaValidacionIngreso;
	}

	public void setFechaValidacionIngreso(Timestamp fechaValidacionIngreso) {
		this.fechaValidacionIngreso = fechaValidacionIngreso;
	}

	public Timestamp getFechaValidacionSalida() {
		return this.fechaValidacionSalida;
	}

	public void setFechaValidacionSalida(Timestamp fechaValidacionSalida) {
		this.fechaValidacionSalida = fechaValidacionSalida;
	}

	

	public AdmEmpleado getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public OpeDetalleTransaccion getIdDetalleTransaccion() {
		return idDetalleTransaccion;
	}

	public void setIdDetalleTransaccion(OpeDetalleTransaccion idDetalleTransaccion) {
		this.idDetalleTransaccion = idDetalleTransaccion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	

}
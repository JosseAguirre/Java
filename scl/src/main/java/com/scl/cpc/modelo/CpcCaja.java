package com.scl.cpc.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.scl.administracion.modelo.AdmEmpleado;

import java.util.Date;


/**
 * The persistent class for the cpc_caja database table.
 * 
 */
@Entity
@Table(name="cpc_caja", schema = "java")
@NamedQuery(name="CpcCaja.findAll", query="SELECT c FROM CpcCaja c")
public class CpcCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_caja")
	private Integer idCaja;
	
	@Column(name="cubiculo")
	private Integer cubiculo;
	
	@Column(name="tiempo")
	private Integer tiempo;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	

	public CpcCaja() {
	}

	public Integer getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	public Integer getCubiculo() {
		return this.cubiculo;
	}

	public void setCubiculo(Integer cubiculo) {
		this.cubiculo = cubiculo;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	

	public Integer getTiempo() {
		return tiempo;
	}

	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}

	public AdmEmpleado getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	
	
	

}
package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_empleado_agencia database table.
 * 
 */
@Entity
@Table(name="fis_empleado_agencia", schema = "bivi")
@NamedQuery(name="FisEmpleadoAgencia.findAll", query="SELECT f FROM FisEmpleadoAgencia f")
public class FisEmpleadoAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_empleado_agencia")
	private Integer idEmpleadoAgencia;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	//uni-directional many-to-one association to AdmEmpleado
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	public FisEmpleadoAgencia() {
	}

	public Integer getIdEmpleadoAgencia() {
		return this.idEmpleadoAgencia;
	}

	public void setIdEmpleadoAgencia(Integer idEmpleadoAgencia) {
		this.idEmpleadoAgencia = idEmpleadoAgencia;
	}

	public AdmAgencia getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public AdmEmpleado getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

}
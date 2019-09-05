package com.scl.administracion.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_cliente_empleado database table.
 * 
 */
@Entity
@Table(name="adm_cliente_empleado", schema = "java")
@NamedQuery(name="AdmClienteEmpleado.findAll", query="SELECT a FROM AdmClienteEmpleado a")
public class AdmClienteEmpleado implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_cliente_empleado")
	private Integer idClienteEmpleado;

	//bi-directional many-to-one association to AdmCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AdmCliente idCliente;

	//bi-directional many-to-one association to AdmEmpleado
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	public AdmClienteEmpleado() {
	}

	public Integer getIdClienteEmpleado() {
		return this.idClienteEmpleado;
	}

	public void setIdClienteEmpleado(Integer idClienteEmpleado) {
		this.idClienteEmpleado = idClienteEmpleado;
	}

	public AdmCliente getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(AdmCliente idCliente) {
		this.idCliente = idCliente;
	}

	public AdmEmpleado getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

}
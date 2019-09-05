package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_detalle_tripulacion database table.
 * 
 */
@Entity
@Table(name="ope_detalle_tripulacion", schema = "java")
@NamedQuery(name="OpeDetalleTripulacion.findAll", query="SELECT o FROM OpeDetalleTripulacion o")
public class OpeDetalleTripulacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_tripulacion")
	private Integer idDetalleTripulacion;

	//bi-directional many-to-one association to AdmEmpleado
	@ManyToOne
	@JoinColumn(name="id_empleado")
	private AdmEmpleado idEmpleado;

	//bi-directional many-to-one association to OpeTripulacion
	@ManyToOne
	@JoinColumn(name="id_tripulacion")
	private OpeTripulacion idTripulacion;

	public OpeDetalleTripulacion() {
	}

	public Integer getIdDetalleTripulacion() {
		return this.idDetalleTripulacion;
	}

	public void setIdDetalleTripulacion(Integer idDetalleTripulacion) {
		this.idDetalleTripulacion = idDetalleTripulacion;
	}

	public AdmEmpleado getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(AdmEmpleado idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public OpeTripulacion getIdTripulacion() {
		return this.idTripulacion;
	}

	public void setIdTripulacion(OpeTripulacion idTripulacion) {
		this.idTripulacion = idTripulacion;
	}

}
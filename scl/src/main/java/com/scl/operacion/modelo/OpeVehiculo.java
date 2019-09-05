package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_vehiculo database table.
 * 
 */
@Entity
@Table(name="ope_vehiculo", schema = "java")
@NamedQuery(name="OpeVehiculo.findAll", query="SELECT o FROM OpeVehiculo o")
public class OpeVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_vehiculo")
	private Integer idVehiculo;

	@Column(name="cod_vehiculo")
	private Integer codVehiculo;

	private String descripcion;

	private String placa;

	//bi-directional many-to-one association to OpeTransaccion
	@OneToMany(mappedBy="idVehiculo")
	private List<OpeTransaccion> opeTransaccions;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;
	
	//bi-directional many-to-one association to OpeCatgegoriaVehiculo
		@ManyToOne
		@JoinColumn(name="id_categoria_vehiculo")
		private OpeCategoriaVehiculo idCategoriaVehiculo;
		

	public OpeVehiculo() {
	}

	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Integer getCodVehiculo() {
		return this.codVehiculo;
	}

	public void setCodVehiculo(Integer codVehiculo) {
		this.codVehiculo = codVehiculo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<OpeTransaccion> getOpeTransaccions() {
		return this.opeTransaccions;
	}

	public void setOpeTransaccions(List<OpeTransaccion> opeTransaccions) {
		this.opeTransaccions = opeTransaccions;
	}

	public OpeTransaccion addOpeTransaccion(OpeTransaccion opeTransaccion) {
		getOpeTransaccions().add(opeTransaccion);
		opeTransaccion.setIdVehiculo(this);

		return opeTransaccion;
	}

	public OpeTransaccion removeOpeTransaccion(OpeTransaccion opeTransaccion) {
		getOpeTransaccions().remove(opeTransaccion);
		opeTransaccion.setIdVehiculo(null);

		return opeTransaccion;
	}

	public AdmDetalleCatalogo getIdCiudadDc() {
		return this.idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public OpeCategoriaVehiculo getIdCategoriaVehiculo() {
		return idCategoriaVehiculo;
	}

	public void setIdCategoriaVehiculo(OpeCategoriaVehiculo idCategoriaVehiculo) {
		this.idCategoriaVehiculo = idCategoriaVehiculo;
	}
	
	

}
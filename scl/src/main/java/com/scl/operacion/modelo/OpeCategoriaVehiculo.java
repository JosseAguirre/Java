package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ope_categoria_vehiculo database table.
 * 
 */
@Entity
@Table(name="ope_categoria_vehiculo", schema = "java")
@NamedQuery(name="OpeCategoriaVehiculo.findAll", query="SELECT o FROM OpeCategoriaVehiculo o")
public class OpeCategoriaVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_categoria_vehiculo")
	private Integer idCategoriaVehiculo;

	private String nombre;

	//bi-directional many-to-one association to OpeSeguroVehiculo
	@OneToMany(mappedBy="idCategoriaVehiculo")
	private List<OpeSeguroVehiculo> opeSeguroVehiculos;

	public OpeCategoriaVehiculo() {
	}

	public Integer getIdCategoriaVehiculo() {
		return this.idCategoriaVehiculo;
	}

	public void setIdCategoriaVehiculo(Integer idCategoriaVehiculo) {
		this.idCategoriaVehiculo = idCategoriaVehiculo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<OpeSeguroVehiculo> getOpeSeguroVehiculos() {
		return this.opeSeguroVehiculos;
	}

	public void setOpeSeguroVehiculos(List<OpeSeguroVehiculo> opeSeguroVehiculos) {
		this.opeSeguroVehiculos = opeSeguroVehiculos;
	}

	public OpeSeguroVehiculo addOpeSeguroVehiculo(OpeSeguroVehiculo opeSeguroVehiculo) {
		getOpeSeguroVehiculos().add(opeSeguroVehiculo);
		opeSeguroVehiculo.setIdCategoriaVehiculo(this);

		return opeSeguroVehiculo;
	}

	public OpeSeguroVehiculo removeOpeSeguroVehiculo(OpeSeguroVehiculo opeSeguroVehiculo) {
		getOpeSeguroVehiculos().remove(opeSeguroVehiculo);
		opeSeguroVehiculo.setIdCategoriaVehiculo(null);

		return opeSeguroVehiculo;
	}

}
package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ope_seguro_vehiculo database table.
 * 
 */
@Entity
@Table(name="ope_seguro_vehiculo", schema = "java")
@NamedQuery(name="OpeSeguroVehiculo.findAll", query="SELECT o FROM OpeSeguroVehiculo o")
public class OpeSeguroVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_seguro_vehiculo")
	private Integer idSeguroVehiculo;

	@Column(name="cantidad_tripulantes")
	private Integer cantidadTripulantes;

	@Column(name="monto_maximo")
	private float montoMaximo;

	@Column(name="monto_minimo")
	private float montoMinimo;

	private String nombre;

	//bi-directional many-to-one association to OpeCategoriaVehiculo
	@ManyToOne
	@JoinColumn(name="id_categoria_vehiculo")
	private OpeCategoriaVehiculo idCategoriaVehiculo;

	public OpeSeguroVehiculo() {
	}

	public Integer getIdSeguroVehiculo() {
		return this.idSeguroVehiculo;
	}

	public void setIdSeguroVehiculo(Integer idSeguroVehiculo) {
		this.idSeguroVehiculo = idSeguroVehiculo;
	}

	public Integer getCantidadTripulantes() {
		return this.cantidadTripulantes;
	}

	public void setCantidadTripulantes(Integer cantidadTripulantes) {
		this.cantidadTripulantes = cantidadTripulantes;
	}

	public float getMontoMaximo() {
		return this.montoMaximo;
	}

	public void setMontoMaximo(float montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public float getMontoMinimo() {
		return this.montoMinimo;
	}

	public void setMontoMinimo(float montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public OpeCategoriaVehiculo getIdCategoriaVehiculo() {
		return this.idCategoriaVehiculo;
	}

	public void setIdCategoriaVehiculo(OpeCategoriaVehiculo idCategoriaVehiculo) {
		this.idCategoriaVehiculo = idCategoriaVehiculo;
	}

}
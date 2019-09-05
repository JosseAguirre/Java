package com.scl.operacion.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.scl.administracion.modelo.AdmDetalleCatalogo;




/**
 * The persistent class for the ope_circuito database table.
 * 
 */
@Entity
@Table(name="ope_circuito" , schema = "java")
@NamedQuery(name="OpeCircuito.findAll", query="SELECT o FROM OpeCircuito o")
public class OpeCircuito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_circuito")
	private Integer idCircuito;

	@Column(name="dia_asociado")
	private String diaAsociado;

	private String nombre;

	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_ciudad_dc")
	private AdmDetalleCatalogo idCiudadDc;
	
	//bi-directional many-to-one association to OpeHojaAlistamiento
		@OneToMany(mappedBy="idCircuito")
		private List<OpeHojaAlistamiento> opeHojaAlistamientos;
		

	public OpeCircuito() {
	}

	public Integer getIdCircuito() {
		return this.idCircuito;
	}

	public void setIdCircuito(Integer idCircuito) {
		this.idCircuito = idCircuito;
	}

	public String getDiaAsociado() {
		return this.diaAsociado;
	}

	public void setDiaAsociado(String diaAsociado) {
		this.diaAsociado = diaAsociado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public AdmDetalleCatalogo getIdCiudadDc() {
		return idCiudadDc;
	}

	public void setIdCiudadDc(AdmDetalleCatalogo idCiudadDc) {
		this.idCiudadDc = idCiudadDc;
	}

	public List<OpeHojaAlistamiento> getOpeHojaAlistamientos() {
		return opeHojaAlistamientos;
	}

	public void setOpeHojaAlistamientos(List<OpeHojaAlistamiento> opeHojaAlistamientos) {
		this.opeHojaAlistamientos = opeHojaAlistamientos;
	}

}
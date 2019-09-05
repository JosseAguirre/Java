package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fis_detalle_bitacora database table.
 * 
 */
@Entity
@Table(name="fis_detalle_bitacora", schema = "bivi")
@NamedQuery(name="FisDetalleBitacora.findAll", query="SELECT f FROM FisDetalleBitacora f")
public class FisDetalleBitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_bitacora")
	private Integer idDetalleBitacora;

	private String apellidos;

	private String identificacion;

	private String nombres;

	private String observaciones;

	private String oficina;

	@Column(name="persona_visitada")
	private String personaVisitada;

	private String placa;

	private Integer seguimiento;
	
	@Column(name="tipo_ingreso")
	private String tipoIngreso;
	
	@Column(name="ingreso_por_parqueadero")
	private String ingresoPorParqueadero;
	

	

	@Column(name="url_imagen")
	private String urlImagen;

	//uni-directional many-to-one association to AdmPuesto
	@ManyToOne
	@JoinColumn(name="id_puesto")
	private AdmPuesto idPuesto;

	public FisDetalleBitacora() {
	}

	public Integer getIdDetalleBitacora() {
		return this.idDetalleBitacora;
	}

	public void setIdDetalleBitacora(Integer idDetalleBitacora) {
		this.idDetalleBitacora = idDetalleBitacora;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getOficina() {
		return this.oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getPersonaVisitada() {
		return this.personaVisitada;
	}

	public void setPersonaVisitada(String personaVisitada) {
		this.personaVisitada = personaVisitada;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getSeguimiento() {
		return this.seguimiento;
	}

	public void setSeguimiento(Integer seguimiento) {
		this.seguimiento = seguimiento;
	}



	public String getUrlImagen() {
		return this.urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public AdmPuesto getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(AdmPuesto idPuesto) {
		this.idPuesto = idPuesto;
	}

	public String getTipoIngreso() {
		return tipoIngreso;
	}

	public void setTipoIngreso(String tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}

	public String getIngresoPorParqueadero() {
		return ingresoPorParqueadero;
	}

	public void setIngresoPorParqueadero(String ingresoPorParqueadero) {
		this.ingresoPorParqueadero = ingresoPorParqueadero;
	}
	
	


}
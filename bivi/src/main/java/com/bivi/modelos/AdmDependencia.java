package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_dependencia database table.
 * 
 */
@Entity
@Table(name="adm_dependencia", schema = "bivi")
@NamedQuery(name="AdmDependencia.findAll", query="SELECT a FROM AdmDependencia a")
public class AdmDependencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dependencia")
	private Integer idDependencia;

	@Column(name="correo_electronico")
	private String correoElectronico;

	private String descripcion;

	@Column(name="id_tipo_detalle_catalogo")
	private Integer idTipoDetalleCatalogo;

	private String nombre;

	@Column(name="parqueadero_asignado")
	private Integer parqueaderoAsignado;

	@Column(name="responsable_apellido")
	private String responsableApellido;

	@Column(name="responsable_nombre")
	private String responsableNombre;

	@Column(name="telefono_fijo")
	private String telefonoFijo;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	public AdmDependencia() {
	}

	public Integer getIdDependencia() {
		return this.idDependencia;
	}

	public void setIdDependencia(Integer idDependencia) {
		this.idDependencia = idDependencia;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdTipoDetalleCatalogo() {
		return this.idTipoDetalleCatalogo;
	}

	public void setIdTipoDetalleCatalogo(Integer idTipoDetalleCatalogo) {
		this.idTipoDetalleCatalogo = idTipoDetalleCatalogo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getParqueaderoAsignado() {
		return this.parqueaderoAsignado;
	}

	public void setParqueaderoAsignado(Integer parqueaderoAsignado) {
		this.parqueaderoAsignado = parqueaderoAsignado;
	}

	public String getResponsableApellido() {
		return this.responsableApellido;
	}

	public void setResponsableApellido(String responsableApellido) {
		this.responsableApellido = responsableApellido;
	}

	public String getResponsableNombre() {
		return this.responsableNombre;
	}

	public void setResponsableNombre(String responsableNombre) {
		this.responsableNombre = responsableNombre;
	}

	public String getTelefonoFijo() {
		return this.telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public AdmAgencia getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

}
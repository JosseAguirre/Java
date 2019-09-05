package com.bivi.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the adm_puestos database table.
 * 
 */
@Entity
@Table(name="adm_puestos", schema = "bivi")
@NamedQuery(name="AdmPuesto.findAll", query="SELECT a FROM AdmPuesto a")
public class AdmPuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_puesto")
	private Integer idPuesto;

	@Column(name="codigo_barras")
	private String codigoBarras;
	
	//bi-directional many-to-one association to AdmDetalleCatalogo
	@ManyToOne
	@JoinColumn(name="id_tipo_puesto_catalogo")
	private AdmDetalleCatalogo idTipoPuestoCatalogo;

	@Column(name="nombre_puesto")
	private String nombrePuesto;
	
	@Column(name="numero_guardias")
	private Integer numeroGuardias;

	private String ubicacion;

	//uni-directional many-to-one association to AdmAgencia
	@ManyToOne
	@JoinColumn(name="id_agencia")
	private AdmAgencia idAgencia;

	public AdmPuesto() {
	}

	public Integer getIdPuesto() {
		return this.idPuesto;
	}

	public void setIdPuesto(Integer idPuesto) {
		this.idPuesto = idPuesto;
	}

	public String getCodigoBarras() {
		return this.codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	
	public AdmDetalleCatalogo getIdTipoPuestoCatalogo() {
		return this.idTipoPuestoCatalogo;
	}

	public void setIdTipoPuestoCatalogo(AdmDetalleCatalogo idTipoPuestoCatalogo) {
		this.idTipoPuestoCatalogo = idTipoPuestoCatalogo;
	}

	public String getNombrePuesto() {
		return this.nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}
	
	public Integer getNumeroGuardias() {
		return this.numeroGuardias;
	}

	public void setNumeroGuardias(Integer numeroGuardias) {
		this.numeroGuardias = numeroGuardias;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public AdmAgencia getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(AdmAgencia idAgencia) {
		this.idAgencia = idAgencia;
	}

}
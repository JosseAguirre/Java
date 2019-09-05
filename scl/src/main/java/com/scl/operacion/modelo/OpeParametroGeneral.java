package com.scl.operacion.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import com.scl.administracion.modelo.*;

/**
 * The persistent class for the ope_parametro_cliente database table.
 * 
 */
@Entity
@Table(name="ope_parametro_general", schema = "java")
@NamedQuery(name="OpeParametroGeneral.findAll", query="SELECT o FROM OpeParametroGeneral o")
public class OpeParametroGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_parametro_general")
	private Integer idParametroGeneral;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="valor")
	private String valor;
	
	@Column(name="prefijo")
	private String prefijo;

	public Integer getIdParametroGeneral() {
		return idParametroGeneral;
	}

	public void setIdParametroGeneral(Integer idParametroGeneral) {
		this.idParametroGeneral = idParametroGeneral;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	
	
	

	
	
}
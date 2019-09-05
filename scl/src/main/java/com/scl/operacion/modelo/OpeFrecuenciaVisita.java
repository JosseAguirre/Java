package com.scl.operacion.modelo;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ope_frecuencia_visita database table.
 * 
 */
@Entity
@Table(name="ope_frecuencia_visita", schema = "java")
@NamedQuery(name="OpeFrecuenciaVisita.findAll", query="SELECT o FROM OpeFrecuenciaVisita o")

@SqlResultSetMapping(
        name = "BookAuthorMapping",
        entities = {
            @EntityResult(
                    entityClass = OpeFrecuenciaVisita.class,
                    fields = {
                        @FieldResult(name = "idFrecuenciaVisista", column = "id_frecuencia_visita"),
                        @FieldResult(name = "horaDesde", column = "hora_desde"),
                        @FieldResult(name = "horaHasta", column = "hora_hasta"),
                        @FieldResult(name = "nombreDia", column = "nombre_dia"),
                        @FieldResult(name = "idHojaAlistamiento", column = "id_hoja_alistamiento")}),
            @EntityResult(
                    entityClass = OpeHojaAlistamiento.class,
                    fields = {
                        @FieldResult(name = "idHojaAlistamiento", column = "id_hoja_alistamiento"),
                        @FieldResult(name = "firstName", column = "firstName"),
                        @FieldResult(name = "lastName", column = "lastName"),
                        @FieldResult(name = "version", column = "authorVersion")})})


public class OpeFrecuenciaVisita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_frecuencia_visita")
	private Integer idFrecuenciaVisita;

	@Column(name="hora_desde")
	private String horaDesde;

	@Column(name="hora_hasta")
	private String horaHasta;

	@Column(name="nombre_dia")
	private String nombreDia;

	//uni-directional many-to-one association to OpeHojaAlistamiento
	@ManyToOne
	@JoinColumn(name="id_hoja_alistamiento")
	private OpeHojaAlistamiento idHojaAlistamiento;

	public OpeFrecuenciaVisita() {
	}

	public Integer getIdFrecuenciaVisita() {
		return this.idFrecuenciaVisita;
	}

	public void setIdFrecuenciaVisita(Integer idFrecuenciaVisita) {
		this.idFrecuenciaVisita = idFrecuenciaVisita;
	}

	public String getHoraDesde() {
		return this.horaDesde;
	}

	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}

	public String getHoraHasta() {
		return this.horaHasta;
	}

	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}

	public String getNombreDia() {
		return this.nombreDia;
	}

	public void setNombreDia(String nombreDia) {
		this.nombreDia = nombreDia;
	}

	public OpeHojaAlistamiento getIdHojaAlistamiento() {
		return this.idHojaAlistamiento;
	}

	public void setIdHojaAlistamiento(OpeHojaAlistamiento idHojaAlistamiento) {
		this.idHojaAlistamiento = idHojaAlistamiento;
	}
	
}


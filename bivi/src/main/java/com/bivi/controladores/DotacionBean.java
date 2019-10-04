package com.bivi.controladores;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.bivi.modelos.*;

import com.bivi.servicios.*;


@ManagedBean
@ViewScoped
public class DotacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FisDotacion dotacion;
	private FisDotacion dotacionSeleccionada;
	private List<FisDotacion> listaDotacion;
	private List<FisDotacion> dotacionFiltrada;
	
	
	//Variable de tipo AdmPuesto de las listas para la busqueda de los selectOneMenu
  	private AdmPuesto puesto;
  	private List<AdmPuesto> listaPuesto;
  	
  	//establesco conección a los servicios por medio de los ejb
 	@EJB
 	private ServicioFisDotacion servicioFisDotacion ;
 	@EJB
 	private ServicioAdmPuestos servicioAdmPuesto;	
 	
 	//Variables que van a ser usadas
	private int idDotacion;
	private int idPuesto;
	private boolean bandera;
	
	
	@PostConstruct
	public void init() {
		cancelar();
		consultaDotacion();
	}

	public void cancelar() {
		dotacion = new FisDotacion();
    	bandera = false;
    	idDotacion = -1;
    	idPuesto = -1;
	}	
	
	public void consultaDotacion() {	
		listaDotacion = new ArrayList<>();
		listaDotacion = servicioFisDotacion.findAll();
	}
	
	//Metodo usado para el contenido de los selectOneMenu
	public void consultaListaCombos() {	
		listaPuesto = new ArrayList<>();
		listaPuesto = servicioAdmPuesto.findAll();
	}
	
	//Metodo para guardar una nueva dotacion
	public void guardar() {
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		dotacion.setIdDotacion((int)servicioFisDotacion.getPK()) ;
		dotacion.setIdPuesto(idPuesto);
		
		servicioFisDotacion.create(dotacion);
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaDotacion();
		cancelar();
	}
	
	//Metodo para actualizar una dotacion
	public void actualizar() {
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		
		this.dotacion.setIdPuesto(idPuesto);
		
		servicioFisDotacion.update(dotacion);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaDotacion();
		cancelar();
	}
	
	//Metodo para eliminar una dotacion
	public void eliminar() {
		if(dotacionSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			dotacion = dotacionSeleccionada;
			servicioFisDotacion.delete(dotacion);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaDotacion();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(dotacionSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			consultaListaCombos();
			bandera = true;
			dotacion = dotacionSeleccionada;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosDotacion').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		dotacion = new FisDotacion();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosDotacion').show();");
	}
	
	@SuppressWarnings("deprecation")
	public void resetarFormulario() {
		RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	public void persistir() {
	    if (bandera == true) {
	        actualizar();
	    } else {
	        guardar();
	    }
	}
	
	
	public FisDotacion getDotacion() {
		return dotacion;
	}
	public void setDotacion(FisDotacion dotacion) {
		this.dotacion = dotacion;
	}
	public FisDotacion getDotacionSeleccionada() {
		return dotacionSeleccionada;
	}
	public void setDotacionSeleccionada(FisDotacion dotacionSeleccionada) {
		this.dotacionSeleccionada = dotacionSeleccionada;
	}
	public List<FisDotacion> getListaDotacion() {
		return listaDotacion;
	}
	public void setListaDotacion(List<FisDotacion> listaDotacion) {
		this.listaDotacion = listaDotacion;
	}
	public List<FisDotacion> getDotacionFiltrada() {
		return dotacionFiltrada;
	}
	public void setDotacionFiltrada(List<FisDotacion> dotacionFiltrada) {
		this.dotacionFiltrada = dotacionFiltrada;
	}
	public AdmPuesto getPuesto() {
		return puesto;
	}
	public void setPuesto(AdmPuesto puesto) {
		this.puesto = puesto;
	}
	public List<AdmPuesto> getListaPuesto() {
		return listaPuesto;
	}
	public void setListaPuesto(List<AdmPuesto> listaPuesto) {
		this.listaPuesto = listaPuesto;
	}
	public int getIdDotacion() {
		return idDotacion;
	}
	public void setIdDotacion(int idDotacion) {
		this.idDotacion = idDotacion;
	}
	public int getIdPuesto() {
		return idPuesto;
	}
	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}
	
	
}

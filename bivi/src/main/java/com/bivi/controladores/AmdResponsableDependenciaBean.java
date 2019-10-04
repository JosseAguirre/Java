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

import com.bivi.modelos.AdmResponsablesDependencia;
import com.bivi.modelos.AdmDependencia;
import com.bivi.servicios.ServicioAmdResponsableDependencia;
import com.bivi.servicios.ServicioAdmDependencia;

@ManagedBean
@ViewScoped
public class AmdResponsableDependenciaBean implements  Serializable {

	private static final long serialVersionUID = 1L;
	private AdmResponsablesDependencia responsableDependencia;
	private AdmResponsablesDependencia responsableDependenciaSeleccionada;
	private List<AdmResponsablesDependencia> listaResponsableDependencia;
	private List<AdmResponsablesDependencia> responsableDependenciaFiltrada;
	
	//Variable de tipo AdmDependencia de las listas para la busqueda de los selectOneMenu
	private List<AdmDependencia> listaDependencia;
	private AdmDependencia dependencia;
	
	//Variables que van a ser usadas
	private int idResponsableDependencia;
	private int idDependencia;
	private boolean bandera;
	
	//Variables necesarias para la coneccion con los servicios
	@EJB
	private ServicioAmdResponsableDependencia servicioAmdResponsableDependencia;
	@EJB
	private ServicioAdmDependencia servicioAdmDependencia;
	
	@PostConstruct
	public void init() {
		consultaListaResponsableDependecia();
		cancelar();	
	}
	
	public void cancelar() {
		responsableDependencia = new AdmResponsablesDependencia();
		bandera = false;
		idResponsableDependencia = -1; 
		idDependencia = -1;
	}
	
	public void consultaListaResponsableDependecia() {
		listaResponsableDependencia = new ArrayList<>();
		listaResponsableDependencia = servicioAmdResponsableDependencia.findAll();
	}
	
	public void consultaListaCombos() {//Metodo usado para el contenido de los selectOneMenu
		listaDependencia = new ArrayList<>();
		listaDependencia = servicioAdmDependencia.findAll();	
	}
	
	public void guardar() {//Crea un nuevo registro en la table AdmResponsablesDependencia
		try{
		AdmDependencia idDependencia = new AdmDependencia();
		idDependencia.setIdDependencia(this.idDependencia);
		
		responsableDependencia.setIdResponsableDependencia((int)servicioAmdResponsableDependencia.getPK());
		responsableDependencia.setIdDependencia(idDependencia);
	
		servicioAmdResponsableDependencia.create(responsableDependencia);//Crea un nuevo registro en la tabla AdmResponsablesDependencia
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente"));
		consultaListaResponsableDependecia();
		cancelar();
		} catch (Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));
		cancelar();
		}
	}
	
	//Metodo para actualizar un responsable dependencia
	public void actualizar() {
		try{
		AdmDependencia idDependencia = new AdmDependencia();
		idDependencia.setIdDependencia(this.idDependencia);
		
		this.responsableDependencia.setIdDependencia(idDependencia);
		
		servicioAmdResponsableDependencia.update(responsableDependencia);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente"));
		consultaListaResponsableDependecia();
		cancelar();
		} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al modificar el registro"));
		cancelar();
		}
	}
	
	//Metodo para eliminar un responsable dependencia
	public void eliminar() {
		if(responsableDependenciaSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro"));
		}else{
			responsableDependencia = responsableDependenciaSeleccionada;
			servicioAmdResponsableDependencia.delete(responsableDependencia);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente"));
			consultaListaResponsableDependecia();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(responsableDependenciaSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro"));
		}else{
			consultaListaCombos();
			bandera = true;
			responsableDependencia = responsableDependenciaSeleccionada;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosResponsableDependencia').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		responsableDependencia = new AdmResponsablesDependencia();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosResponsableDependencia').show();");
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
	

	public AdmResponsablesDependencia getResponsableDependencia() {
		return responsableDependencia;
	}

	public void setResponsableDependencia(AdmResponsablesDependencia responsableDependencia) {
		this.responsableDependencia = responsableDependencia;
	}

	public AdmResponsablesDependencia getResponsableDependenciaSeleccionada() {
		return responsableDependenciaSeleccionada;
	}

	public void setResponsableDependenciaSeleccionada(AdmResponsablesDependencia responsableDependenciaSeleccionada) {
		this.responsableDependenciaSeleccionada = responsableDependenciaSeleccionada;
	}

	public List<AdmResponsablesDependencia> getListaResponsableDependencia() {
		return listaResponsableDependencia;
	}

	public void setListaResponsableDependencia(List<AdmResponsablesDependencia> listaResponsableDependencia) {
		this.listaResponsableDependencia = listaResponsableDependencia;
	}

	public List<AdmResponsablesDependencia> getResponsableDependenciaFiltrada() {
		return responsableDependenciaFiltrada;
	}

	public void setResponsableDependenciaFiltrada(List<AdmResponsablesDependencia> responsableDependenciaFiltrada) {
		this.responsableDependenciaFiltrada = responsableDependenciaFiltrada;
	}

	public List<AdmDependencia> getListaDependencia() {
		return listaDependencia;
	}

	public void setListaDependencia(List<AdmDependencia> listaDependencia) {
		this.listaDependencia = listaDependencia;
	}

	public AdmDependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(AdmDependencia dependencia) {
		this.dependencia = dependencia;
	}

	public int getIdResponsableDependencia() {
		return idResponsableDependencia;
	}

	public void setIdResponsableDependencia(int idResponsableDependencia) {
		this.idResponsableDependencia = idResponsableDependencia;
	}

	public int getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(int idDependencia) {
		this.idDependencia = idDependencia;
	}
	
}

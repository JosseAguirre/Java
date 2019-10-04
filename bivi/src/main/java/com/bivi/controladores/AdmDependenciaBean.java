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

import com.bivi.modelos.AdmAgencia;
import com.bivi.modelos.AdmDependencia;
import com.bivi.modelos.AdmDetalleCatalogo;
import com.bivi.servicios.ServicioAdmAgencia;
import com.bivi.servicios.ServicioAdmDependencia;
import com.bivi.servicios.ServicioAdmDetalleCatalogo;


@ManagedBean
@ViewScoped
public class AdmDependenciaBean implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	private AdmDependencia dependencia;
	private AdmDependencia dependenciaSeleccionada;
	private List<AdmDependencia> listaDependencia;
	private List<AdmDependencia> dependenciaFiltrada;
	
	//Variable de tipo AdmDetalleCatalogo de las listas para la busqueda de los selectOneMenu
	private List<AdmDetalleCatalogo> listaTipoDetalleCatalogo;
	private AdmDetalleCatalogo detalleCatalogo;
	
	//Variable de tipo AdmAgencia de las listas para la busqueda de los selectOneMenu
	private List<AdmAgencia> listaAgencia;
	private AdmAgencia agencia;
	
	//Variables que van a ser usadas
	private int idTipoDetalleCatalogo;
	private int idDependencia;
	private int idAgencia;
	private boolean bandera;
	
	//Variables necesarias para la coneccion con los servicios
	@EJB
	private ServicioAdmDependencia servicioAdmDependencia;
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	@EJB
	private ServicioAdmAgencia servicioAdmAgencia;
	
	
	

	@PostConstruct
	public void init() {
		cancelar();	
		consultaListaDependecias();
	}
	
	public void cancelar() {
		dependencia = new AdmDependencia();
		bandera = false;
		idTipoDetalleCatalogo = -1; 
		idDependencia = -1;
		idAgencia = -1;
	}
	
	public void consultaListaDependecias() {
		listaDependencia = new ArrayList<>();
		listaDependencia = servicioAdmDependencia.findAll();
	}
	
	
	public void consultaListaCombos() {//Metodo usado para el contenido de los selectOneMenu

		listaTipoDetalleCatalogo = new ArrayList<>();
		listaTipoDetalleCatalogo = servicioDetalleCatalogo.dependencias();
		
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAdmAgencia.findAll();
	}
	
	public void guardar() {//Crea un nuevo registro en la table AdmDependencia
		try {
		AdmDetalleCatalogo idTipoDetalleCatalogo = new AdmDetalleCatalogo();
		idTipoDetalleCatalogo.setIdDetalleCatalogo(this.idTipoDetalleCatalogo);
		
		AdmAgencia idAgencia = new AdmAgencia();
		idAgencia.setIdAgencia(this.idAgencia);
		
		dependencia.setIdDependencia((int)servicioAdmDependencia.getPK());
		dependencia.setIdAgencia(idAgencia);
		dependencia.setIdTipoDetalleCatalogo(idTipoDetalleCatalogo);
		
		servicioAdmDependencia.create(dependencia);//Crea un nuevo registro en la tabla AdmDependencia
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaDependecias();
		cancelar();
		} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));
		cancelar();
		}
	}
	
	//Metodo para actualizar una dependencia
	public void actualizar() {
		try {
		AdmDetalleCatalogo idTipoDetalleCatalogo = new AdmDetalleCatalogo();
		idTipoDetalleCatalogo.setIdDetalleCatalogo(this.idTipoDetalleCatalogo);
		
		AdmAgencia idAgencia = new AdmAgencia();
		idAgencia.setIdAgencia(this.idAgencia);
		
		this.dependencia.setIdTipoDetalleCatalogo(idTipoDetalleCatalogo);
		this.dependencia.setIdAgencia(idAgencia);
		
		servicioAdmDependencia.update(dependencia);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaListaDependecias();
		cancelar();
		} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al modificar el registro"));
		cancelar();
		}
	}
	
	//Metodo para eliminar una dependencia
	public void eliminar() {
		if(dependenciaSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			dependencia = dependenciaSeleccionada;
			servicioAdmDependencia.delete(dependencia);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaListaDependecias();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(dependenciaSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			consultaListaCombos();
			bandera = true;
			dependencia = dependenciaSeleccionada;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosDependencia').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		dependencia = new AdmDependencia();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosDependencia').show();");
	}
	
	//Resetea el formulario
	@SuppressWarnings("deprecation")
	public void resetarFormulario() {
		RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	//Comprueba si es la modificacion de un registro o la creacion de uno nuevo
	public void persistir() {
	    if (bandera == true) {
	        actualizar();
	    } else {
	        guardar();
	    }
	}
	
	public AdmDependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(AdmDependencia dependencia) {
		this.dependencia = dependencia;
	}
	
	public AdmDependencia getDependenciaSeleccionada() {
		return dependenciaSeleccionada;
	}

	public void setDependenciaSeleccionada(AdmDependencia dependenciaSeleccionada) {
		this.dependenciaSeleccionada = dependenciaSeleccionada;
	}

	public List<AdmDependencia> getListaDependencia() {
		return listaDependencia;
	}

	public void setListaDependencia(List<AdmDependencia> listaDependencia) {
		this.listaDependencia = listaDependencia;
	}

	public List<AdmDependencia> getDependenciaFiltrada() {
		return dependenciaFiltrada;
	}

	public void setDependenciaFiltrada(List<AdmDependencia> dependenciaFiltrada) {
		this.dependenciaFiltrada = dependenciaFiltrada;
	}
	
	public List<AdmDetalleCatalogo> getListaTipoDetalleCatalogo() {
		return listaTipoDetalleCatalogo;
	}
	public void setListaTipoDetalleCatalogo(List<AdmDetalleCatalogo> listaTipoDetalleCatalogo) {
		this.listaTipoDetalleCatalogo = listaTipoDetalleCatalogo;
	}
	public AdmDetalleCatalogo getDetalleCatalogo() {
		return detalleCatalogo;
	}
	public void setDetalleCatalogo(AdmDetalleCatalogo detalleCatalogo) {
		this.detalleCatalogo = detalleCatalogo;
	}
	public int getIdTipoDetalleCatalogo() {
		return idTipoDetalleCatalogo;
	}
	public void setIdTipoDetalleCatalogo(int idTipoDetalleCatalogo) {
		this.idTipoDetalleCatalogo = idTipoDetalleCatalogo;
	}

	public int getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(int idDependencia) {
		this.idDependencia = idDependencia;
	}
	
	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

	public List<AdmAgencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<AdmAgencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public AdmAgencia getAgencia() {
		return agencia;
	}

	public void setAgencia(AdmAgencia agencia) {
		this.agencia = agencia;
	}
}

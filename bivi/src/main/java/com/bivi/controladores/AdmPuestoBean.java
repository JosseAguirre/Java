package com.bivi.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.bivi.modelos.AdmAgencia;
import com.bivi.modelos.AdmPuesto;
import com.bivi.modelos.AdmDetalleCatalogo;
import com.bivi.servicios.ServicioAdmAgencia;
import com.bivi.servicios.ServicioAdmPuestos;
import com.bivi.servicios.ServicioAdmDetalleCatalogo;

@ManagedBean
@ViewScoped
public class AdmPuestoBean implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	private AdmPuesto puesto;
	private AdmPuesto puestoSeleccionado;
	private List<AdmPuesto> listaPuesto;
	private List<AdmPuesto> puestoFiltrado;
	
	
	//Variable de tipo AdmDetalleCatalogo de las listas para la busqueda de los selectOneMenu
	private List<AdmDetalleCatalogo> listaTipoPuesto;
	private AdmDetalleCatalogo detalleCatalogo;
	
	//Variable de tipo AdmCliente de las listas para la busqueda de los selectOneMenu
	private List<AdmAgencia> listaAgencia;
	private AdmAgencia agencia;
	
	// establesco conección a los servicios por medio de los ejb
	@EJB
	private ServicioAdmPuestos servicioAdmPuestos;
	@EJB
	private ServicioAdmAgencia servicioAdmAgencia;
	@EJB
	private ServicioAdmDetalleCatalogo servicioAdmDetalleCatalogo;
	
	
	
	//Variables que van a ser usadas
	private int idTipoPuestoCatalogo;
	private int idAgencia;
	private int idPuesto;
	private boolean bandera;
	
	
	//metodo que inicia el proceso
	@PostConstruct
	public void init() {
		cancelar();
		consultaListaPuesto();
	}
	
	public void cancelar() {
		puesto = new AdmPuesto();
    	bandera = false;
    	idTipoPuestoCatalogo = -1; 
    	idPuesto = -1;
		idAgencia = -1;
    }
	
	//metodo que llena una lista con los registros provenientes de la base de datos
	public void consultaListaPuesto() {
		listaPuesto = new ArrayList<>();
		listaPuesto = servicioAdmPuestos.findAll();
	}
	
	//Metodo usado para el contenido de los selectOneMenu
	public void consultaListaCombos() {
		
		listaTipoPuesto = new ArrayList<>();
		listaTipoPuesto  = servicioAdmDetalleCatalogo.tipopuesto();
		
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAdmAgencia.findAll();
	}
	
	//Metodo para guardar una nueva agencia
	public void guardar() {
		AdmDetalleCatalogo idTipoPuestoCatalogo = new AdmDetalleCatalogo();
		idTipoPuestoCatalogo.setIdDetalleCatalogo(this.idTipoPuestoCatalogo);
		
		AdmAgencia idAgencia = new AdmAgencia();
		idAgencia.setIdAgencia(this.idAgencia);
		
		puesto.setIdPuesto((int)servicioAdmPuestos.getPK());
		puesto.setIdTipoPuestoCatalogo(idTipoPuestoCatalogo);
		puesto.setIdAgencia(idAgencia);
		
		servicioAdmPuestos.create(puesto);
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaPuesto();
		cancelar();
	}
	
	//Metodo para actualizar una agencia
	public void actualizar() {
		AdmDetalleCatalogo idTipoPuestoCatalogo = new AdmDetalleCatalogo();
		idTipoPuestoCatalogo.setIdDetalleCatalogo(this.idTipoPuestoCatalogo);
		
		AdmAgencia idAgencia = new AdmAgencia();
		idAgencia.setIdAgencia(this.idAgencia);;
		
		this.puesto.setIdTipoPuestoCatalogo(idTipoPuestoCatalogo);
		this.puesto.setIdAgencia(idAgencia);
		
		servicioAdmPuestos.update(puesto);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaListaPuesto();
		cancelar();
	}
	
	//Metodo para eliminar una agencia
	public void eliminar() {
		if(puestoSeleccionado == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			puesto = puestoSeleccionado;
			servicioAdmPuestos.delete(puesto);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaListaPuesto();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(puestoSeleccionado == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			consultaListaCombos();
			bandera = true;
			puesto = puestoSeleccionado;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosPuesto').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		puesto = new AdmPuesto();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosPuesto').show();");
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

	
	
	public AdmPuesto getPuesto() {
		return puesto;
	}

	public void setPuesto(AdmPuesto puesto) {
		this.puesto = puesto;
	}

	public AdmPuesto getPuestoSeleccionado() {
		return puestoSeleccionado;
	}

	public void setPuestoSeleccionado(AdmPuesto puestoSeleccionado) {
		this.puestoSeleccionado = puestoSeleccionado;
	}

	public List<AdmPuesto> getListaPuesto() {
		return listaPuesto;
	}

	public void setListaPuesto(List<AdmPuesto> listaPuesto) {
		this.listaPuesto = listaPuesto;
	}

	public List<AdmPuesto> getPuestoFiltrado() {
		return puestoFiltrado;
	}

	public void setPuestoFiltrado(List<AdmPuesto> puestoFiltrado) {
		this.puestoFiltrado = puestoFiltrado;
	}
	
	// -------------------------------------------------------------------
	public List<AdmDetalleCatalogo> getListaTipoPuesto() {
		return listaTipoPuesto;
	}

	public void setTipoPuesto(List<AdmDetalleCatalogo> listaTipoPuesto) {
		this.listaTipoPuesto = listaTipoPuesto;
	}
	
	public AdmDetalleCatalogo getDetalleCatalogo() {
		return detalleCatalogo;
	}

	public void setDetalleCatalogo(AdmDetalleCatalogo detalleCatalogo) {
		this.detalleCatalogo = detalleCatalogo;
	}
	
	//---------------------------------------------------------------------
	
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
	
	//------------------------------------------------------------------
	
	public int getIdTipoPuestoCatalogo() {
		return idTipoPuestoCatalogo;
	}

	public void setIdTipoPuestoCatalogo(int idTipoPuestoCatalogo) {
		this.idTipoPuestoCatalogo = idTipoPuestoCatalogo;
	}
	
	//-----------------------------------------------------------------
	
	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}
	
	//------------------------------------------------------------------
	
	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

}

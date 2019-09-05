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
import com.bivi.modelos.AdmCliente;
import com.bivi.modelos.AdmDetalleCatalogo;
import com.bivi.servicios.ServicioAdmAgencia;
import com.bivi.servicios.ServicioAdmCliente;
import com.bivi.servicios.ServicioAdmDetalleCatalogo;

@ManagedBean
@ViewScoped
public class AdmAgenciaBean implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	private AdmAgencia agencia;
	private AdmAgencia agenciaSeleccionada;
	private List<AdmAgencia> listaAgencia;
	private List<AdmAgencia> agenciaFiltrada;
	
	
	//Variable de tipo AdmDetalleCatalogo de las listas para la busqueda de los selectOneMenu
	private List<AdmDetalleCatalogo> listaCiudad;
	private AdmDetalleCatalogo detalleCatalogo;
	
	//Variable de tipo AdmCliente de las listas para la busqueda de los selectOneMenu
	private List<AdmCliente> listaCliente;
	private AdmCliente cliente;
	
	// establesco conección a los servicios por medio de los ejb
	@EJB
	private ServicioAdmAgencia servicioAdmAgencia;
	@EJB
	private ServicioAdmDetalleCatalogo servicioAdmDetalleCatalogo;
	@EJB
	private ServicioAdmCliente servicioAdmCliente;
	
	
	//Variables que van a ser usadas
	private int idCiudad;
	private int idCliente;
	private int idAgencia;
	private boolean bandera;
	
	
	//metodo que inicia el proceso
	@PostConstruct
	public void init() {
		cancelar();
		consultaListaAgencia();
	}
	
	public void cancelar() {
    	agencia = new AdmAgencia();
    	bandera = false;
    	idCiudad = -1; 
		idCliente = -1;
		idAgencia = -1;
    }
	
	//metodo que llena una lista con los registros provenientes de la base de datos
	public void consultaListaAgencia() {
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAdmAgencia.findAll();
		System.out.println(listaAgencia);
	}
	
	//Metodo usado para el contenido de los selectOneMenu
	public void consultaListaCombos() {
		
		listaCiudad = new ArrayList<>();
		listaCiudad  = servicioAdmDetalleCatalogo.ciudades();
		
		listaCliente = new ArrayList<>();
		listaCliente = servicioAdmCliente.buscaClientePadre();
	}
	
	//Metodo para guardar una nueva agencia
	public void guardar() {
		AdmDetalleCatalogo idCiudad = new AdmDetalleCatalogo();
		idCiudad.setIdDetalleCatalogo(this.idCliente);
		
		AdmCliente idCliente = new AdmCliente();
		idCliente.setIdCliente(this.idCliente);
		
		agencia.setIdAgencia((int)servicioAdmAgencia.getPK());
		agencia.setIdCiudad(idCiudad);
		agencia.setIdCliente(idCliente);
		
		servicioAdmAgencia.create(agencia);
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaAgencia();
		cancelar();
	}
	
	//Metodo para actualizar una agencia
	public void actualizar() {
		AdmDetalleCatalogo idCiudad = new AdmDetalleCatalogo();
		idCiudad.setIdDetalleCatalogo(this.idCiudad);
		
		AdmCliente idCliente = new AdmCliente();
		idCliente.setIdCliente(this.idCliente);
		
		this.agencia.setIdCiudad(idCiudad);
		this.agencia.setIdCliente(idCliente);
		
		servicioAdmAgencia.update(agencia);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaListaAgencia();
		cancelar();
	}
	
	//Metodo para eliminar una agencia
	public void eliminar() {
		if(agenciaSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			agencia = agenciaSeleccionada;
			servicioAdmAgencia.delete(agencia);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaListaAgencia();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(agenciaSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			consultaListaCombos();
			bandera = true;
			agencia = agenciaSeleccionada;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosAgencia').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		agencia = new AdmAgencia();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosAgencia').show();");
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

	
	
	public AdmAgencia getAgencia() {
		return agencia;
	}

	public void setAgencia(AdmAgencia agencia) {
		this.agencia = agencia;
	}

	public AdmAgencia getAgenciaSeleccionada() {
		return agenciaSeleccionada;
	}

	public void setAgenciaSeleccionada(AdmAgencia agenciaSeleccionada) {
		this.agenciaSeleccionada = agenciaSeleccionada;
	}

	public List<AdmAgencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<AdmAgencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public List<AdmAgencia> getAgenciaFiltrada() {
		return agenciaFiltrada;
	}

	public void setAgenciaFiltrada(List<AdmAgencia> agenciaFiltrada) {
		this.agenciaFiltrada = agenciaFiltrada;
	}
	
	public List<AdmDetalleCatalogo> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}
	
	public AdmDetalleCatalogo getDetalleCatalogo() {
		return detalleCatalogo;
	}

	public void setDetalleCatalogo(AdmDetalleCatalogo detalleCatalogo) {
		this.detalleCatalogo = detalleCatalogo;
	}
	
	public List<AdmCliente> getListaCliente() {
		return listaCliente;
	}
	
	public void setListaCliente(List<AdmCliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	
	public AdmCliente getCliente() {
		return cliente;
	}
	
	public void setCliente(AdmCliente cliente) {
		this.cliente = cliente;
	}
	
	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

}

package com.scl.administracion.controlador;

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
import com.scl.administracion.modelo.*;
import com.scl.administracion.servicio.*;


@ManagedBean
@ViewScoped
public class AdmClienteBean implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	private AdmCliente admCliente;
	private AdmCliente clienteSeleccionado;
	private List<AdmCliente> listaCliente;
	private List<AdmCliente> listaClientePadre;
	private List<AdmCliente> clienteFiltrado;
	
	
	
	
	

	//Variable de tipo admCliente de las listas para la busqueda de los selectOneMenu
	


	//Variable de tipo AdmDetalleCatalogo de las listas para la busqueda de los selectOneMenu
	private List<AdmDetalleCatalogo> listaCiudad;
	private List<AdmDetalleCatalogo> listaEstado;
	private List<AdmDetalleCatalogo> listaTipoCliente;
	private List<AdmDetalleCatalogo> listaActividad;
	
	private AdmDetalleCatalogo detalleCatalogo;
	
	
	//Variables que van a ser usadas


	private int idCiudad;
	private int idCliente;
	private int idEstado;
	private int idActividad;
	private int idClientePadre;
	private int idTipoCliente;
	private boolean bandera ;
	

	
	//Variables necesarias para la coneccion con los servicios
	@EJB
	private ServicioAdmCliente servicioCliente;
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;

	
	
	@PostConstruct
	public void init() {
		cancelar();	
		consultaListaClientes();
       
	}
	
	public void cancelar() {
		admCliente = new AdmCliente();
		
		idCiudad = -1;
		idActividad = -1;
		idEstado = -1; 
		idTipoCliente = -1;	
		idCliente = -1;
		
	}
	
	////SECCION CONSULTAS//////
	
	public void consultaListaClientes() {
		listaCliente = new ArrayList<>();
		listaCliente = servicioCliente.findAll();	
	}
	
	public void consultaListaCombos() {//Metodo usado para el contenido de los selectOneMenu

		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCatalogo.ciudades();
		
		listaActividad = new ArrayList<>();
		listaActividad = servicioDetalleCatalogo.actividades();
		
		listaEstado  = new ArrayList<>();
		listaEstado = servicioDetalleCatalogo.estados();
		
		listaClientePadre = new ArrayList<>();
		listaClientePadre = servicioCliente.buscaClientePadre();
		
		listaTipoCliente = new ArrayList<>();
		listaTipoCliente = servicioDetalleCatalogo.tiposcliente();
		
		
	}
	
	
	
	
///CRUD ////
	
	public void guardar() {//Crea un nuevo registro en la table admCliente
		
		AdmDetalleCatalogo idCiudad = new AdmDetalleCatalogo();
		idCiudad.setIdDetalleCatalogo(this.idCiudad);
		
		AdmDetalleCatalogo tipoCliente = new AdmDetalleCatalogo();
		tipoCliente.setIdDetalleCatalogo(this.idTipoCliente);
		
		AdmCliente clientePadre = new AdmCliente();
		clientePadre.setIdCliente(this.idCliente);
		
		AdmDetalleCatalogo actividad = new AdmDetalleCatalogo();
		actividad.setIdDetalleCatalogo(this.idActividad);
		
		AdmDetalleCatalogo estado = new AdmDetalleCatalogo();
		estado.setIdDetalleCatalogo(this.idEstado);
		
		
		

		admCliente.setIdCliente((int)servicioCliente.getPK());//Setea el Primary key del registro a crear
		admCliente.setIdCiudadDc(idCiudad);
		admCliente.setIdTipoClienteDc(tipoCliente);
		admCliente.setIdClientePadre(clientePadre);
		admCliente.setIdActividadDc(actividad);
		admCliente.setIdEstadoDc(estado);
		
		if(clientePadre.getIdCliente() ==0){
    		this.admCliente.setIdClientePadre(null);	
    	}
	
		
		servicioCliente.create(admCliente);//Crea un nuevo registro en la tabla admCliente
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaClientes();
		cancelar();
	}



public void actualizar() {
	/*
	admCliente.setIdActividadDc(servicioDetalleCatalogo.findOne(idActividad));
	admCliente.setIdCiudadDc(servicioDetalleCatalogo.findOne(idCiudad));
	admCliente.setIdEstadoDc(servicioDetalleCatalogo.findOne(idEstado));
	admCliente.setIdTipoClienteDc(servicioDetalleCatalogo.findOne(idTipoCliente));
	*/
	AdmDetalleCatalogo idCiudad = new AdmDetalleCatalogo();
	idCiudad.setIdDetalleCatalogo(this.idCiudad);
	
	AdmDetalleCatalogo idTipoCliente = new AdmDetalleCatalogo();
	idTipoCliente.setIdDetalleCatalogo(this.idCliente);
	
	AdmCliente idCliente = new AdmCliente();
	idCliente.setIdCliente(this.idCliente);
	
	AdmDetalleCatalogo idActividad = new AdmDetalleCatalogo();
	idActividad.setIdDetalleCatalogo(this.idActividad);
	
	AdmDetalleCatalogo idEstado = new AdmDetalleCatalogo();
	idEstado.setIdDetalleCatalogo(this.idEstado);
	
	this.admCliente.setIdCiudadDc(idCiudad);
	this.admCliente.setIdActividadDc(idActividad);
	this.admCliente.setIdTipoClienteDc(idTipoCliente);
	this.admCliente.setIdClientePadre(idCliente);
	this.admCliente.setIdEstadoDc(idEstado);
	
	
	servicioCliente.update(admCliente);	
	FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
	consultaListaClientes();
	cancelar();
	
}

public void eliminar() {
	if(clienteSeleccionado == null){
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
				
	}else {	
	admCliente = clienteSeleccionado;
	//responsable.setSysdelete(true);
	detalleCatalogo.setIdDetalleCatalogo(94);
	//detalleCatalogo = servicioDetalleCatalogo.findOne(idEstado);
	admCliente.setIdEstadoDc(detalleCatalogo);
	servicioCliente.update(admCliente);
	FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
	consultaListaClientes();
	cancelar();		
	}
	
	
}

public void modificar(){
	if(clienteSeleccionado == null){
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
				
	}else {
		
		consultaListaCombos();
		
	bandera =	true;
	admCliente = clienteSeleccionado;
	idCiudad = admCliente.getIdCiudadDc().getIdDetalleCatalogo();
	idEstado = admCliente.getIdEstadoDc().getIdDetalleCatalogo();
	idActividad = admCliente.getIdActividadDc().getIdDetalleCatalogo();
	idTipoCliente = admCliente.getIdTipoClienteDc().getIdDetalleCatalogo();
	
	if(admCliente.getIdClientePadre() == null){
		idClientePadre = 0;	
	} else{
	idClientePadre = admCliente.getIdClientePadre().getIdCliente();
	}
	
	
	resetarFormulario();
	RequestContext.getCurrentInstance().execute("PF('dlgDatosCliente').show();");
	}
}

public void nuevo(){	
	bandera =	false;
	consultaListaCombos();
	admCliente = new AdmCliente();
	resetarFormulario();
	RequestContext.getCurrentInstance().execute("PF('dlgDatosCliente').show();");
	
}

public static void resetarFormulario() {
    RequestContext.getCurrentInstance().reset("frmCrear");
}

public void persistir() {
    if (bandera == true) {
        actualizar();
    } else {
        guardar();
    }
}

public AdmCliente getAdmCliente() {
	return admCliente;
}

public void setAdmCliente(AdmCliente admCliente) {
	this.admCliente = admCliente;
}

public List<AdmCliente> getListaCliente() {
	return listaCliente;
}

public void setListaCliente(List<AdmCliente> listaCliente) {
	this.listaCliente = listaCliente;
}

public List<AdmCliente> getListaClientePadre() {
	return listaClientePadre;
}

public void setListaClientePadre(List<AdmCliente> listaClientePadre) {
	this.listaClientePadre = listaClientePadre;
}

public List<AdmDetalleCatalogo> getListaCiudad() {
	return listaCiudad;
}

public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
	this.listaCiudad = listaCiudad;
}

public List<AdmDetalleCatalogo> getListaEstado() {
	return listaEstado;
}

public void setListaEstado(List<AdmDetalleCatalogo> listaEstado) {
	this.listaEstado = listaEstado;
}

public List<AdmDetalleCatalogo> getListaTipoCliente() {
	return listaTipoCliente;
}

public void setListaTipoCliente(List<AdmDetalleCatalogo> listaTipoCliente) {
	this.listaTipoCliente = listaTipoCliente;
}

public List<AdmDetalleCatalogo> getListaActividad() {
	return listaActividad;
}

public void setListaActividad(List<AdmDetalleCatalogo> listaActividad) {
	this.listaActividad = listaActividad;
}

public int getIdCiudad() {
	return idCiudad;
}

public void setIdCiudad(int idCiudad) {
	this.idCiudad = idCiudad;
}

public int getIdEstado() {
	return idEstado;
}

public void setIdEstado(int idEstado) {
	this.idEstado = idEstado;
}

public int getIdActividad() {
	return idActividad;
}

public void setIdActividad(int idActividad) {
	this.idActividad = idActividad;
}



public int getIdTipoCliente() {
	return idTipoCliente;
}

public void setIdTipoCliente(int idTipoCliente) {
	this.idTipoCliente = idTipoCliente;
}




public int getIdClientePadre() {
	return idClientePadre;
}

public void setIdClientePadre(int idClientePadre) {
	this.idClientePadre = idClientePadre;
}

public AdmCliente getClienteSeleccionado() {
	return clienteSeleccionado;
}

public void setClienteSeleccionado(AdmCliente clienteSeleccionado) {
	this.clienteSeleccionado = clienteSeleccionado;
}

public AdmDetalleCatalogo getDetalleCatalogo() {
	return detalleCatalogo;
}

public void setDetalleCatalogo(AdmDetalleCatalogo detalleCatalogo) {
	this.detalleCatalogo = detalleCatalogo;
}

public List<AdmCliente> getClienteFiltrado() {
	return clienteFiltrado;
}

public void setClienteFiltrado(List<AdmCliente> clienteFiltrado) {
	this.clienteFiltrado = clienteFiltrado;
}

public int getIdCliente() {
	return idCliente;
}

public void setIdCliente(int idCliente) {
	this.idCliente = idCliente;
}






}

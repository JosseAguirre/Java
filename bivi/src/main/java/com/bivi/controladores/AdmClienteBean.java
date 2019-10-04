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
import com.bivi.servicios.*;
import com.bivi.modelos.*;


@ManagedBean
@ViewScoped
public class AdmClienteBean implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	private AdmCliente admCliente;
	private AdmCliente clienteSeleccionado;
	private List<AdmCliente> listaCliente;
	private List<AdmCliente> listaClientePadre;
	private List<AdmCliente> clienteFiltrado;
	
	
	//Variable de tipo AdmDetalleCatalogo de las listas para la busqueda de los selectOneMenu
	private List<AdmDetalleCatalogo> listaCiudad;
	private AdmDetalleCatalogo detalleCatalogo;
	private List<AdmDetalleCatalogo> listaTipoCliente;
	private AdmDetalleCatalogo detalleTipoCliente;
	
	
	
	//Variables que van a ser usadas
	private int idCiudad;
	private int idTipoClienteCatalogo;
	private int idCliente;
	private int idClientePadre;
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
		bandera = false;
		idCiudad = -1; 
		idCliente = -1;
		idTipoClienteCatalogo = -1;
	}
	
	public void consultaListaClientes() {
		listaCliente = new ArrayList<>();
		listaCliente = servicioCliente.findAll();	
	}
	
	public void consultaListaCombos() {//Metodo usado para el contenido de los selectOneMenu

		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCatalogo.ciudades();
		
		listaTipoCliente = new ArrayList<>();
		listaTipoCliente = servicioDetalleCatalogo.tipocliente();
		
		listaClientePadre = new ArrayList<>();
		listaClientePadre = servicioCliente.buscaClientePadre();
		
		
	}
	
	
	public void guardar() {//Crea un nuevo registro en la table admCliente
		
		AdmDetalleCatalogo idCiudad = new AdmDetalleCatalogo();
		idCiudad.setIdDetalleCatalogo(this.idCiudad);
		
		AdmDetalleCatalogo idTipoClienteCatalogo = new AdmDetalleCatalogo();
		idTipoClienteCatalogo.setIdDetalleCatalogo(this.idTipoClienteCatalogo);
		
		AdmCliente clientePadre = new AdmCliente();
		clientePadre.setIdCliente(this.idCliente);
		

		admCliente.setIdCliente((int)servicioCliente.getPK());//Setea el Primary key del registro a crear
		admCliente.setIdCiudad(idCiudad);
		admCliente.setIdTipoClienteCatalogo(idTipoClienteCatalogo);
		admCliente.setIdClientePadre(clientePadre);
		
		
		if(clientePadre.getIdCliente() ==0){
    		this.admCliente.setIdClientePadre(null);	
    	}
	
		
		servicioCliente.create(admCliente);//Crea un nuevo registro en la tabla admCliente
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaClientes();
		cancelar();
	}


	public void actualizar() {
	
	AdmDetalleCatalogo idCiudad = new AdmDetalleCatalogo();
	idCiudad.setIdDetalleCatalogo(this.idCiudad);
	
	AdmDetalleCatalogo idTipoClienteCatalogo = new AdmDetalleCatalogo();
	idTipoClienteCatalogo.setIdDetalleCatalogo(this.idTipoClienteCatalogo);
	
	AdmCliente clientePadre = new AdmCliente();
	clientePadre.setIdCliente(this.idCliente);
	
	this.admCliente.setIdCiudad(idCiudad);
	this.admCliente.setIdClientePadre(clientePadre);
	this.admCliente.setIdTipoClienteCatalogo(idTipoClienteCatalogo);
	
	if(clientePadre.getIdCliente() ==0){
		this.admCliente.setIdClientePadre(null);	
	}
	
	
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
		servicioCliente.delete(admCliente);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
		consultaListaClientes();
		cancelar();		
		}
	}
	
	@SuppressWarnings("deprecation")
	public void modificar(){
		if(clienteSeleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
			
			consultaListaCombos();
			
		bandera =	true;
		admCliente = clienteSeleccionado;
		idCiudad = admCliente.getIdCiudad().getIdDetalleCatalogo();
		
		if(admCliente.getIdClientePadre() == null){
			idClientePadre = 0;	
		} else{
		idClientePadre = admCliente.getIdClientePadre().getIdCliente();
		}
		
		
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosCliente').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo(){	
		bandera =	false;
		consultaListaCombos();
		admCliente = new AdmCliente();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosCliente').show();");
		
	}
	
	@SuppressWarnings("deprecation")
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
	
	
	// Getters and Setters
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

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
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

	public List<AdmDetalleCatalogo> getListaTipoCliente() {
		return listaTipoCliente;
	}

	public void setListaTipoCliente(List<AdmDetalleCatalogo> listaTipoCliente) {
		this.listaTipoCliente = listaTipoCliente;
	}

	public AdmDetalleCatalogo getDetalleTipoCliente() {
		return detalleTipoCliente;
	}

	public void setDetalleTipoCliente(AdmDetalleCatalogo detalleTipoCliente) {
		this.detalleTipoCliente = detalleTipoCliente;
	}

	public int getIdTipoClienteCatalogo() {
		return idTipoClienteCatalogo;
	}

	public void setIdTipoClienteCatalogo(int idTipoClienteCatalogo) {
		this.idTipoClienteCatalogo = idTipoClienteCatalogo;
	}

}

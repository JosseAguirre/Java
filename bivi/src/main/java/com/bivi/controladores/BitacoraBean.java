package com.bivi.controladores;
 
import java.io.Serializable;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

//import org.primefaces.component.export.ExcelOptions;
//import org.primefaces.component.export.PDFOptions;
import org.primefaces.context.RequestContext;

import com.bivi.modelos.*;

import com.bivi.servicios.*;






@ManagedBean
@ViewScoped
public class BitacoraBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FisDetalleBitacora detalleBitacora;
	private FisDetalleBitacora detalleBitacoraSeleccionada;
    private List<FisDetalleBitacora> listaDetalleBitacora;
    private List<FisDetalleBitacora> detalleBitacoraFiltrada;
    
    //Variable de tipo AdmPuesto de las listas para la busqueda de los selectOneMenu
  	private AdmPuesto puesto;
  	private List<AdmPuesto> listaPuesto;

  	// establesco conección a los servicios por medio de los ejb
	@EJB
	private ServicioFisDetalleBitacora servicioFisDetalleBitacora ;
	@EJB
	private ServicioAdmPuestos servicioAdmPuesto;
	
	//Variables que van a ser usadas
	private int idDetalleBitacora;
	private int idPuesto;
	private Date fecha;
	private boolean bandera;

	
	
	

	@PostConstruct
	public void init() {
		cancelar();
		consultaDetalleBitacora();
	}

	public void cancelar() {
		detalleBitacora = new FisDetalleBitacora();
    	bandera = false;
    	idDetalleBitacora = -1;
    	idPuesto = -1;
	}
	

	
	
	public void consultaDetalleBitacora() {	
		setListaDetalleBitacora(new ArrayList<>());
		setListaDetalleBitacora(servicioFisDetalleBitacora.buscaBitacora());
	}
	
	//Metodo usado para el contenido de los selectOneMenu
	public void consultaListaCombos() {	
		setListaPuesto(new ArrayList<>());
		setListaPuesto(servicioAdmPuesto.findAll());
	}
	
	//Metodo para guardar una nueva bitacora
	public void guardar() {
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		detalleBitacora.setIdDetalleBitacora((int)servicioFisDetalleBitacora.getPK());
		detalleBitacora.setIdPuesto(idPuesto);	
		
		servicioFisDetalleBitacora.create(detalleBitacora);
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaDetalleBitacora();
		cancelar();
	}

	//Metodo para actualizar una bitacora
	public void actualizar() {
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		
		this.detalleBitacora.setIdPuesto(idPuesto);
		
		servicioFisDetalleBitacora.update(detalleBitacora);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaDetalleBitacora();
		cancelar();
	}
	
	//Metodo para eliminar una bitacora
	public void eliminar() {
		if(detalleBitacoraSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			detalleBitacora = detalleBitacoraSeleccionada;
			servicioFisDetalleBitacora.delete(detalleBitacora);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaDetalleBitacora();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(detalleBitacoraSeleccionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			consultaListaCombos();
			bandera = true;
			detalleBitacora = detalleBitacoraSeleccionada;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosBitacora').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		detalleBitacora = new FisDetalleBitacora();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosBitacora').show();");
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



	public FisDetalleBitacora getDetalleBitacora() {
		return detalleBitacora;
	}
	
	public void setDetalleBitacora(FisDetalleBitacora detalleBitacora) {
		this.detalleBitacora = detalleBitacora;
	}

	public FisDetalleBitacora getDetalleBitacoraSeleccionada() {
		return detalleBitacoraSeleccionada;
	}

	public void setDetalleBitacoraSeleccionada(FisDetalleBitacora detalleBitacoraSeleccionada) {
		this.detalleBitacoraSeleccionada = detalleBitacoraSeleccionada;
	}

	public List<FisDetalleBitacora> getListaDetalleBitacora() {
		return listaDetalleBitacora;
	}

	public void setListaDetalleBitacora(List<FisDetalleBitacora> listaDetalleBitacora) {
		this.listaDetalleBitacora = listaDetalleBitacora;
	}

	public List<FisDetalleBitacora> getDetalleBitacoraFiltrada() {
		return detalleBitacoraFiltrada;
	}

	public void setDetalleBitacoraFiltrada(List<FisDetalleBitacora> detalleBitacoraFiltrada) {
		this.detalleBitacoraFiltrada = detalleBitacoraFiltrada;
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

	public int getIdDetalleBitacora() {
		return idDetalleBitacora;
	}

	public void setIdDetalleBitacora(int idDetalleBitacora) {
		this.idDetalleBitacora = idDetalleBitacora;
	}

	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

}

package com.bivi.controladores;
 
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.bivi.modelos.*;

import com.bivi.servicios.*;






@ManagedBean
@ViewScoped
public class ConsignasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private FisConsignasGenerales consignasGenerales;
	private FisConsignasEspecificasPuesto consignasEspecificas;
	
	private List<FisConsignasGenerales> listaConsignasG;
	private List<FisConsignasEspecificasPuesto> listaConsignasE;
	
	private List<FisConsignasGenerales> consignasSeleccionadasG;
	private List<FisConsignasEspecificasPuesto> consignasSeleccionadasE;
	
	private List<AdmPuesto>listaPuestos;
	
	private int puestoId;
	private int idPuestoA;
	
	private boolean flagG;
	private boolean flagE;
	
	

	@EJB
	private ServicioFisConsignasG servicioConsignasG ; 
	
	@EJB
	private ServicioFisConsignasE servicioConsignasE ;
	

	@EJB
	private ServicioAdmPuestos servicioPuestos ;
	
	

	@PostConstruct
	public void init() {
		cancelar();
		consultaConsignasG();
		consultaConsignasE();
		consultaPuestos();
		flagG = false;
		flagE = false;
		
		

			
	}
	
public void cancelar() {
	
	consignasGenerales = new FisConsignasGenerales();
	consignasEspecificas = new FisConsignasEspecificasPuesto();
	
		
	

	}
	
	
	
	
public void consultaConsignasG() {
		
	listaConsignasG = new ArrayList<>();
		listaConsignasG = servicioConsignasG.findConsigGen();
		
		
		
	}

public void consultaPuestos() {
	
	listaPuestos = new ArrayList<>();
	listaPuestos = servicioPuestos.findAll();
		
		
		
	}


public void consultaConsignasE() {
	
	listaConsignasE = new ArrayList<>();
	listaConsignasE = servicioConsignasE.findConsigEsp();

	
}


public void persistirG(){
	
	if(flagG==true){
		System.out.println("respuesta:"+flagG);
		actulizaConsignaG ();
		
	}	
	else{
		System.out.println("respuesta:"+flagG);
		guardarConsignasG();
		
	}
	
}

	public void actulizaConsignaG() {
	
		
		servicioConsignasG.update(consignasGenerales);

	}

	public void guardarConsignasG() {
		
		

		consignasGenerales.setIdConsignaG(servicioConsignasG.getPK());
		servicioConsignasG.create(consignasGenerales);
		

		FacesContext.getCurrentInstance().addMessage("exito",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));

		consultaConsignasG();
		flagG = false;

		cancelar();
	}
	
	
	
	
	public void eliminarConsignasG(){
		
		servicioConsignasG.delete(consignasGenerales);
		consultaConsignasG();
		
		
		
	}
	
	
	
	/////// CONSIGNAS GENERALES)
	
	

	
		
		
		
		
		public void onRowEditG(RowEditEvent event){
			//FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
	        //FacesContext.getCurrentInstance().addMessage(null, msg);

			consignasGenerales =(FisConsignasGenerales) event.getObject();
			
			actulizaConsignaG();
			
			
			
		}
	
		
	public void onRowCancelG(RowEditEvent event){
			
			
			
		}
	
	
	public void onCellEditG(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
	}

	
	///// CONSIGNAS ESPECIFICAS
	
public void persistirE(){
		
		if(flagE==true){
			
			actulizaConsignaE ();
			
			
		}	
		else{
			
			guardarConsignasE();
			
		}
		
	}
	
	
	
	
	
	public void onRowEditE(RowEditEvent event){
		consignasEspecificas =(FisConsignasEspecificasPuesto) event.getObject();
		System.out.println("mmmmm"+consignasEspecificas.getIdPuesto().getNombrePuesto().toString());
		actulizaConsignaE();
		
		
		
	}

	
public void onRowCancelE(RowEditEvent event){
		
		
		
	}

public void guardarConsignasE() {
	
	
	
	AdmPuesto puesto = new AdmPuesto ();
	puesto.setIdPuesto(idPuestoA);
	
	consignasEspecificas.setIdConsignaEspecificaPuesto(servicioConsignasE.getPK());
	consignasEspecificas.setIdPuesto(puesto);
	servicioConsignasE.create(consignasEspecificas);

	FacesContext.getCurrentInstance().addMessage("exito",
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));

	consultaConsignasE();

	cancelar();
}

public void eliminarConsignasE(){
	
	servicioConsignasE.delete(consignasEspecificas);
	consultaConsignasE();
	
	
	
}

public void actulizaConsignaE() {
	
	
	servicioConsignasE.update(consignasEspecificas);

}


	
	
	
	
	

	public FisConsignasGenerales getConsignasGenerales() {
		return consignasGenerales;
	}

	public void setConsignasGenerales(FisConsignasGenerales consignasGenerales) {
		this.consignasGenerales = consignasGenerales;
	}

	public FisConsignasEspecificasPuesto getConsignasEspecificas() {
		return consignasEspecificas;
	}

	public void setConsignasEspecificas(FisConsignasEspecificasPuesto consignasEspecificas) {
		this.consignasEspecificas = consignasEspecificas;
	}

	public List<FisConsignasGenerales> getListaConsignasG() {
		return listaConsignasG;
	}

	public void setListaConsignasG(List<FisConsignasGenerales> listaConsignasG) {
		this.listaConsignasG = listaConsignasG;
	}

	public List<FisConsignasEspecificasPuesto> getListaConsignasE() {
		return listaConsignasE;
	}

	public void setListaConsignasE(List<FisConsignasEspecificasPuesto> listaConsignasE) {
		this.listaConsignasE = listaConsignasE;
	}

	public List<FisConsignasGenerales> getConsignasSeleccionadasG() {
		return consignasSeleccionadasG;
	}

	public void setConsignasSeleccionadasG(List<FisConsignasGenerales> consignasSeleccionadasG) {
		this.consignasSeleccionadasG = consignasSeleccionadasG;
	}

	public List<FisConsignasEspecificasPuesto> getConsignasSeleccionadasE() {
		return consignasSeleccionadasE;
	}

	public void setConsignasSeleccionadasE(List<FisConsignasEspecificasPuesto> consignasSeleccionadasE) {
		this.consignasSeleccionadasE = consignasSeleccionadasE;
	}

	public List<AdmPuesto> getListaPuestos() {
		return listaPuestos;
	}

	public void setListaPuestos(List<AdmPuesto> listaPuestos) {
		this.listaPuestos = listaPuestos;
	}



	public int getPuestoId() {
		return puestoId;
	}

	public void setPuestoId(int puestoId) {
		this.puestoId = puestoId;
	}

	public int getIdPuestoA() {
		return idPuestoA;
	}

	public void setIdPuestoA(int idPuestoA) {
		this.idPuestoA = idPuestoA;
	}

	


	

	
	
	

}

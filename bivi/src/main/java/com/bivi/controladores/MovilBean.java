package com.bivi.controladores;
 
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.bivi.modelos.*;

import com.bivi.servicios.*;







@ManagedBean
@ViewScoped
public class MovilBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FisMovil movil;
    private List<FisMovil> listaMovil;
    
    private List<AdmDetalleCatalogo> listaCiudad;
    
    
    private List<AdmAgencia> listaAgencia;
    
    
    int idAgencia;
    int idDetalleCat;



	@EJB
	private ServicioFisMovil servicioMovil ; 
	
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCat; 
	
	@EJB
	private ServicioAdmAgencia servicioAgencia ; 

	
	
	

	@PostConstruct
	public void init() {
		cancelar();
		
		consultaMovil();
		
		consultaCiudad();
		consultaAgencia();
		
		
		
		
	}

	
	
	
	public void cancelar() {
		movil = new FisMovil();
	     idAgencia = -1;
	     idDetalleCat = -1;
		
		
		


	}
	
	
	
	@SuppressWarnings("deprecation")
	public void guardar() {
		AdmAgencia agencia = new AdmAgencia();
		agencia.setIdAgencia(idAgencia);
		
		

		AdmDetalleCatalogo detalleCat = new AdmDetalleCatalogo();
		detalleCat.setIdDetalleCatalogo(idDetalleCat);
				
		movil.setIdMovil(servicioMovil.getPK());
		movil.setIdAgencia(agencia);
		movil.setIdCiudadDc(detalleCat);
		
		
		System.out.println("agencia  "+idAgencia);
		System.out.println("ciudad  "+idDetalleCat);
		
		servicioMovil.create(movil);
		
		consultaMovil();
		cancelar();
		PrimeFaces.current().resetInputs("frmCrear");
		//RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	/*@SuppressWarnings("deprecation")
	public void nuevo(){	
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgMovil').show();");
		
	}
	
	@SuppressWarnings("deprecation")
	public static void resetarFormulario() {
        RequestContext.getCurrentInstance().reset("frmCrear");
	}*/
	
	
	
	public void consultaCiudad() {	
		
		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCat.ciudadesTodas();
		
		
	}
	
	
	public void consultaAgencia() {	
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAgencia.findAll();
	}

	
	
	public void consultaMovil() {	
		
	
		listaMovil = new ArrayList<>();
		listaMovil = servicioMovil.buscaMovil();
		
		
	
	}
	
	
	
	public void onRowEdit(RowEditEvent event){
		movil =(FisMovil) event.getObject();
		//System.out.println("mmmmm"+consignasEspecificas.getIdPuesto().getNombrePuesto().toString());
		actualizaMovil();
		
		
		
	}
	
	public void actualizaMovil(){
		
		servicioMovil.update(movil);
		
		
	}
	
	public void eliminarMovil(){
		servicioMovil.delete(movil);
		consultaMovil();
		cancelar();
		
	}

	
public void onRowCancel(RowEditEvent event){
		
		
		
	}






	public FisMovil getMovil() {
		return movil;
	}




	public void setMovil(FisMovil movil) {
		this.movil = movil;
	}




	public List<FisMovil> getListaMovil() {
		return listaMovil;
	}




	public void setListaMovil(List<FisMovil> listaMovil) {
		this.listaMovil = listaMovil;
	}




	public List<AdmDetalleCatalogo> getListaCiudad() {
		return listaCiudad;
	}




	public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}




	public List<AdmAgencia> getListaAgencia() {
		return listaAgencia;
	}




	public void setListaAgencia(List<AdmAgencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}




	public int getIdAgencia() {
		return idAgencia;
	}




	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}




	public int getIdDetalleCat() {
		return idDetalleCat;
	}




	public void setIdDetalleCat(int idDetalleCat) {
		this.idDetalleCat = idDetalleCat;
	}




	



	
	
	

}

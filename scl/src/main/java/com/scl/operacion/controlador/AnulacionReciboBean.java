package com.scl.operacion.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.administracion.servicio.ServicioUsuarioCiudad;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.*;


@ManagedBean
@ViewScoped
public class AnulacionReciboBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	    private List<OpeTransaccion> listaTransaccion;
	    private OpeTransaccion transaccionSeleccionada;
	    private List<AdmDetalleCatalogo> listaCiudad;
	    String numeroRecibo;
	    String observacion;
	    
	    
	    
	    
	    int idCiudad;
	    

	    @EJB
	    private ServicioTransaccion servicioTransaccion;
	    @EJB
		private ServicioUsuarioCiudad servicioUsuarioCiudad;
	    @EJB
		private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	        
	        consultaListaCiudad();
	        
	      
	       

	       
	    }

	  
	     
	    public void cancelar() {
	    	numeroRecibo = null;
	 	   observacion = null;
	 	  listaTransaccion = new ArrayList<>();
	    	
	        

	    }
	    
	    public void consultaListaCiudad() {
	    	AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
	    		setListaCiudad(new ArrayList<>());
	    		setListaCiudad(servicioDetalleCatalogo.ciudadesAsignadas(usuario));
	    	}



	   public void consultarRecibo(){
		   listaTransaccion = new ArrayList<>();
		   listaTransaccion = servicioTransaccion.buscaReciboParaAnular();
		   
		   
		   
	   }
	     
	    public void anular() {
	    	

	        try {
	        	
	        	if(transaccionSeleccionada == null){
	        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Debe seleccionar un registro"));
	        		
	        	}else{
	        		transaccionSeleccionada.setIdTransaccion(servicioTransaccion.getPK());
	        		transaccionSeleccionada.setEstadoTransaccion(21);
	        		transaccionSeleccionada.setObservacion(observacion);
	        		
	        		
	        		servicioTransaccion.create(transaccionSeleccionada);
	        		
	        		cancelar();
	        		
	        	}
	        	
	        	
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al anular"));

	        }

	    }






		public int getIdCiudad() {
			return idCiudad;
		}



		public void setIdCiudad(int idCiudad) {
			this.idCiudad = idCiudad;
		}



		public OpeTransaccion getTransaccionSeleccionada() {
			return transaccionSeleccionada;
		}



		public void setTransaccionSeleccionada(OpeTransaccion transaccionSeleccionada) {
			this.transaccionSeleccionada = transaccionSeleccionada;
		}



		public List<AdmDetalleCatalogo> getListaCiudad() {
			return listaCiudad;
		}



		public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
			this.listaCiudad = listaCiudad;
		}



		public List<OpeTransaccion> getListaTransaccion() {
			return listaTransaccion;
		}



		public void setListaTransaccion(List<OpeTransaccion> listaTransaccion) {
			this.listaTransaccion = listaTransaccion;
		}



		public String getNumeroRecibo() {
			return numeroRecibo;
		}



		public void setNumeroRecibo(String numeroRecibo) {
			this.numeroRecibo = numeroRecibo;
		}



		public String getObservacion() {
			return observacion;
		}



		public void setObservacion(String observacion) {
			this.observacion = observacion;
		}


	  

}






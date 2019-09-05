package com.scl.sac.controlador;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmAgencia;
import com.scl.administracion.servicio.ServicioAdmCliente;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.sac.modelo.SacDetalleSolicitudServicio;
import com.scl.sac.modelo.SacSolicitudServicio;
import com.scl.sac.servicio.ServicioDetalleSolicitud;
import com.scl.sac.servicio.ServicioSolicitud;

@ManagedBean
@ViewScoped
public class SolicitudBajoPedidoBean implements Serializable {
		 private static final long serialVersionUID = 1L;
		 private SacSolicitudServicio solicitud;
		 private SacDetalleSolicitudServicio detalleSolicitud;
			private List<AdmCliente> listaCliente;
			private List<AdmAgencia> listaAgencia;
			private int idCliente;
			private int idAgenciaOrigen;
			private int idAgenciaDestino;
			private int idCiudad;
			private List<AdmDetalleCatalogo> listaCiudad;
			
			private Timestamp fechaActual;
			
			
			
			float totalDenominacion;
			
		 
		 	@EJB
		    private ServicioAdmAgencia servicioAgencia;
		 	@EJB
		    private ServicioSolicitud servicioSolicitud;
		 	@EJB
		    private ServicioDetalleSolicitud serviciodetalleSolicitud;
		 	@EJB
		    private ServicioAdmCliente servicioCliente;
		    @EJB
		    private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
		    
		   
		    @PostConstruct
		    public void init() {
		        cancelar();
		        consultaListaCiudad();
		       
		        
		       
		       
		    }
		    
		    
		    
		    public void guardarSolicitud(){
		    	Calendar cali = Calendar.getInstance();
				fechaActual = new Timestamp(cali.getTimeInMillis());
		    	
		    	
		    
		    	
		    	
		    	if((solicitud.getMonto() != totalDenominacion) || solicitud.getMonto() == 0.0 || totalDenominacion == 0.0 ){
		    		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Detalle no coincide con el monto solicitado"));
		    		
		    		
		    	} else {
		    		AdmAgencia ao = new AdmAgencia ();
		    		ao.setIdAgencia(idAgenciaOrigen);
		    		
		    		AdmAgencia ad = new AdmAgencia ();
		    		ad.setIdAgencia(idAgenciaOrigen);
		    		
		    		solicitud.setIdSolicitudServicio(servicioSolicitud.getPK());
		    		solicitud.setIdAgenciaOrigen(ao);
		    		solicitud.setIdAgenciaDestino(ad);
		    		solicitud.setFechaCreacion(fechaActual);
		    		
			    	servicioSolicitud.create(solicitud);
			    	
			    	guardarDeatlleSolicitud();
			    	

		    		
		    		/////////
		    	
		    	
		    	
		    	}
		    	
		    	
		    }
		    
		    
		    public void guardarDeatlleSolicitud(){ // guardo el detalle de las denominaciones de la solicitud
		    	SacDetalleSolicitudServicio detalle;
		    	List <SacDetalleSolicitudServicio> lista = new ArrayList<>();
		    	
		    	
		    	
	    		 try {
	    			 SacSolicitudServicio idSolicitud = new SacSolicitudServicio ();
	    			 idSolicitud = solicitud;
	    			 
	    			 
	    			 for(SacDetalleSolicitudServicio item :lista){
	    				 
	 	    			detalleSolicitud.setIdDetalleSolicutudServicio(serviciodetalleSolicitud.getPK());
	 	 		    	detalleSolicitud.setIdSolicitudServicio(idSolicitud); // se crea una consulta adicional
	 	 		    	detalleSolicitud.setCantidad(item.getCantidad());
	 	 		    	detalleSolicitud.setSubtotal(item.getSubtotal());
	 	 		    	detalleSolicitud.setIdEspecie(item.getIdEspecie());
	 	 		    	
	 	 		    	
	 	 		    	serviciodetalleSolicitud.create(detalleSolicitud);
	 	    			
	 	    			 
	 	    		 }
	    			 //FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos guardados con exito"));
	    			 RequestContext.getCurrentInstance().execute("PF('dlgMsg').show();");
	 	    		 limpiar();
	 	    		 
	 		    	
	    			 
	    			 
					
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Se ha producido un error al guardar " + e));
				}
	    		 
	    		 
	    		
		    	
		    	
		    	
		    }
		    
		    
		    public void limpiar(){
		    	//variables billtes cantidad

				
				 totalDenominacion= 0;
		    	
		    	
		    }
		   
 
 
 

		    
		    public void consultaListaCiudad() {
				AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("usuario"); // obtengo el usuario logueado
				listaCiudad = new ArrayList<>();
				listaCiudad = servicioDetalleCatalogo.ciudadesAsignadas(usuario);
				
				
			}
		    
		    public void cancelar(){
		    	
		    	solicitud = new SacSolicitudServicio();
		    	detalleSolicitud = new SacDetalleSolicitudServicio();
		    	idAgenciaOrigen = -1;
		    	idAgenciaDestino = -1;
		    	idCiudad = -1;
		    			
		    	
		    	
		    }
		    
		    
		    public void consultaListaCliente() {
				listaCliente = new ArrayList<>();
				listaCliente = servicioCliente.buscaClientePadre();

			}

		    
		    public void consultaListaAgencia() {

		    	AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
				listaAgencia = new ArrayList<>();
				listaAgencia = servicioAgencia.buscaAgenciaCiudad(idCiudad, usuario);
				

			}

			public SacSolicitudServicio getSolicitud() {
				return solicitud;
			}

			public void setSolicitud(SacSolicitudServicio solicitud) {
				this.solicitud = solicitud;
			}

			public List<AdmCliente> getListaCliente() {
				return listaCliente;
			}

			public void setListaCliente(List<AdmCliente> listaCliente) {
				this.listaCliente = listaCliente;
			}

			public List<AdmAgencia> getListaAgencia() {
				return listaAgencia;
			}

			public void setListaAgencia(List<AdmAgencia> listaAgencia) {
				this.listaAgencia = listaAgencia;
			}

			public int getIdCliente() {
				return idCliente;
			}

			public void setIdCliente(int idCliente) {
				this.idCliente = idCliente;
			}

			

			public int getIdAgenciaOrigen() {
				return idAgenciaOrigen;
			}



			public void setIdAgenciaOrigen(int idAgenciaOrigen) {
				this.idAgenciaOrigen = idAgenciaOrigen;
			}



			public int getIdAgenciaDestino() {
				return idAgenciaDestino;
			}



			public void setIdAgenciaDestino(int idAgenciaDestino) {
				this.idAgenciaDestino = idAgenciaDestino;
			}



			public int getIdCiudad() {
				return idCiudad;
			}

			public void setIdCiudad(int idCiudad) {
				this.idCiudad = idCiudad;
			}

			public List<AdmDetalleCatalogo> getListaCiudad() {
				return listaCiudad;
			}

			public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
				this.listaCiudad = listaCiudad;
			}

			public SacDetalleSolicitudServicio getDetalleSolicitud() {
				return detalleSolicitud;
			}

			public void setDetalleSolicitud(SacDetalleSolicitudServicio detalleSolicitud) {
				this.detalleSolicitud = detalleSolicitud;
			}

		

			public float getTotalDenominacion() {
				return totalDenominacion;
			}

			public void setTotalDenominacion(float totalDenominacion) {
				this.totalDenominacion = totalDenominacion;
			}

			

			
			
			
			
		    
		    
		    
}


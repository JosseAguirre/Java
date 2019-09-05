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
public class ConsultaSolicitudBean implements Serializable {
		 private static final long serialVersionUID = 1L;
		 private SacSolicitudServicio solicitud;
		 private SacDetalleSolicitudServicio detalleSolicitud;
			private List<AdmCliente> listaCliente;
			private List<AdmAgencia> listaAgencia;
			private int idCliente;
			private int idAgencia;
			private int idCiudad;
			private List<AdmDetalleCatalogo> listaCiudad;
			
			private Timestamp fechaActual;
			
			//variables billtes cantidad
			int b100;
			int b50;
			int b20;
			int b10;
			int b5;
			int b1;

			//variables monedas cantidad
			
			int m1;
			int m50;
			int m25;
			int m10;
			int m5;
			int m1c;
			
			//subtotal billetes
			int subb100;
			int subb50;
			int subb20;
			int subb10;
			int subb5;
			int subb1;
			
			int totalBillete;
			
			
			
			//subtotal monedas
			int subm1;
			float subm50;
			float subm25;
			float subm10;
			float subm5;
			float subm1c;
			float totalMoneda;
			
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
		    		AdmAgencia ag = new AdmAgencia ();
		    		ag.setIdAgencia(idAgencia);
		    		
		    		solicitud.setIdSolicitudServicio(servicioSolicitud.getPK());
		    		solicitud.setIdAgenciaDestino(ag);
		    		solicitud.setFechaCreacion(fechaActual);
		    		solicitud.setTipoServicio("ATM");
		    		
			    	servicioSolicitud.create(solicitud);
			    	
			    	guardarDeatlleSolicitud();
			    	

		    		
		    		/////////
		    	
		    	
		    	
		    	}
		    	
		    	
		    }
		    
		    
		    public void guardarDeatlleSolicitud(){ // guardo el detalle de las denominaciones de la solicitud
		    	SacDetalleSolicitudServicio detalle;
		    	List <SacDetalleSolicitudServicio> lista = new ArrayList<>();
		    	
		    	
		    	
	    		//billetes
	    		detalle = new SacDetalleSolicitudServicio();
	    		detalle.setIdEspecie(1);
	    		detalle.setCantidad(b100);
	    		detalle.setSubtotal(subb100);
	    		lista.add(detalle);
	    		
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(2);
	    		 detalle.setCantidad(b50);
	    		 detalle.setSubtotal(subb50);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(3);
	    		 detalle.setCantidad(b20);
	    		 detalle.setSubtotal(subb20);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(4);
	    		 detalle.setCantidad(b10);
	    		 detalle.setSubtotal(subb10);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(5);
	    		 detalle.setCantidad(b5);
	    		 detalle.setSubtotal(subb5);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(6);
	    		 detalle.setCantidad(b1);
	    		 detalle.setSubtotal(subb1);
	    		 lista.add(detalle);
	    		 
	    		 
	    		////monedas 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(7);
	    		 detalle.setCantidad(m1);
	    		 detalle.setSubtotal(subm1);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(8);
	    		 detalle.setCantidad(m50);
	    		 detalle.setSubtotal(subm50);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(9);
	    		 detalle.setCantidad(m25);
	    		 detalle.setSubtotal(subm25);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(10);
	    		 detalle.setCantidad(m10);
	    		 detalle.setSubtotal(subm10);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(11);
	    		 detalle.setCantidad(m5);
	    		 detalle.setSubtotal(subm5);
	    		 lista.add(detalle);
	    		 
	    		 detalle = new SacDetalleSolicitudServicio();
	    		 detalle.setIdEspecie(12);
	    		 detalle.setCantidad(m1c);
	    		 detalle.setSubtotal(subm1c);
	    		 lista.add(detalle);
	    
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
				 b100 = 0;
				 b50 = 0;
				 b20 = 0;
				 b10 = 0;
				 b5 = 0;
				 b1 = 0;
				
				
			
				//variables monedas cantidad
				
				 m1 = 0;
				 m50 = 0;
				 m25 = 0;
				 m10 = 0;
				 m5 = 0;
				 m1c = 0;
				
				//subtotal billetes
				 subb100 = 0;
				 subb50 = 0;
				 subb20 = 0;
				 subb10 = 0;
				 subb5 = 0;
				 subb1= 0;
				
				 totalBillete= 0;
				
				
				
				//subtotal monedas
				 subm1 = 0;
				 subm50= 0;
				 subm25= 0;
				 subm10= 0;
				 subm5= 0;
				 subm1c= 0;
				 totalMoneda= 0;
				
				 totalDenominacion= 0;
		    	
		    	
		    }
		    public void subTotalBillete(){
		    	subb100 = b100 * 100;
		    	subb50 = b50 * 50;
		    	subb20 = b20 * 20;
		    	subb10 = b10 * 10;
		    	subb5 = b5 * 5;
		    	subb1 = b1 * 1;
		    	totalBillete = subb100 +  subb50 + subb20 + subb10 +  subb5 + subb1 ;
		    	totalDenominacion = totalBillete + totalMoneda;
		    	
		    	
		    	
		    	
		    	
		     
		    	
		    	
		    }
		    
 public void subTotalMoneda(){
	 subm1 = m1 * 1;
 	subm50 = (float) (m50 * 0.50);
 	subm25 = (float) (m25 * 0.25) ;
 	subm10 = (float) (m10 * 0.10);
 	subm5 = (float) (m5 * 0.05);
 	subm1c = (float) (m1c * 0.01);
 	
 	totalMoneda = subm1 +  subm50 + subm25 + subm10 +  subm5 + subm1c ;
 	totalDenominacion = totalBillete + totalMoneda;
		    	
		    	
		    }
 
 
 public void total(){
	 
	 
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
		    	idAgencia = -1;
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

			public int getIdAgencia() {
				return idAgencia;
			}

			public void setIdAgencia(int idAgencia) {
				this.idAgencia = idAgencia;
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

			public int getB100() {
				return b100;
			}

			public void setB100(int b100) {
				this.b100 = b100;
			}

			public int getB50() {
				return b50;
			}

			public void setB50(int b50) {
				this.b50 = b50;
			}

			public int getB20() {
				return b20;
			}

			public void setB20(int b20) {
				this.b20 = b20;
			}

			public int getB10() {
				return b10;
			}

			public void setB10(int b10) {
				this.b10 = b10;
			}

			public int getB5() {
				return b5;
			}

			public void setB5(int b5) {
				this.b5 = b5;
			}

			public int getB1() {
				return b1;
			}

			public void setB1(int b1) {
				this.b1 = b1;
			}

			

			

			public int getM50() {
				return m50;
			}

			public void setM50(int m50) {
				this.m50 = m50;
			}

			public int getM25() {
				return m25;
			}

			public void setM25(int m25) {
				this.m25 = m25;
			}

			public int getM10() {
				return m10;
			}

			public void setM10(int m10) {
				this.m10 = m10;
			}

			public int getM5() {
				return m5;
			}

			public void setM5(int m5) {
				this.m5 = m5;
			}

			public int getM1c() {
				return m1c;
			}

			public void setM1c(int m1c) {
				this.m1c = m1c;
			}

			public int getSubb100() {
				return subb100;
			}

			public void setSubb100(int subb100) {
				this.subb100 = subb100;
			}

			public int getSubb50() {
				return subb50;
			}

			public void setSubb50(int subb50) {
				this.subb50 = subb50;
			}

			public int getSubb20() {
				return subb20;
			}

			public void setSubb20(int subb20) {
				this.subb20 = subb20;
			}

			public int getSubb10() {
				return subb10;
			}

			public void setSubb10(int subb10) {
				this.subb10 = subb10;
			}

			public int getSubb5() {
				return subb5;
			}

			public void setSubb5(int subb5) {
				this.subb5 = subb5;
			}

			public int getSubb1() {
				return subb1;
			}

			public void setSubb1(int subb1) {
				this.subb1 = subb1;
			}

			public int getSubm1() {
				return subm1;
			}

			public void setSubm1(int subm1) {
				this.subm1 = subm1;
			}

			public int getM1() {
				return m1;
			}

			public void setM1(int m1) {
				this.m1 = m1;
			}

			public double getSubm50() {
				return subm50;
			}

			public int getTotalBillete() {
				return totalBillete;
			}

			public void setTotalBillete(int totalBillete) {
				this.totalBillete = totalBillete;
			}

			public float getSubm25() {
				return subm25;
			}

			public void setSubm25(float subm25) {
				this.subm25 = subm25;
			}

			public float getSubm10() {
				return subm10;
			}

			public void setSubm10(float subm10) {
				this.subm10 = subm10;
			}

			public float getSubm5() {
				return subm5;
			}

			public void setSubm5(float subm5) {
				this.subm5 = subm5;
			}

			public float getSubm1c() {
				return subm1c;
			}

			public void setSubm1c(float subm1c) {
				this.subm1c = subm1c;
			}

			public float getTotalMoneda() {
				return totalMoneda;
			}

			public void setTotalMoneda(float totalMoneda) {
				this.totalMoneda = totalMoneda;
			}

			public void setSubm50(float subm50) {
				this.subm50 = subm50;
			}

			public float getTotalDenominacion() {
				return totalDenominacion;
			}

			public void setTotalDenominacion(float totalDenominacion) {
				this.totalDenominacion = totalDenominacion;
			}

			

			
			
			
			
		    
		    
		    
}


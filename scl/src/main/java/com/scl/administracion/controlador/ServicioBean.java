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

import com.scl.administracion.modelo.AdmServicio;
import com.scl.administracion.servicio.ServicioAdmServicio;
import com.scl.operacion.modelo.*;



@ManagedBean
@ViewScoped
public class ServicioBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	    private AdmServicio servicio;
	    private AdmServicio servicioSeleccionado;
	    private List<AdmServicio> listaServicio;
	    private List<AdmServicio> servicioFiltrado;
	    private boolean bandera;

	    @EJB
	    private ServicioAdmServicio servicioServicio;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	        consultalistaServicio();
	      
	       

	       
	    }

	    //metodo que instancia nuevos objetos de una clase
	     
	    public void cancelar() {
	    	servicio = new AdmServicio();
	        bandera = false;

	    }


	    //metodo que guarda un usuario nuevo
	     
	    public void guardar() {

	        try {
	        	servicio.setIdServicio(servicioServicio.getPK());
	       
	        	servicioServicio.create(servicio);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "Aviso", "Se ha guardado don exito "));
	            consultalistaServicio();
	            cancelar();
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

	        }

	    }


	    //metodo para eliminar un usuario 
	     
	    public void eliminar() {
	    	if(servicioSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {	
				servicio = servicioSeleccionado;
			
				servicioServicio.update(servicio);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
			consultalistaServicio();
			cancelar();		
			}

	    }
	    
	    public void actualizar() {
		

			servicioServicio.update(servicio);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Actualizado Correctamente "));
			cancelar();
			
		}
		
		public void modificar(){
			if(servicioSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {
			bandera =	true;
			servicio = servicioSeleccionado;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosservicio').show();");
			}
		}
		
		public void nuevo(){	
			bandera =	false;
			servicio = new AdmServicio();
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosservicio').show();");
			
		}
		
		public static void resetarFormulario() {
	        RequestContext.getCurrentInstance().reset("frmCrear");
		}

  
	   

	    //metodo que llena una lista con los registros provenientes d ela base de datos
	     
	    public void consultalistaServicio() {

	        listaServicio = new ArrayList<>(); // Creo una lista para mostrar todo los usuarios en el datatable
	        listaServicio = servicioServicio.findAll();

	        
	    }
	    
	  //metodo que verifica si la accion es de guardar nuevo usuario o actualizar uno ya existente
	    public void persistir() {

	        if (bandera == true) {

	            actualizar();

	        } else {
	            guardar();

	        }
	    }




}






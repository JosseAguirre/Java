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
import org.primefaces.context.RequestContext;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.ServicioEstadoTransaccion;

@ManagedBean
@ViewScoped
public class EstadoTransaccionBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	    private OpeEstadoTransaccion estadoTransaccion;
	    private OpeEstadoTransaccion estadoSeleccionado;
	    private List<OpeEstadoTransaccion> listaEstado;
	    private List<OpeEstadoTransaccion> estadoFiltrado;
	    private boolean bandera;

	    @EJB
	    private ServicioEstadoTransaccion servicioEstado;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	        consultaListaEstado();
	      
	       

	       
	    }

	    //metodo que instancia nuevos objetos de una clase
	     
	    public void cancelar() {
	    	estadoTransaccion = new OpeEstadoTransaccion();
	        bandera = false;

	    }


	    //metodo que guarda un usuario nuevo
	     
	    public void guardar() {

	        try {
	        	estadoTransaccion.setIdEstadoTransaccion(servicioEstado.getPK());
	       
	        	servicioEstado.create(estadoTransaccion);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "Aviso", "Se ha guardado don exito "));
	            consultaListaEstado();
	            cancelar();
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

	        }

	    }


	    //metodo para eliminar un usuario 
	     
	    public void eliminar() {
	    	if(estadoSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {	
				estadoTransaccion = estadoSeleccionado;
			
				servicioEstado.update(estadoTransaccion);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
			consultaListaEstado();
			cancelar();		
			}

	    }
	    
	    public void actualizar() {
		

			servicioEstado.update(estadoTransaccion);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Actualizado Correctamente "));
			cancelar();
			
		}
		
		public void modificar(){
			if(estadoSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {
			bandera =	true;
			estadoTransaccion = estadoSeleccionado;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosEstado').show();");
			}
		}
		
		public void nuevo(){	
			bandera =	false;
			estadoTransaccion = new OpeEstadoTransaccion();
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosEstado').show();");
			
		}
		
		public static void resetarFormulario() {
	        RequestContext.getCurrentInstance().reset("frmCrear");
		}

  
	   

	    //metodo que llena una lista con los registros provenientes d ela base de datos
	     
	    public void consultaListaEstado() {

	        listaEstado = new ArrayList<>(); // Creo una lista para mostrar todo los usuarios en el datatable
	        listaEstado = servicioEstado.findAll();

	        
	    }
	    
	  //metodo que verifica si la accion es de guardar nuevo usuario o actualizar uno ya existente
	    public void persistir() {

	        if (bandera == true) {

	            actualizar();

	        } else {
	            guardar();

	        }
	    }

		public OpeEstadoTransaccion getEstadoTransaccion() {
			return estadoTransaccion;
		}

		public void setEstadoTransaccion(OpeEstadoTransaccion estadoTransaccion) {
			this.estadoTransaccion = estadoTransaccion;
		}

		public OpeEstadoTransaccion getEstadoSeleccionado() {
			return estadoSeleccionado;
		}

		public void setEstadoSeleccionado(OpeEstadoTransaccion estadoSeleccionado) {
			this.estadoSeleccionado = estadoSeleccionado;
		}

		public List<OpeEstadoTransaccion> getListaEstado() {
			return listaEstado;
		}

		public void setListaEstado(List<OpeEstadoTransaccion> listaEstado) {
			this.listaEstado = listaEstado;
		}

		public List<OpeEstadoTransaccion> getEstadoFiltrado() {
			return estadoFiltrado;
		}

		public void setEstadoFiltrado(List<OpeEstadoTransaccion> estadoFiltrado) {
			this.estadoFiltrado = estadoFiltrado;
		}
	    
	    


}






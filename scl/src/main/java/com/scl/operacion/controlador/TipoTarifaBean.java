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
import com.scl.operacion.servicio.ServicioTipoTarifa;


@ManagedBean
@ViewScoped
public class TipoTarifaBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	    private OpeTipoTarifa tipoTarifa;
	    private OpeTipoTarifa tipoTarifaSeleccionado;
	    private List<OpeTipoTarifa> listaTipoTarifa;
	    private List<OpeTipoTarifa> tipoTarifaFiltrado;
	    private boolean bandera;

	    @EJB
	    private ServicioTipoTarifa servicioTipoTarifa;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	        consultaListaTipoTarifa();
	      
	       

	       
	    }

	    //metodo que instancia nuevos objetos de una clase
	     
	    public void cancelar() {
	    	tipoTarifa = new OpeTipoTarifa();
	        bandera = false;

	    }


	    //metodo que guarda un usuario nuevo
	     
	    public void guardar() {

	        try {
	        	tipoTarifa.setIdTipoTarifa(servicioTipoTarifa.getPK());
	       
	        	servicioTipoTarifa.create(tipoTarifa);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "Aviso", "Se ha guardado don exito "));
	            consultaListaTipoTarifa();
	            cancelar();
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

	        }

	    }


	    //metodo para eliminar un usuario 
	     
	    public void eliminar() {
	    	if(tipoTarifaSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {	
				tipoTarifa = tipoTarifaSeleccionado;
			
				servicioTipoTarifa.update(tipoTarifa);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
			consultaListaTipoTarifa();
			cancelar();		
			}

	    }
	    
	    public void actualizar() {
		

			servicioTipoTarifa.update(tipoTarifa);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Actualizado Correctamente "));
			cancelar();
			
		}
		
		public void modificar(){
			if(tipoTarifaSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {
			bandera =	true;
			tipoTarifa = tipoTarifaSeleccionado;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosTipoTarifa').show();");
			}
		}
		
		public void nuevo(){	
			bandera =	false;
			tipoTarifa = new OpeTipoTarifa();
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosTipoTarifa').show();");
			
		}
		
		public static void resetarFormulario() {
	        RequestContext.getCurrentInstance().reset("frmCrear");
		}

  
	   

	    //metodo que llena una lista con los registros provenientes d ela base de datos
	     
	    public void consultaListaTipoTarifa() {

	        listaTipoTarifa = new ArrayList<>(); // Creo una lista para mostrar todo los usuarios en el datatable
	        listaTipoTarifa = servicioTipoTarifa.findAll();

	        
	    }
	    
	  //metodo que verifica si la accion es de guardar nuevo usuario o actualizar uno ya existente
	    public void persistir() {

	        if (bandera == true) {

	            actualizar();

	        } else {
	            guardar();

	        }
	    }

		public OpeTipoTarifa getTipoTarifa() {
			return tipoTarifa;
		}

		public void setTipoTarifa(OpeTipoTarifa tipoTarifa) {
			this.tipoTarifa = tipoTarifa;
		}

		public OpeTipoTarifa getTipoTarifaSeleccionado() {
			return tipoTarifaSeleccionado;
		}

		public void setTipoTarifaSeleccionado(OpeTipoTarifa tipoTarifaSeleccionado) {
			this.tipoTarifaSeleccionado = tipoTarifaSeleccionado;
		}

		public List<OpeTipoTarifa> getListaTipoTarifa() {
			return listaTipoTarifa;
		}

		public void setListaTipoTarifa(List<OpeTipoTarifa> listaTipoTarifa) {
			this.listaTipoTarifa = listaTipoTarifa;
		}

		public List<OpeTipoTarifa> getTipoTarifaFiltrado() {
			return tipoTarifaFiltrado;
		}

		public void setTipoTarifaFiltrado(List<OpeTipoTarifa> tipoTarifaFiltrado) {
			this.tipoTarifaFiltrado = tipoTarifaFiltrado;
		}

	    


}






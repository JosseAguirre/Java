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
import com.scl.operacion.servicio.ServicioCategoriaVehiculo;
import com.scl.operacion.servicio.ServicioSeguroVehiculo;



@ManagedBean
@ViewScoped
public class SeguroVehiculoBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	    private OpeSeguroVehiculo seguroVehiculo;
	    private OpeSeguroVehiculo seguroVehiculoSeleccionado;
	    private List<OpeSeguroVehiculo> listaSeguroVehiculo;
	    private List<OpeSeguroVehiculo> seguroVehiculoFiltrado;
	    
	    private OpeCategoriaVehiculo categoriaVehiculo;
	    private List<OpeCategoriaVehiculo> listaCategoriaVehiculo;
	   private int idCategoria;
	    private boolean bandera;

	    @EJB
	    private ServicioSeguroVehiculo servicioSeguroVehiculo;
	    
	    @EJB
	    private ServicioCategoriaVehiculo servicioCategoriaVehiculo;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	        consultaListaseguroVehiculo();
	        consultaListaCategoria();
	      
	       

	       
	    }

	    //metodo que instancia nuevos objetos de una clase
	     
	    public void cancelar() {
	    	seguroVehiculo = new OpeSeguroVehiculo();
	    	categoriaVehiculo = new OpeCategoriaVehiculo();
	        bandera = false;
	        idCategoria = -1;

	    }


	    //metodo que guarda un usuario nuevo
	     
	    public void guardar() {

	        try {
	        	seguroVehiculo.setIdSeguroVehiculo(servicioSeguroVehiculo.getPK());
	        	categoriaVehiculo = servicioCategoriaVehiculo.findOne(idCategoria);
	        	seguroVehiculo.setIdCategoriaVehiculo(categoriaVehiculo);
	        	servicioSeguroVehiculo.create(seguroVehiculo);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "Aviso", "Se ha guardado don exito "));
	            consultaListaseguroVehiculo();
	            cancelar();
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

	        }

	    }


	    //metodo para eliminar un usuario 
	     
	    public void eliminar() {
	    	if(seguroVehiculoSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {	
				seguroVehiculo = seguroVehiculoSeleccionado;
			
				servicioSeguroVehiculo.update(seguroVehiculo);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
			consultaListaseguroVehiculo();
			cancelar();		
			}

	    }
	    
	    public void actualizar() {
		

			servicioSeguroVehiculo.update(seguroVehiculo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Actualizado Correctamente "));
			cancelar();
			
		}
		
		public void modificar(){
			if(seguroVehiculoSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {
			bandera =	true;
			seguroVehiculo = seguroVehiculoSeleccionado;
			idCategoria = seguroVehiculo.getIdCategoriaVehiculo().getIdCategoriaVehiculo();
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosSeguroVehiculo').show();");
			}
		}
		
		public void nuevo(){	
			bandera =	false;
			seguroVehiculo = new OpeSeguroVehiculo();
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosSeguroVehiculo').show();");
			
		}
		
		public static void resetarFormulario() {
	        RequestContext.getCurrentInstance().reset("frmCrear");
		}

  
	   

	    //metodo que llena una lista con los registros provenientes d ela base de datos
	     
	    public void consultaListaseguroVehiculo() {

	        listaSeguroVehiculo = new ArrayList<>(); // Creo una lista para mostrar todo los usuarios en el datatable
	        listaSeguroVehiculo = servicioSeguroVehiculo.findAll();

	        
	    }
	    
	    
	    public void consultaListaCategoria() {		
			listaCategoriaVehiculo = new ArrayList<>();
			listaCategoriaVehiculo = servicioCategoriaVehiculo.findAll();
		}
	    
	    
	  //metodo que verifica si la accion es de guardar nuevo usuario o actualizar uno ya existente
	    public void persistir() {

	        if (bandera == true) {

	            actualizar();

	        } else {
	            guardar();

	        }
	    }

		public OpeSeguroVehiculo getSeguroVehiculo() {
			return seguroVehiculo;
		}

		public void setSeguroVehiculo(OpeSeguroVehiculo seguroVehiculo) {
			this.seguroVehiculo = seguroVehiculo;
		}

		public OpeSeguroVehiculo getSeguroVehiculoSeleccionado() {
			return seguroVehiculoSeleccionado;
		}

		public void setSeguroVehiculoSeleccionado(OpeSeguroVehiculo seguroVehiculoSeleccionado) {
			this.seguroVehiculoSeleccionado = seguroVehiculoSeleccionado;
		}

		public List<OpeSeguroVehiculo> getListaSeguroVehiculo() {
			return listaSeguroVehiculo;
		}

		public void setListaSeguroVehiculo(List<OpeSeguroVehiculo> listaSeguroVehiculo) {
			this.listaSeguroVehiculo = listaSeguroVehiculo;
		}

		public List<OpeSeguroVehiculo> getSeguroVehiculoFiltrado() {
			return seguroVehiculoFiltrado;
		}

		public void setSeguroVehiculoFiltrado(List<OpeSeguroVehiculo> seguroVehiculoFiltrado) {
			this.seguroVehiculoFiltrado = seguroVehiculoFiltrado;
		}

		public OpeCategoriaVehiculo getCategoriaVehiculo() {
			return categoriaVehiculo;
		}

		public void setCategoriaVehiculo(OpeCategoriaVehiculo categoriaVehiculo) {
			this.categoriaVehiculo = categoriaVehiculo;
		}

		public List<OpeCategoriaVehiculo> getListaCategoriaVehiculo() {
			return listaCategoriaVehiculo;
		}

		public void setListaCategoriaVehiculo(List<OpeCategoriaVehiculo> listaCategoriaVehiculo) {
			this.listaCategoriaVehiculo = listaCategoriaVehiculo;
		}

		public int getIdCategoria() {
			return idCategoria;
		}

		public void setIdCategoria(int idCategoria) {
			this.idCategoria = idCategoria;
		}

	
	    


}






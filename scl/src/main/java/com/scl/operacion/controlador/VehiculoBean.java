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

import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.*;


@ManagedBean
@ViewScoped
public class VehiculoBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	    private  OpeVehiculo vehiculo;
	    private OpeVehiculo vehiculoSeleccionado;
	    private List<OpeVehiculo> listaVehiculo;
	    private List<OpeVehiculo> vehiculoFiltrado;
	    private List<AdmDetalleCatalogo> listaCiudad;
	    private List<AdmDetalleCatalogo> listaCiudadTodas;
	    private List<OpeCategoriaVehiculo> listaCategoria;
	    int idCiudad;
	    int idCiudadT;
	    int idCategoria;
	   

	    @EJB
	    private ServicioVehiculo servicioVehiculo;
	    
	    @EJB
	    private ServicioCategoriaVehiculo servicioCategoria;
	    
	    @EJB
		private ServicioAdmDetalleCatalogo servicioDetalleCatalogo ;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	       
	        consultaListaCiudad();
	      
	       

	       
	    }

	    //metodo que instancia nuevos objetos de una clase
	     
	    public void cancelar() {
	    	vehiculo = new OpeVehiculo();
	    	 idCiudad = -1;
	    	 idCiudadT = -1;
		     idCategoria = -1;

	    }


	    //metodo que guarda un usuario nuevo
	     
	    public void guardar() {
	    	
	    	
	    	AdmDetalleCatalogo ciudad = new AdmDetalleCatalogo();
	    	ciudad.setIdDetalleCatalogo(idCiudadT);
	    	
	    	OpeCategoriaVehiculo categoria = new OpeCategoriaVehiculo();
	    	categoria.setIdCategoriaVehiculo(idCategoria);

	        try {
	        	vehiculo.setIdVehiculo(servicioVehiculo.getPK());
	        	vehiculo.setIdCiudadDc(ciudad);
	        	vehiculo.setIdCategoriaVehiculo(categoria);
	        	servicioVehiculo.create(vehiculo);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "Aviso", "Se ha guardado don exito "));
	            consultalistaVehiculo();
	            cancelar();
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

	        }
	    	

	    }


	    //metodo para eliminar un usuario 
	     
	   /* public void eliminar() {
	    	if(vehiculoSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {	
				vehiculo = vehiculoSeleccionado;
			
				servicioVehiculo.update(vehiculo);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
			consultalistaVehiculo();
			cancelar();		
			}

	    }*/
	    
	    public void actualizar() {
		

			servicioVehiculo.update(vehiculo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Actualizado Correctamente "));
			cancelar();
			
		}
	    
	    
		
		public void modificar(){
			if(vehiculoSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {
			
			vehiculo = vehiculoSeleccionado;
			conultaListas();
			
			
			RequestContext.getCurrentInstance().execute("PF('dlgVehiculo').show();");
			}
		}
		
		public void nuevo(){	
			
			vehiculo = new OpeVehiculo();
			conultaListas();
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgVehiculo').show();");
			
		}
		
		public static void resetarFormulario() {
	        RequestContext.getCurrentInstance().reset("frmCrear");
		}

		
		public void conultaListas(){ // consulta toda las listas 
			
			consultaListaCategoria();
			consultaListaCiudadTodas();
			
			
		}
  
		
		public void consultaListaCiudad() {
			AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
				listaCiudad = new ArrayList<>();
				listaCiudad = servicioDetalleCatalogo.ciudadesAsignadas(usuario);
			}
	   

	   
	     
	    public void consultalistaVehiculo() {

	        listaVehiculo = new ArrayList<>(); //
	        listaVehiculo = servicioVehiculo.buscarVehiculosCiudad(idCiudad);

	        
	    }
	    
	    public void consultaListaCategoria() {

	        listaCategoria = new ArrayList<>(); //
	        listaCategoria = servicioCategoria.findAll();

	        
	    }
	    
	    
	    public void consultaListaCiudadTodas() {
	    	setListaCiudadTodas(new ArrayList<>()); 
	    	setListaCiudadTodas(servicioDetalleCatalogo.ciudades());
	    	
	    }
	    
	    
	    

		public OpeVehiculo getVehiculo() {
			return vehiculo;
		}

		public void setVehiculo(OpeVehiculo vehiculo) {
			this.vehiculo = vehiculo;
		}

		public OpeVehiculo getVehiculoSeleccionado() {
			return vehiculoSeleccionado;
		}

		public void setVehiculoSeleccionado(OpeVehiculo vehiculoSeleccionado) {
			this.vehiculoSeleccionado = vehiculoSeleccionado;
		}

		public List<OpeVehiculo> getListaVehiculo() {
			return listaVehiculo;
		}

		public void setListaVehiculo(List<OpeVehiculo> listaVehiculo) {
			this.listaVehiculo = listaVehiculo;
		}

		public List<OpeVehiculo> getVehiculoFiltrado() {
			return vehiculoFiltrado;
		}

		public void setVehiculoFiltrado(List<OpeVehiculo> vehiculoFiltrado) {
			this.vehiculoFiltrado = vehiculoFiltrado;
		}

		public int getIdCiudad() {
			return idCiudad;
		}

		public void setIdCiudad(int idCiudad) {
			this.idCiudad = idCiudad;
		}

		public int getIdCategoria() {
			return idCategoria;
		}

		public void setIdCategoria(int idCategoria) {
			this.idCategoria = idCategoria;
		}

		public List<AdmDetalleCatalogo> getListaCiudad() {
			return listaCiudad;
		}

		public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
			this.listaCiudad = listaCiudad;
		}

		public List<OpeCategoriaVehiculo> getListaCategoria() {
			return listaCategoria;
		}

		public void setListaCategoria(List<OpeCategoriaVehiculo> listaCategoria) {
			this.listaCategoria = listaCategoria;
		}

		public List<AdmDetalleCatalogo> getListaCiudadTodas() {
			return listaCiudadTodas;
		}

		public void setListaCiudadTodas(List<AdmDetalleCatalogo> listaCiudadTodas) {
			this.listaCiudadTodas = listaCiudadTodas;
		}

		public int getIdCiudadT() {
			return idCiudadT;
		}

		public void setIdCiudadT(int idCiudadT) {
			this.idCiudadT = idCiudadT;
		}
	    
		
	    
	    
	 

}






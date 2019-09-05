package com.bivi.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.bivi.modelos.*;
import com.bivi.servicios.*;

import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class AdmUsuarioBean implements  Serializable {

    private static final long serialVersionUID = 1L;
    private AdmUsuario admusuario;
    private AdmUsuario usuarioseleccionado;
    
    private List<AdmUsuario> listausuario;
    private List<AdmUsuario> usuariofiltrado;  
    
    private List<AdmEmpleado> listaempleado;
    private AdmEmpleado admempleado;
    
  
    private int idCliente;
    private List<AdmCliente> listaCliente;
	private List<AdmAgencia> listaAgencia;
    
    private List<AdmRol> listaRol;
    private List<AdmRolUsuario> rolesAsignados;
    private List<AdmRolUsuario> rolesSeleccionados;
    
    private List<AdmUsuarioAgencia> agenciasSeleccionadas;
    private List<AdmUsuarioAgencia> agenciasAsignadas;
    
    private AdmUsuarioCiudad usuarioCiudad;
    private AdmUsuarioAgencia usuarioAgencia;
    
    private List<AdmUsuarioCiudad> ciudadesAsignadas;
    private List<AdmUsuarioCiudad> ciudadesSeleccionadas;
    
    private AdmRolUsuario admRolUsuario;
    
	private List<AdmDetalleCatalogo> listaCiudad;
    
    private Date date;
    private int idempleado;
    private int idestado;
    private int idRol;
    private int idAgencia;
    private int idCiudadA;
    private int idCiudadC;
    private int ciudad;
    private int ciudadC;
    private int idUsuario;
    
    
    
    private boolean bandera;

    @EJB
    private ServicioAdmUsuario serviciousuario;//
    @EJB
    private ServicioAdmEmpleado servicioempleado;//
    @EJB
	private ServicioAdmRol servicioRol;//
    @EJB
    private ServicioAdmRolUsuario servicioRolUsuario;//
    @EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;//
    @EJB
	private ServicioAdmAgencia servicioAgencia;//
    @EJB
	private ServicioAdmCliente servicioCliente;//
    @EJB
   	private ServicioUsuarioCiudad servicioUsuarioCiudad;//
    @EJB
    private ServicioUsuarioAgencia servicioUsuarioAgencia;
    

     
    @PostConstruct
    public void init() {
        cancelar();
        consultaListaUsuarios();
        consultaListaCombos();
        setDate(new Date());
        
       
		consultaRoles();
		consultaListaCiudad();
		consultaListaCliente();
		usuarioseleccionado = new AdmUsuario();
       
    }

    //metodo que instancia nuevos objetos de una clase
     
    public void cancelar() {
        admusuario = new AdmUsuario();
        admempleado = new AdmEmpleado();
        idestado = -1;
        bandera = false;
        rolesAsignados = new ArrayList<>();
        ciudadesSeleccionadas = new ArrayList<>();
        agenciasSeleccionadas = new ArrayList<>();
    }

    
    
    public void fechaCaducidad() {
        Calendar fechaCaducidad = Calendar.getInstance();
        fechaCaducidad.setTime(date);
        fechaCaducidad.add(Calendar.DATE, 30);
        date = fechaCaducidad.getTime();

    }

    //metodo que guarda un usuario nuevo
     
    public void guardar() {

        try {
            admusuario.setIdUsuario(serviciousuario.getPK());
            admusuario.setIdEmpleado(admempleado);
            admusuario.setClave(SesionBean.encriptarSHA512(admusuario.getClave()));
            admusuario.setFechaCreacion(date);
            admusuario.setFechaModificacion(date);
            fechaCaducidad();
            
          
            serviciousuario.create(admusuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Aviso", "Se registró el Usuario: " + admusuario.getUsuario()));
            consultaListaUsuarios();
            cancelar();
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

        }

    }


    //metodo para eliminar un usuario 
     
    public void eliminar() {
    	if(usuarioseleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {	
			admusuario = usuarioseleccionado;
		
		serviciousuario.update(admusuario);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
		consultaListaUsuarios();
		cancelar();		
		}

    }
    
    public void persistir(){
    	System.out.println("bandera esss :"+bandera);
    	if(bandera == true){
    		actualizar();  		
    	}else {
    		guardar();
    		
    	}
    	
    }
    
    public void actualizar(){
    	
    	try {
    		serviciousuario.update(admusuario);
    		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Regitro actualizado con exito"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Se produjo un error al actualizar :"+ e));
		}
 
    }
    
   
	
	@SuppressWarnings("deprecation")
	public void modificar(){
		if(usuarioseleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
		bandera =	true;
		admusuario = usuarioseleccionado;
		consultaRolesAsignados();
		consultaListaCiudad();
		consultaListaCliente();
		consultaCiudadesAsignadas();
		consultaAgenciasAsignadas();
		
		
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosUsuario').show();");
		}
	}
	
	public void cargarDatosUsuario(){
		
		cancelar();
		consultaRoles();
		consultaRolesAsignados();
		consultaListaCiudad();
		consultaListaCliente();
		consultaCiudadesAsignadas();
		consultaAgenciasAsignadas ();
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo(){
		bandera =	false;
		//admusuario = new AdmUsuario ();
		cancelar();
		consultaRoles();
		consultaRolesAsignados();
		consultaListaCiudad();
		consultaListaCliente();
		consultaCiudadesAsignadas();
		consultaAgenciasAsignadas ();
		//resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosUsuario').show();");
		
	}
	
	@SuppressWarnings("deprecation")
	public static void resetarFormulario() {
        RequestContext.getCurrentInstance().reset("frmCrear");
	}
    

    //metodo que llena una lista con los registros provenientes d ela base de datos
     
    public void consultaListaUsuarios() {

        listausuario = new ArrayList<>(); // Creo una lista para mostrar todo los usuarios en el datatable
        listausuario = serviciousuario.findAll();  
    }
    
    
    public void consultaListaCombos() {
    	listaempleado = new ArrayList<>(); // Creo una lista para mostar todos los empleado en un datatable
        listaempleado = servicioempleado.findAll();	
    }
    
    public void consultaListaCiudad() {
    	if(usuarioseleccionado != null){
    	
    		listaCiudad = new ArrayList<>();
    		listaCiudad = servicioDetalleCatalogo.ciudadesTodas();
    	}else {
    		
    		listaCiudad = new ArrayList<>();
    	}
    	
    	consultaAgenciasAsignadas();
    }
    
    public void consultaListaAgencia() {

		setListaAgencia(new ArrayList<>());
		setListaAgencia(servicioAgencia.buscaAgenciaCiudad(ciudad, idCliente)); //consulta todas las agencias del cliente seleccionado
		
		consultaAgenciasAsignadas();
	}
    
    public void consultaAgenciasAsignadas() {
    	if(usuarioseleccionado != null){
    	
    	agenciasAsignadas = new ArrayList<>();
    	agenciasAsignadas = servicioUsuarioAgencia.buscaAgenciaCiudadCliente(usuarioseleccionado );
    	} else {
    		
    		agenciasAsignadas = new ArrayList<>();
    	}

	}
    
    
    public void consultaCiudadesAsignadas() {
    	if(usuarioseleccionado != null){
    	ciudadesAsignadas = new ArrayList<>();
    	ciudadesAsignadas = servicioUsuarioCiudad.buscaCiudadesAsiganadas(usuarioseleccionado );
    	} else{
    		ciudadesAsignadas = new ArrayList<>();
    		
    	}

	}
    
    public void onRowSelectEmpleado(SelectEvent event) {
		admempleado =(AdmEmpleado) event.getObject();
    }

    
    public void asignarAgencia(){
     usuarioAgencia = new AdmUsuarioAgencia();
   
    	AdmAgencia id = new AdmAgencia();
    	id.setIdAgencia(idAgencia);
    	usuarioAgencia.setIdUsuarioAgencia(servicioUsuarioAgencia.getPK());
    	usuarioAgencia.setIdUsuario(usuarioseleccionado);
    	usuarioAgencia.setIdAgencia(id);	 	
    	servicioUsuarioAgencia.create(usuarioAgencia);
    	consultaAgenciasAsignadas();
    }
    
    public void eliminarAgencia(){
	  
	  for(AdmUsuarioAgencia item : agenciasSeleccionadas){
		  
		 servicioUsuarioAgencia.eliminaAgenciaAsiganda(item.getIdUsuarioAgencia());
		  
	  }
	  
	  consultaAgenciasAsignadas();
    }
  
  public void asignarCiudad(){
	 
	  
	  AdmDetalleCatalogo d = new AdmDetalleCatalogo();
	  d.setIdDetalleCatalogo(idCiudadC);
	  
	  AdmUsuario us = new AdmUsuario ();
	  us.setIdUsuario(usuarioseleccionado.getIdUsuario());
	  
	usuarioCiudad = new AdmUsuarioCiudad();
  	usuarioCiudad.setIdUsuarioCiudad(servicioUsuarioCiudad.getPK());
  	usuarioCiudad.setIdUsuario(us);
  	usuarioCiudad.setIdDetalleCatalogo(d); 	
  	servicioUsuarioCiudad.create(usuarioCiudad);
  	
  	consultaCiudadesAsignadas();
  	cancelar();
  }
  

  
  public void eliminarCiudad(){
	  
	  for(AdmUsuarioCiudad item : ciudadesSeleccionadas){
		  System.out.println("ssss"+ item.getIdUsuarioCiudad());
		  
		  servicioUsuarioCiudad.eliminarUsuarioCiudad(item.getIdUsuarioCiudad());
		  
	  }
	  consultaCiudadesAsignadas();
  }
    
    public void consultaListaCliente() {
		listaCliente = new ArrayList<>();
		listaCliente = servicioCliente.buscaClientePadre();
	}

   
    
    public void consultaRoles() {
    	
		setListaRol(new ArrayList<>());
		setListaRol(servicioRol.findAll());
    	
	}
    
    public void consultaRolesAsignados() {
    	if(usuarioseleccionado != null){
		rolesAsignados = new ArrayList<>();
		rolesAsignados = servicioRolUsuario.rolesAsignados(usuarioseleccionado);
    	} else { 
    		rolesAsignados = new ArrayList<>();
    	}

	}
    
    public void asignarRol(){
    	admRolUsuario= new AdmRolUsuario();
    	
    	AdmRol rol = new AdmRol();
    	rol.setIdRol(idRol);
    	
    	
    	 admRolUsuario.setIdRolUsuario(servicioRolUsuario.getPK());
    	 admRolUsuario.setIdRol(rol);
    	 admRolUsuario.setIdUsuario(usuarioseleccionado);
         servicioRolUsuario.create(admRolUsuario);
    	
         consultaRolesAsignados();
    		
    }
    
    
    public void eliminarRol(){
    	
    	 for(AdmRolUsuario item : rolesSeleccionados){
   		  System.out.println("ssss"+ item.getIdRolUsuario());
   		  
   		  servicioRolUsuario.eliminarRol(item.getIdRolUsuario());
   		  
   	  }
   	  
   	  consultaRolesAsignados();	
    }
    
    //--------------------------------------------------------------------
    
    public void asignaCiudad() {

		ciudad = idCiudadA;
    }
    public void asignaCiudadC() {

  		ciudadC = idCiudadC;
    }
    
    public List<AdmUsuario> getListausuario() {
        return listausuario;
    }

    public void setListausuario(List<AdmUsuario> listausuario) {
        this.listausuario = listausuario;
    }

    public List<AdmEmpleado> getListaempleado() {
        return listaempleado;
    }

    public void setListaempleado(List<AdmEmpleado> listaempleado) {
        this.listaempleado = listaempleado;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public AdmUsuario getAdmUsuario() {
        return admusuario;
    }

    public void setAdmUsuario(AdmUsuario admusuario) {
        this.admusuario = admusuario;
    }

    public AdmEmpleado getAdmEmpleado() {
        return admempleado;
    }

    public void setAdmEmpleado(AdmEmpleado admempleado) {
        this.admempleado = admempleado;
    }

    public List<AdmUsuario> getUsuariofiltrado() {
        return usuariofiltrado;
    }

    public void setUsuariofiltrado(List<AdmUsuario> usuariofiltrado) {
        this.usuariofiltrado = usuariofiltrado;
    }

    public int getIdestado() {
        return idestado;
    }

    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AdmUsuario getUsuarioseleccionado() {
        return usuarioseleccionado;
    }

    public void setUsuarioseleccionado(AdmUsuario usuarioseleccionado) {
        this.usuarioseleccionado = usuarioseleccionado;
    }

	public List<AdmRol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<AdmRol> listaRol) {
		this.listaRol = listaRol;
	}



	public List<AdmRolUsuario> getRolesAsignados() {
		return rolesAsignados;
	}

	public void setRolesAsignados(List<AdmRolUsuario> rolesAsignados) {
		this.rolesAsignados = rolesAsignados;
	}


	public List<AdmRolUsuario> getRolesSeleccionados() {
		return rolesSeleccionados;
	}

	public void setRolesSeleccionados(List<AdmRolUsuario> rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

	public List<AdmDetalleCatalogo> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
		this.listaCiudad = listaCiudad;
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



	public List<AdmUsuarioAgencia> getAgenciasSeleccionadas() {
		return agenciasSeleccionadas;
	}

	public void setAgenciasSeleccionadas(List<AdmUsuarioAgencia> agenciasSeleccionadas) {
		this.agenciasSeleccionadas = agenciasSeleccionadas;
	}

	public int getIdCiudadA() {
		return idCiudadA;
	}

	public void setIdCiudadA(int idCiudadA) {
		this.idCiudadA = idCiudadA;
	}

	public int getIdCiudadC() {
		return idCiudadC;
	}

	public void setIdCiudadC(int idCiudadC) {
		this.idCiudadC = idCiudadC;
	}

	

	public List<AdmUsuarioAgencia> getAgenciasAsignadas() {
		return agenciasAsignadas;
	}

	public void setAgenciasAsignadas(List<AdmUsuarioAgencia> agenciasAsignadas) {
		this.agenciasAsignadas = agenciasAsignadas;
	}

	public List<AdmUsuarioCiudad> getCiudadesAsignadas() {
		return ciudadesAsignadas;
	}

	public void setCiudadesAsignadas(List<AdmUsuarioCiudad> ciudadesAsignadas) {
		this.ciudadesAsignadas = ciudadesAsignadas;
	}

	public List<AdmUsuarioCiudad> getCiudadesSeleccionadas() {
		return ciudadesSeleccionadas;
	}

	public void setCiudadesSeleccionadas(List<AdmUsuarioCiudad> ciudadesSeleccionadas) {
		this.ciudadesSeleccionadas = ciudadesSeleccionadas;
	}

	public AdmUsuarioAgencia getUsuarioAgencia() {
		return usuarioAgencia;
	}

	public void setUsuarioAgencia(AdmUsuarioAgencia usuarioAgencia) {
		this.usuarioAgencia = usuarioAgencia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	

  
}

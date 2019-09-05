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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.event.RowEditEvent;

import com.bivi.modelos.*;

import com.bivi.servicios.*;






@ManagedBean
@ViewScoped
public class AsignacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AdmAgencia agencia;
    private AdmAgencia agenciaSeleccionada;
    private List<AdmAgencia> listaAgencia;
    private List<FisGuardiaAgencia> listaAsignacion;
    
    
	

	
	
	//GUARDIAS agencias
		private FisGuardiaAgencia guardiaAgencia;
		private List <FisGuardiaAgencia> listaGuardia;
		private List <FisGuardiaAgencia> listaGuardiaSeleccionado; // puede seleccionar varios tripulantes
		
		
		//guardias epleados
				private AdmEmpleado empleado;
				private List <AdmEmpleado> listaEmpleado;
				private List <AdmEmpleado> listaGuardiasSeleccionados; // puede seleccionar varios tripulantes
			
	

	private int idUsuario;
	private int idAgencia;
	
	private Date fecha;
	

	@EJB
	private ServicioFisGuardiaAgencia servicioFisGuardia ; 
	
	@EJB
	private ServicioAdmRolUsuario servicioRolUsuario ; 
	@EJB
	private ServicioAdmEmpleado servicioEmpleado ;
	@EJB
	private ServicioAdmAgencia servicioAgencia;
	
	
	
	

	@PostConstruct
	public void init() {
		cancelar();
		
		consultaListaEmpleados();
		consultaListaAgencias();
		consultaListaAsignacion();
		fecha = new Date ();
		guardiaAgencia.setFechaAsignacion(fecha);
		
		
		
		
		
			
	}
	
public void consultaListaAsignacion() {
		
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAgencia.findAll();
	}
	
	
	public void consultaListaAgencias() {
		
		listaAsignacion = new ArrayList<>();
		listaAsignacion = servicioFisGuardia.buscaAsigancion();
		
		
		
	}
	
	
	
	public void cancelar() {
		
		
		setGuardiaAgencia(new FisGuardiaAgencia());
		
		
		
		
		
		
		

	}
	

	public void guardar() {
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
			AdmAgencia agencia = new AdmAgencia();
			agencia.setIdAgencia(idAgencia);
		//obtengo la fecha y hora corriente
		Timestamp fechaActual;
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		//Guardo en la tabla ope_tripulacion
		for (AdmEmpleado  item: listaGuardiasSeleccionados){
			guardiaAgencia.setIdGuardiaAgencia(servicioFisGuardia.getPK());
			guardiaAgencia.setIdUsuario2(usuario);
			guardiaAgencia.setSysDelete(0);
			guardiaAgencia.setActivo(1);
			guardiaAgencia.setIdEmpleado(item);
			guardiaAgencia.setIdAgencia(agencia);
			
		System.out.println("xxxxxx"+guardiaAgencia.getIdEmpleado().getNombres().toString());
		servicioFisGuardia.create(guardiaAgencia);
			//guardo enla tabla ope_detalle_tripulacion
			
		
		}
		
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		//consultaListas();	
		consultaListaAgencias();
		
		cancelar();
	} 
	
	
	



	
	
	
	
	
	
	
	public void consultaListaEmpleados() {	
		
	
		setListaEmpleado(new ArrayList<>());
		setListaEmpleado(servicioEmpleado.findAll( ));
		
		
	
	}
	
	
	
	public void onRowEdit(RowEditEvent event){
		guardiaAgencia=(FisGuardiaAgencia) event.getObject();
		
		actualizaAsignacion();
		
	
	}
	
	public void actualizaAsignacion(){
		servicioFisGuardia.update(guardiaAgencia);
			
	}
	
	public void eliminarAsignacion(){
		
		servicioFisGuardia.delete(guardiaAgencia);
		consultaListaAsignacion();
		
		
		
	}
	
	
	
	
	
public void onRowCancel(RowEditEvent event){
		
		
		
	}
	
	


	public AdmEmpleado getEmpleado() {
		return empleado;
	}


	public void setEmpleado(AdmEmpleado empleado) {
		this.empleado = empleado;
	}


	public List <AdmEmpleado> getListaEmpleado() {
		return listaEmpleado;
	}


	public void setListaEmpleado(List <AdmEmpleado> listaEmpleado) {
		this.listaEmpleado = listaEmpleado;
	}


	public List <AdmEmpleado> getListaGuardiasSeleccionados() {
		return listaGuardiasSeleccionados;
	}


	public void setListaGuardiasSeleccionados(List <AdmEmpleado> listaGuardiasSeleccionados) {
		this.listaGuardiasSeleccionados = listaGuardiasSeleccionados;
	}


	public FisGuardiaAgencia getGuardiaAgencia() {
		return guardiaAgencia;
	}


	public void setGuardiaAgencia(FisGuardiaAgencia guardiaAgencia) {
		this.guardiaAgencia = guardiaAgencia;
	}


	public AdmAgencia getAgencia() {
		return agencia;
	}


	public void setAgencia(AdmAgencia agencia) {
		this.agencia = agencia;
	}


	public AdmAgencia getAgenciaSeleccionada() {
		return agenciaSeleccionada;
	}


	public void setAgenciaSeleccionada(AdmAgencia agenciaSeleccionada) {
		this.agenciaSeleccionada = agenciaSeleccionada;
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

	public List<FisGuardiaAgencia> getListaAsignacion() {
		return listaAsignacion;
	}

	public void setListaAsignacion(List<FisGuardiaAgencia> listaAsignacion) {
		this.listaAsignacion = listaAsignacion;
	}

	public List<FisGuardiaAgencia> getListaGuardiaSeleccionado() {
		return listaGuardiaSeleccionado;
	}

	public void setListaGuardiaSeleccionado(List<FisGuardiaAgencia> listaGuardiaSeleccionado) {
		this.listaGuardiaSeleccionado = listaGuardiaSeleccionado;
	}		
	
	

	
	
	
	
/*
	 public void persitir() {
	        if (bandera == true) {
	            //actualizar();
	        } else {
	        //    guardar();
	        }
	    }
	 

	*/


	
	
	

	

	





	

	



	
	
	
	
	
	
	

}

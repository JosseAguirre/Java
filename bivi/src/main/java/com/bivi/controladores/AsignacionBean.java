package com.bivi.controladores;
 
import java.io.Serializable;
import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
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

//import org.primefaces.component.export.ExcelOptions;
//import org.primefaces.component.export.PDFOptions;
import org.primefaces.context.RequestContext;
//import org.primefaces.event.RowEditEvent;

import com.bivi.modelos.*;
import com.bivi.servicios.*;






@ManagedBean
@ViewScoped
public class AsignacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FisGuardiaAgencia guardiaAgencia;
	private FisGuardiaAgencia guardiaAgenciaSelecionada;
	private List<FisGuardiaAgencia> listaGuardiaAgencia;
	private List<FisGuardiaAgencia> guardiaAgenciaFiltrada;
	
	//Variable de tipo AdmAgencia de las listas para la busqueda de los selectOneMenu
	private AdmAgencia agencia;
	private List<AdmAgencia> listaAgencia;
	
	//Variable de tipo AdmAgencia de las listas para la busqueda de los selectOneMenu
	private AdmUsuario usuario;
	private List<AdmUsuario> listaUsuario;
	
	//Variable de tipo AdmPuesto de las listas para la busqueda de los selectOneMenu
	private AdmPuesto puesto;
	private List<AdmPuesto> listaPuesto;
	
	//establesco conección a los servicios por medio de los ejb
	@EJB
	private ServicioFisGuardiaAgencia servicioFisGuardiaAgencia;
	@EJB
	private ServicioAdmAgencia servicioAdmAgencia;
	@EJB
	private ServicioAdmUsuario servicioAdmUsuario;
	@EJB
	private ServicioAdmPuestos servicioAdmPuesto;
	
	//Variables que van a ser usadas
	private int idGuardiaAgencia;
	private int idAgencia;
	private int idUsuario;
	private int idPuesto;
	private Date fecha;
	private boolean bandera;
	
	//metodo que inicia el proceso
	@PostConstruct
	public void init() {
		cancelar();
		consultaListaGuardiaAgencia();
		fecha = new Date ();
		//guardiaAgencia.setFechaAsignacion(fecha);
	}
	
	public void cancelar() {
		guardiaAgencia = new FisGuardiaAgencia();
    	bandera = false;
    	idGuardiaAgencia = -1;
    	idAgencia = -1;
    	idUsuario = -1;
    	idPuesto = -1;
    }
	
	//metodo que llena una lista con los registros provenientes de la base de datos
	
	public void consultaListaGuardiaAgencia() {
		listaGuardiaAgencia = new ArrayList<>();
		listaGuardiaAgencia = servicioFisGuardiaAgencia.findAll();
	}
	
	//Metodo usado para el contenido de los selectOneMenu
	public void consultaListaCombos() {
		
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAdmAgencia.findAll();
		
		listaUsuario = new ArrayList<>();
		listaUsuario = servicioAdmUsuario.buscaGuardias();
		
		listaPuesto = new ArrayList<>();
		listaPuesto = servicioAdmPuesto.findAll();
	}
	
	//Metodo para guardar una nueva asignacion
	public void guardar() {
		
		AdmUsuario usuarioAsignadoPor = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		AdmAgencia idAgencia = new AdmAgencia();
		idAgencia.setIdAgencia(this.idAgencia);
		
		AdmUsuario idUsuario = new AdmUsuario();
		idUsuario.setIdUsuario(this.idUsuario);
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		//obtengo la fecha y hora corriente
		Timestamp fechaActual;
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		guardiaAgencia.setIdGuardiaAgencia((int)servicioFisGuardiaAgencia.getPK());
		guardiaAgencia.setIdAgencia(idAgencia);
		guardiaAgencia.setIdUsuario(idUsuario);
		guardiaAgencia.setIdPuesto(idPuesto);
		guardiaAgencia.setIdUsuarioAsignadoPor(usuarioAsignadoPor);
		guardiaAgencia.setFechaAsignacion(fechaActual);
		guardiaAgencia.setActivo(1);
		guardiaAgencia.setSysDelete(0);
		
		
		servicioFisGuardiaAgencia.create(guardiaAgencia);
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaGuardiaAgencia();
		cancelar();
	}
	
	//Metodo para actualizar una asignacion
	public void actualizar() {	
		AdmAgencia idAgencia = new AdmAgencia();
		idAgencia.setIdAgencia(this.idAgencia);
		
		AdmUsuario idUsuario = new AdmUsuario();
		idUsuario.setIdUsuario(this.idUsuario);
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		this.guardiaAgencia.setIdAgencia(idAgencia);
		this.guardiaAgencia.setIdUsuario(idUsuario);
		this.guardiaAgencia.setIdPuesto(idPuesto);
		
		
		servicioFisGuardiaAgencia.update(guardiaAgencia);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaListaGuardiaAgencia();
		cancelar();
	}
	
	//Metodo para eliminar una asignacion
	public void eliminar() {
		if(guardiaAgenciaSelecionada == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			guardiaAgencia = guardiaAgenciaSelecionada;
			servicioFisGuardiaAgencia.delete(guardiaAgencia);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaListaGuardiaAgencia();
			cancelar();
		}
	}
	
	//Metodo modificar
		@SuppressWarnings("deprecation")
		public void modificar() {
			if(guardiaAgenciaSelecionada == null) {
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
			}else{
				consultaListaCombos();
				bandera = true;
				guardiaAgencia = guardiaAgenciaSelecionada;
				
				resetarFormulario();
				RequestContext.getCurrentInstance().execute("PF('dlgDatosAsignacion').show();");
			}
		}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaListaCombos();
		guardiaAgencia = new FisGuardiaAgencia();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosAsignacion').show();");
	}
	
	@SuppressWarnings("deprecation")
	public void resetarFormulario() {
		RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	public void persistir() {
	    if (bandera == true) {
	        actualizar();
	    } else {
	        guardar();
	    }
	}
	
	
	public FisGuardiaAgencia getGuardiaAgencia() {
		return guardiaAgencia;
	}
	public void setGuardiaAgencia(FisGuardiaAgencia guardiaAgencia) {
		this.guardiaAgencia = guardiaAgencia;
	}
	public FisGuardiaAgencia getGuardiaAgenciaSelecionada() {
		return guardiaAgenciaSelecionada;
	}
	public void setGuardiaAgenciaSelecionada(FisGuardiaAgencia guardiaAgenciaSelecionada) {
		this.guardiaAgenciaSelecionada = guardiaAgenciaSelecionada;
	}
	public List<FisGuardiaAgencia> getListaGuardiaAgencia() {
		return listaGuardiaAgencia;
	}
	public void setListaGuardiaAgencia(List<FisGuardiaAgencia> listaGuardiaAgencia) {
		this.listaGuardiaAgencia = listaGuardiaAgencia;
	}
	public List<FisGuardiaAgencia> getGuardiaAgenciaFiltrada() {
		return guardiaAgenciaFiltrada;
	}
	public void setGuardiaAgenciaFiltrada(List<FisGuardiaAgencia> guardiaAgenciaFiltrada) {
		this.guardiaAgenciaFiltrada = guardiaAgenciaFiltrada;
	}
	public AdmAgencia getAgencia() {
		return agencia;
	}
	public void setAgencia(AdmAgencia agencia) {
		this.agencia = agencia;
	}
	public List<AdmAgencia> getListaAgencia() {
		return listaAgencia;
	}
	public void setListaAgencia(List<AdmAgencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}
	public AdmUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(AdmUsuario usuario) {
		this.usuario = usuario;
	}
	public List<AdmUsuario> getListaUsuario() {
		return listaUsuario;
	}
	public void setListaUsuario(List<AdmUsuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	public AdmPuesto getPuesto() {
		return puesto;
	}
	public void setPuesto(AdmPuesto puesto) {
		this.puesto = puesto;
	}
	public List<AdmPuesto> getListaPuesto() {
		return listaPuesto;
	}
	public void setListaPuesto(List<AdmPuesto> listaPuesto) {
		this.listaPuesto = listaPuesto;
	}
	public int getIdGuardiaAgencia() {
		return idGuardiaAgencia;
	}
	public void setIdGuardiaAgencia(int idGuardiaAgencia) {
		this.idGuardiaAgencia = idGuardiaAgencia;
	}
	public int getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdPuesto() {
		return idPuesto;
	}
	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	/*
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
	*/
}

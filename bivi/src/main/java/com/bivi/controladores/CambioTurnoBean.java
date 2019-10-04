package com.bivi.controladores;
 
import java.io.Serializable;
import java.sql.Timestamp;
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

import org.primefaces.context.RequestContext;


import com.bivi.modelos.*;
import com.bivi.servicios.*;






@ManagedBean
@ViewScoped
public class CambioTurnoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FisCambioTurno cambioTurno;
	private FisCambioTurno cambioTurnoSeleccionado;
	private List<FisCambioTurno> listaCambioTurno;
	private List<FisCambioTurno> cambioTurnoFiltrado;
	
	//Variable de tipo AdmUsuario de las listas para la busqueda de los selectOneMenu
	private AdmUsuario usuario;
	private List<AdmUsuario> listaUsuario;
	
	//Variable de tipo AdmPuesto de las listas para la busqueda de los selectOneMenu
	private AdmPuesto puesto;
	private List<AdmPuesto> listaPuesto;
	
	//Variable de tipo AdmPuesto de las listas para la busqueda de los selectOneMenu
	private FisGuardiaAgencia guardiaAgencia;
	private List<FisGuardiaAgencia> listaGuardiaAgencia;
	private List<FisGuardiaAgencia> listaGuardiaAgenciaGuardias;
	
	@EJB
	private ServicioFisCambioTurno servicioFisCambioTurno;
	@EJB
	private ServicioAdmUsuario servicioAdmUsuario;
	@EJB
	private ServicioAdmPuestos servicioAdmPuesto;
	@EJB
	private ServicioFisGuardiaAgencia servicioFisGuardiaAgencia;
	
	
	//Variables que van a ser usadas
	private int idCambioTurno;
	private int idUsuario;
	private int idUsuario2;
	private int idPuesto;
	private Date fecha;
	private boolean bandera;
	
	
	@PostConstruct
	public void init() {
		cancelar();
		consultaListaCambioTurno();
		consultaPuestoAsignado();
		fecha = new Date ();
	}
	
	public void cancelar() {
		cambioTurno = new FisCambioTurno();
    	bandera = false;
    	idCambioTurno = -1;
    	idUsuario = -1;
    	idUsuario2 = -1;
    }
	
	//metodo que llena una lista con los registros provenientes de la base de datos
	public void consultaListaCambioTurno() {
		AdmUsuario guardia = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		listaCambioTurno = new ArrayList<>();
		listaCambioTurno = servicioFisCambioTurno.buscaCambiosPorPersona(guardia);
	}
	
	public void consultaPuestoAsignado() {
		
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		listaGuardiaAgencia = new ArrayList<>();
		listaGuardiaAgencia = servicioFisGuardiaAgencia.buscaPuestoUsuario(usuario);
		
		idPuesto = listaGuardiaAgencia.get(0).getIdPuesto().getIdPuesto();
	}
	
	public void consultaGuardias() {
		
		listaGuardiaAgenciaGuardias = new ArrayList<>();
		listaGuardiaAgenciaGuardias = servicioFisGuardiaAgencia.buscaGuardiasPuesto(idPuesto);
	}
	
	//Metodo para guardar una nuevo cambio
	public void guardar() {
		
		AdmUsuario idUsuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		AdmUsuario idUsuario2 = new AdmUsuario();
		idUsuario2.setIdUsuario(this.idUsuario2);
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		//obtengo la fecha y hora corriente
		Timestamp fechaActual;
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		cambioTurno.setIdCambioTurno((int)servicioFisCambioTurno.getPK());
		cambioTurno.setIdUsuarioEntrega(idUsuario);
		cambioTurno.setIdUsuarioRecibe(idUsuario2);
		cambioTurno.setIdPuesto(idPuesto);
		cambioTurno.setFechaCambio(fechaActual);
		
		
		servicioFisCambioTurno.create(cambioTurno);
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListaCambioTurno();
		cancelar();
	}
	
	//Metodo para actualizar un cambio
	public void actualizar() {
		
		AdmUsuario idUsuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		AdmUsuario idUsuario2 = new AdmUsuario();
		idUsuario2.setIdUsuario(this.idUsuario2);
		
		AdmPuesto idPuesto = new AdmPuesto();
		idPuesto.setIdPuesto(this.idPuesto);
		
		//obtengo la fecha y hora corriente
		Timestamp fechaActual1;
		Calendar cali = Calendar.getInstance();
		fechaActual1 = new Timestamp(cali.getTimeInMillis());
		
		this.cambioTurno.setIdUsuarioEntrega(idUsuario);
		this.cambioTurno.setIdUsuarioRecibe(idUsuario2);
		this.cambioTurno.setIdPuesto(idPuesto);
		this.cambioTurno.setFechaCambio(fechaActual1);
		
		
		
		servicioFisCambioTurno.update(cambioTurno);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Modificados Correctamente "));
		consultaListaCambioTurno();
		cancelar();
	}
	
	//Metodo para eliminar un cambio
	public void eliminar() {
		if(cambioTurnoSeleccionado == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			cambioTurno = cambioTurnoSeleccionado;
			servicioFisCambioTurno.delete(cambioTurno);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
			consultaListaCambioTurno();
			cancelar();
		}
	}
	
	//Metodo modificar
	@SuppressWarnings("deprecation")
	public void modificar() {
		if(cambioTurnoSeleccionado == null) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		}else{
			consultaGuardias();
			bandera = true;
			cambioTurno = cambioTurnoSeleccionado;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosCambio').show();");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		consultaGuardias();
		cambioTurno = new FisCambioTurno();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosCambio').show();");
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
	
	
	public FisCambioTurno getCambioTurno() {
		return cambioTurno;
	}
	public void setCambioTurno(FisCambioTurno cambioTurno) {
		this.cambioTurno = cambioTurno;
	}
	public FisCambioTurno getCambioTurnoSeleccionado() {
		return cambioTurnoSeleccionado;
	}
	public void setCambioTurnoSeleccionado(FisCambioTurno cambioTurnoSeleccionado) {
		this.cambioTurnoSeleccionado = cambioTurnoSeleccionado;
	}
	public List<FisCambioTurno> getListaCambioTurno() {
		return listaCambioTurno;
	}
	public void setListaCambioTurno(List<FisCambioTurno> listaCambioTurno) {
		this.listaCambioTurno = listaCambioTurno;
	}
	public List<FisCambioTurno> getCambioTurnoFiltrado() {
		return cambioTurnoFiltrado;
	}
	public void setCambioTurnoFiltrado(List<FisCambioTurno> cambioTurnoFiltrado) {
		this.cambioTurnoFiltrado = cambioTurnoFiltrado;
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
	public FisGuardiaAgencia getGuardiaAgencia() {
		return guardiaAgencia;
	}
	public void setGuardiaAgencia(FisGuardiaAgencia guardiaAgencia) {
		this.guardiaAgencia = guardiaAgencia;
	}
	public List<FisGuardiaAgencia> getListaGuardiaAgencia() {
		return listaGuardiaAgencia;
	}
	public void setListaGuardiaAgencia(List<FisGuardiaAgencia> listaGuardiaAgencia) {
		this.listaGuardiaAgencia = listaGuardiaAgencia;
	}
	public List<FisGuardiaAgencia> getListaGuardiaAgenciaGuardias() {
		return listaGuardiaAgenciaGuardias;
	}

	public void setListaGuardiaAgenciaGuardias(List<FisGuardiaAgencia> listaGuardiaAgenciaGuardias) {
		this.listaGuardiaAgenciaGuardias = listaGuardiaAgenciaGuardias;
	}

	public int getIdCambioTurno() {
		return idCambioTurno;
	}
	public void setIdCambioTurno(int idCambioTurno) {
		this.idCambioTurno = idCambioTurno;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdUsuario2() {
		return idUsuario2;
	}

	public void setIdUsuario2(int idUsuario2) {
		this.idUsuario2 = idUsuario2;
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
}

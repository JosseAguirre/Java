package com.scl.operacion.controlador;

import java.io.Serializable;


import java.sql.Timestamp;
import java.text.ParseException;
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

import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmAgencia;
import com.scl.administracion.servicio.ServicioAdmCliente;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.operacion.modelo.OpeCircuito;
import com.scl.operacion.modelo.OpeEquipoMovil;
import com.scl.operacion.modelo.OpeTransaccion;
import com.scl.operacion.servicio.ServicioCircuito;
import com.scl.operacion.servicio.ServicioEquipoMovil;
import com.scl.operacion.servicio.ServicioRutero;
import com.scl.operacion.servicio.ServicioTransaccion;


@ManagedBean
@ViewScoped
public class ReasignacionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private  List<OpeTransaccion> listaTransaccion; 
	private  List<OpeTransaccion> transaccionSeleccionada;
	private List<AdmDetalleCatalogo> listaCiudad;
	

	private List<OpeCircuito> listaCircuitoMovil;
	//private List<OpeCircuito> circuitosSeleccionados;

	
	
	

	private List<Object>listaReasignacion;
	
	

	private List<OpeEquipoMovil> listaEquipoMovil;
	
	private OpeTransaccion transaccion;

	
	
	private int idCiudad;
	
	private int idCircuito;
	private int idEquipoMovil;
	private Timestamp fechaActual;
	
	
	private double sumaEfectivoTransito;
	private double sumaChequeTransito;
	private double sumaTotalTransito;
	
	
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	@EJB
	private ServicioCircuito servicioCircuito ;
	@EJB
	private ServicioRutero servicioRutero ;
	@EJB
	private ServicioAdmCliente servicioCliente;
	@EJB
	private ServicioAdmAgencia servicioAgencia;
	@EJB
	private ServicioEquipoMovil servicioEquipoMovil;
	@EJB
	private ServicioTransaccion servicioTransaccion;
	
	@PostConstruct
	public void init() {
		cancelar();
		
		
		
		consultaListaCiudad();
		//consultaListas();
		
		
		
		
		//		
		
		
		
			
	}
	
      public void consultaListaReasignacion() throws ParseException{ 
			
		
		//String concat = Arrays.toString(circuitosSeleccionados);
	//	concat = concat.substring(1,concat.length()-1);
    	  String idEstado = "12,13";
		listaTransaccion = new ArrayList<>();
		listaTransaccion = servicioTransaccion.buscaTransaccionEnDP(idCiudad, idEstado);
			
	
	}
	

	
	public void consultaListaCiudad() {
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
			setListaCiudad(new ArrayList<>());
			setListaCiudad(servicioDetalleCatalogo.ciudadesAsignadas(usuario));
		}
	
	
	public void cancelar() {
		setIdCiudad(-1);
		transaccion = new OpeTransaccion();
		
		

	}
	
	public void nuevo() throws ParseException{	
		if(transaccionSeleccionada.size() == 0){
			FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No hay transaciones selecciondas"));
			
			
		}else {
			
			validarTransacciones();
			
		
		
		
		}
		
	} 
	
	
	public void validarTransacciones() throws ParseException{
		int a = 0;
		
		for(OpeTransaccion item : transaccionSeleccionada){
			
			if (item.getEstadoTransaccion()== 12){
				a+=1;
	
			}
			
			
		}
		
		if(a>= 1){
			
			FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Solo se pueden reasignar transacciones peviamente validada su ingreso"));
			
			
			
		} else{
			
			RequestContext.getCurrentInstance().execute("PF('dlgReasignar').show();");	
		
		
		
		
		
		}
		
	}
	
	
	public void reasignarTransaccion() throws ParseException{ //guarda las transaciones reasignadas	
		
		 
		
		
		
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());

		
			
			for(OpeTransaccion item : transaccionSeleccionada){
			
				
				item.setIdTransaccion(servicioTransaccion.getPK());
				item.setEstadoTransaccion(14);
				item.setFechaInicio(fechaActual);
				item.setFechaFin(fechaActual);
				item.setFechaOperacion(transaccion.getFechaOperacion());
				item.setIdEquipoMovil(idEquipoMovil);
				item.setIdCircuito(idCircuito);
				
				servicioTransaccion.create(item);
				}
				
				
			
			
				
				
			
			consultaListaReasignacion ();
			
			
			
			
		
		
		
		
		
		
		
	} 
	
	
	public void consultaListaMovil() {		
		setListaEquipoMovil(new ArrayList<>());
		setListaEquipoMovil(servicioEquipoMovil.buscaEquipoMovil2(idCiudad, transaccion.getFechaOperacion().toString()));
		for(OpeEquipoMovil item : listaEquipoMovil){
			System.out.println("xxxxx"+item.getNombre());
		}
		
	}
	
public void consultaCircuitoPorEquipo() {
		
		
		setListaCircuitoMovil(new ArrayList<>());
		setListaCircuitoMovil(servicioCircuito.buscaCircuitoPorEquipo(idEquipoMovil, transaccion.getFechaOperacion()));
		System.out.println("tamaño "+listaCircuitoMovil.size());
		
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




	public List<OpeEquipoMovil> getListaEquipoMovil() {
		return listaEquipoMovil;
	}




	public void setListaEquipoMovil(List<OpeEquipoMovil> listaEquipoMovil) {
		this.listaEquipoMovil = listaEquipoMovil;
	}




	public OpeTransaccion getTransaccion() {
		return transaccion;
	}




	public void setTransaccion(OpeTransaccion transaccion) {
		this.transaccion = transaccion;
	}




	public List<OpeCircuito> getListaCircuitoMovil() {
		return listaCircuitoMovil;
	}




	public void setListaCircuitoMovil(List<OpeCircuito> listaCircuitoMovil) {
		this.listaCircuitoMovil = listaCircuitoMovil;
	}




	public int getIdEquipoMovil() {
		return idEquipoMovil;
	}




	public void setIdEquipoMovil(int idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}




	public int getIdCircuito() {
		return idCircuito;
	}




	public void setIdCircuito(int idCircuito) {
		this.idCircuito = idCircuito;
	}




	public List<Object> getListaReasignacion() {
		return listaReasignacion;
	}




	public void setListaReasignacion(List<Object> listaReasignacion) {
		this.listaReasignacion = listaReasignacion;
	}




	

	public List<OpeTransaccion> getTransaccionSeleccionada() {
		return transaccionSeleccionada;
	}

	public void setTransaccionSeleccionada(List<OpeTransaccion> transaccionSeleccionada) {
		this.transaccionSeleccionada = transaccionSeleccionada;
	}

	public double getSumaEfectivoTransito() {
		return sumaEfectivoTransito;
	}

	public void setSumaEfectivoTransito(double sumaEfectivoTransito) {
		this.sumaEfectivoTransito = sumaEfectivoTransito;
	}

	public double getSumaChequeTransito() {
		return sumaChequeTransito;
	}

	public void setSumaChequeTransito(double sumaChequeTransito) {
		this.sumaChequeTransito = sumaChequeTransito;
	}

	public double getSumaTotalTransito() {
		return sumaTotalTransito;
	}

	public void setSumaTotalTransito(double sumaTotalTransito) {
		this.sumaTotalTransito = sumaTotalTransito;
	}

	public List<OpeTransaccion> getListaTransaccion() {
		return listaTransaccion;
	}

	public void setListaTransaccion(List<OpeTransaccion> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}

	
	
}

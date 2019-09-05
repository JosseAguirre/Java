package com.scl.cpc.controlador;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
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

import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.administracion.servicio.ServicioAdmEmpleado;
import com.scl.cpc.modelo.CpcCaja;
import com.scl.cpc.modelo.*;
import com.scl.cpc.servicio.*;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.ServicioTransaccion;

@ManagedBean
@ViewScoped
public class AsignacionTulasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CpcCaja caja;
	private List<CpcCaja> listaCajas;
	private List<CpcCaja> cajasSeleccionadas;
	private CpcDistribucionCaja distribucion;
	private int idEmpleadoA;
	private int idEmpleado;
	private List<AdmEmpleado> listaCajerosAsignados; // lista usada para llenar el
													// combo de seleccion cuando
													// se asignan las
													// transacciones
	private List<AdmEmpleado> listaCajeros; // lista usada para mostrar todos
											// los empleados con rol de cajero y
											// asignarle un cubiculo
	private List<OpeTransaccion> listaTransaccionesNOA;
	private List<OpeTransaccion> transaccionesSelecNOA;
	private List<CpcDistribucionCaja> listaTransaccionesSIA;
	private List<CpcDistribucionCaja> transaccionesSelecSIA;
	String space = " ";

	@EJB
	private ServicioCaja servicioCaja;
	@EJB
	private ServicioAdmEmpleado servicioEmpleado;
	@EJB
	private ServicioTransaccion servicioTransaccion;
	@EJB
	private ServicioDistribucionCaja servicioDistribucion;

	@PostConstruct
	public void init() {
		consultaCajerosAsignados();
		consultaTrNOA();
		consultaTrSIA();
		cancelar();

	}

	public void cancelar() {
		
		
		
		
		caja = new CpcCaja();
		distribucion = new CpcDistribucionCaja();
		idEmpleadoA = -1;
		idEmpleado = -1;
		
		
		

	}

	// GESTION DE ASIGNACION DE TRANSACCIONES PARA CAJEROS
	
	public void consultaCajerosAsignados() { //consulta los cajeros que ya tiene asignado una caja --llena el combo
		listaCajerosAsignados = new ArrayList<>();
		listaCajerosAsignados = servicioEmpleado.buscaCajerosAsignados(new Date());
		
	

	}

	

	public void consultaTrNOA() { // Consulta lista de transaccion NO asignadas
									// a cajeros
		listaTransaccionesNOA = new ArrayList<>();
		listaTransaccionesNOA = servicioTransaccion.buscaTrNoAsignadas(new Date());

	}

	public void consultaTrSIA() { // Consulta lista de transaccion SI asignadas // a cajeros
		listaTransaccionesSIA = new ArrayList<>();
		 try {
			listaTransaccionesSIA = servicioDistribucion.buscaTrSiAsignadas(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void guardarAsignacion() { // guarda las transacciones que se asignaran al cajero para conteo
		
		//obtengo la fecha y hora corriente
				Timestamp fechaActual;
				Calendar cali = Calendar.getInstance();
				fechaActual = new Timestamp(cali.getTimeInMillis());
				
				
			
				
				
		if (transaccionesSelecNOA == null || idEmpleadoA == -0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar Registro y un Cajero "));

		
		
		
		} else{
		
			
			
			
			
			AdmEmpleado em = new AdmEmpleado();
			em.setIdEmpleado(idEmpleadoA);
		
		for (OpeTransaccion item : transaccionesSelecNOA) {
			distribucion.setIdDistribucionCaja(servicioDistribucion.getPK());
			distribucion.setFechaAsigancion(fechaActual);
			distribucion.setIdTransaccion(item);
			distribucion.setIdEmpleado(em);
			distribucion.setEstado(0);
			servicioDistribucion.create(distribucion);

		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registros Guardados con exito "));
		consultaTrNOA();
		consultaTrSIA();
		
		}

	}

	public void quitarAsignacion() {// quita las transacciones que se asignar previamente al cajero para conteo
		if (transaccionesSelecSIA == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));

		} else {
			for (CpcDistribucionCaja item : transaccionesSelecSIA) {
				servicioDistribucion.quitarAsignacion(item);
				 consultaTrSIA();
				 consultaTrNOA();

			}

		}

	}
	
	/////////////////////////////////////

	// GESTION DE CAJAS
	public void nuevaCaja() {
		consultaCajeros();
		consultaCajas();
		RequestContext.getCurrentInstance().reset("frmCrear");
		RequestContext.getCurrentInstance().execute("PF('dlgCaja').show();");

	}

	public void consultaCajeros() {
		listaCajeros = new ArrayList<>();
		listaCajeros = servicioEmpleado.buscaCajeros();

	}

	public void consultaCajas() {
		listaCajas = new ArrayList<>();
		listaCajas = servicioCaja.buscaCajas(new Date());

	}

	public void guardarCaja() {
		
		
		if(idEmpleado == 0 || caja.getCubiculo() == null || caja.getTiempo() == null){
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe llenar todos los campos requeridos "));
			
			
			
			
			
		}else{
		
		
		AdmEmpleado em = new AdmEmpleado();
		em.setIdEmpleado(idEmpleado);
		
	caja.setIdCaja(servicioCaja.getPK());
	caja.setFecha(new Date());
	
	caja.setIdEmpleado(em);	
	

	servicioCaja.create(caja);
	
	consultaCajas();
	consultaCajerosAsignados();
	cancelar();
	
		}

	}
	
	

	public void quitarCaja() {
		
		if(cajasSeleccionadas == null){
			
			System.out.println("yyyy");
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
			
			
		}else {
			
			System.out.println("xxxx");
			
			for(CpcCaja item: cajasSeleccionadas ){
			
			servicioCaja.eliminaCaja(item);
			
			}
			
			consultaCajas();
			consultaCajerosAsignados();
			cancelar();
		}
		
		

	}
	
	
	
	

	public CpcCaja getCaja() {
		return caja;
	}

	public void setCaja(CpcCaja caja) {
		this.caja = caja;
	}

	public int getIdEmpleadoA() {
		return idEmpleadoA;
	}

	public void setIdEmpleadoA(int idEmpleadoA) {
		this.idEmpleadoA = idEmpleadoA;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	

	public List<AdmEmpleado> getListaCajerosAsignados() {
		return listaCajerosAsignados;
	}

	public void setListaCajerosAsignados(List<AdmEmpleado> listaCajerosAsignados) {
		this.listaCajerosAsignados = listaCajerosAsignados;
	}

	public List<AdmEmpleado> getListaCajeros() {
		return listaCajeros;
	}

	public void setListaCajeros(List<AdmEmpleado> listaCajeros) {
		this.listaCajeros = listaCajeros;
	}

	public List<OpeTransaccion> getListaTransaccionesNOA() {
		return listaTransaccionesNOA;
	}

	public void setListaTransaccionesNOA(List<OpeTransaccion> listaTransaccionesNOA) {
		this.listaTransaccionesNOA = listaTransaccionesNOA;
	}

	public List<OpeTransaccion> getTransaccionesSelecNOA() {
		return transaccionesSelecNOA;
	}

	public void setTransaccionesSelecNOA(List<OpeTransaccion> transaccionesSelecNOA) {
		this.transaccionesSelecNOA = transaccionesSelecNOA;
	}

	public List<CpcDistribucionCaja> getListaTransaccionesSIA() {
		return listaTransaccionesSIA;
	}

	public void setListaTransaccionesSIA(List<CpcDistribucionCaja> listaTransaccionesSIA) {
		this.listaTransaccionesSIA = listaTransaccionesSIA;
	}

	public List<CpcDistribucionCaja> getTransaccionesSelecSIA() {
		return transaccionesSelecSIA;
	}

	public void setTransaccionesSelecSIA(List<CpcDistribucionCaja> transaccionesSelecSIA) {
		this.transaccionesSelecSIA = transaccionesSelecSIA;
	}

	public List<CpcCaja> getListaCajas() {
		return listaCajas;
	}

	public void setListaCajas(List<CpcCaja> listaCajas) {
		this.listaCajas = listaCajas;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public List<CpcCaja> getCajasSeleccionadas() {
		return cajasSeleccionadas;
	}

	public void setCajasSeleccionadas(List<CpcCaja> cajasSeleccionadas) {
		this.cajasSeleccionadas = cajasSeleccionadas;
	}
	
	
	

}

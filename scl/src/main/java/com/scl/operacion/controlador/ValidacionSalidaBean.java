package com.scl.operacion.controlador;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.operacion.modelo.OpeDetalleTransaccion;
import com.scl.operacion.modelo.OpeTransaccion;
import com.scl.operacion.modelo.OpeValidacionDetalle;
import com.scl.operacion.servicio.ServicioDetalleTransaccion;
import com.scl.operacion.servicio.ServicioTransaccion;
import com.scl.operacion.servicio.ServicioValidacionDetalle;


@ManagedBean
@ViewScoped
public class ValidacionSalidaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	private List<OpeDetalleTransaccion>listaNoValidadasS; //lista para guardar el resultado de la consulta, pendientes por validar en salida
	private OpeValidacionDetalle validacion;
	private OpeValidacionDetalle validacionM;
	private List<AdmDetalleCatalogo> listaCiudad;
	private OpeTransaccion transaccion;
	private Timestamp fechaActual;
	private List<OpeDetalleTransaccion> detalleSeleccionado;
	private List<OpeDetalleTransaccion> listaDetalleTransaccion;
	private OpeDetalleTransaccion detalleTransaccion;
	private List<OpeDetalleTransaccion> sellosEscaneados;
	private List<OpeDetalleTransaccion> sellosSeleccionados;
	private  List<OpeTransaccion> listaTransaccion;
	private  List<OpeTransaccion> listaTransaccionPendientes;
	
	private List<String> listaNumerosRecibos;
	
	
	

	
	private Date fecha;
	private int idCiudad;

	private double sumaEfectivoTransito;
	private double sumaChequeTransito;
	private double sumaTotalTransito;
	
	
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	@EJB
	private ServicioValidacionDetalle servicioValidacion;
	@EJB
	private ServicioTransaccion servicioTransaccion ;
	@EJB
	private ServicioDetalleTransaccion servicioDetalleTransaccion ;
	
	@PostConstruct
	public void init()  {
		cancelar();
		fecha = new Date ();
		transaccion.setFechaOperacion(fecha);
		
		consultaListaCiudad();
		
		
	}
	
	public void consultaListaCiudad() {
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
			setListaCiudad(new ArrayList<>());
			setListaCiudad(servicioDetalleCatalogo.ciudadesAsignadas(usuario));
		}
	
	
	
	public void cancelar() {
		idCiudad =-1;
		transaccion = new OpeTransaccion();
		listaDetalleTransaccion = new ArrayList<>();
		detalleTransaccion = new OpeDetalleTransaccion(); 
		listaNumerosRecibos =new ArrayList<>();
		
		sellosEscaneados = new  ArrayList<>();
		validacion = new OpeValidacionDetalle();
		validacionM = new OpeValidacionDetalle();
		

	}
	
	
	
	public void consultaListaReasignacion() { 
		String idEstado = "14";
			
		
try {
	listaTransaccion = new ArrayList<>();
	listaTransaccion = servicioTransaccion.buscaTransaccionEnDP(idCiudad, idEstado);
	
} catch (Exception e) {
	// TODO: handle exception
}
			
				
		
		}
	
	/////INGRESO
	
	
	
	
	/////SALIDA
	
	
	 
	 
	 public void guardarTransaccion(){
		 
		 Calendar cali = Calendar.getInstance();
			fechaActual = new Timestamp(cali.getTimeInMillis());
			
			//consulto los recibo que estan incompletos de validacion
			
			
			//remuevo los recibos que no se ingresaran pues
			
			List <String> recibos = new ArrayList<>(listaNumerosRecibos);
			
			String pendiente;
			
			
			
			for(OpeDetalleTransaccion item : listaNoValidadasS){
				pendiente = item.getIdTransaccion().getNumeroRecibo();
				for(String escaneado : listaNumerosRecibos ){
					if(escaneado.equals(pendiente) ){
						recibos.remove(pendiente);
						System.out.println("fue eliminado"+ pendiente);
						
						
						
					}
					
					
					
				}
				
				
				
			}
			
			
			for(String x:recibos){
				
				System.out.println("validos para insertar son"+ x);
				
			}
			
		 for(OpeTransaccion tr: listaTransaccion){
			 
			 for(String obj : recibos){
				 
				 if(tr.getNumeroRecibo().toString().equals(obj)){
					 //inserto una fila con el nuevo estado
					 tr.setIdTransaccion(servicioTransaccion.getPK());
						tr.setEstadoTransaccion(15);
						tr.setFechaInicio(fechaActual);
						tr.setFechaFin(fechaActual);
						
						servicioTransaccion.create(tr);
 
				 }
				 
			 }
			 
			 
		 }
		 
		 
	 }
	 
	 
		

	
	
	
	public void consultaNoValidasS() { //consultas las fundas no validas en su salida de  la boveda de tulas en transito
		setListaNoValidadasS(new ArrayList<>());
		setListaNoValidadasS(servicioTransaccion.noValidadasS(idCiudad, transaccion.getFechaOperacion()));
		consultaListaReasignacion();
	}
	
	
	
	public void agregarSelloEscaneado(){
		
		if(detalleTransaccion.getSello()!=""){
			sellosEscaneados.add(detalleTransaccion);	
			detalleTransaccion = new OpeDetalleTransaccion ();		
			
		}

	}
	
	
	public void guardarValidacion(){ //guarda las validaciones qeu se hacen medinte la lectura con el escanner de mano
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		
		 Set<String> set = new HashSet<String>();
		 listaNumerosRecibos = new ArrayList<String>();
		
		for(OpeDetalleTransaccion selloEsca : sellosEscaneados){
			for(OpeDetalleTransaccion selloPend : listaNoValidadasS){
				if(selloEsca.getSello().equals(selloPend.getSello())){
					
					validacion.setIdValidacionDetalle(servicioValidacion.getPK());
					validacion.setIdDetalleTransaccion(selloPend);
					validacion.setFechaValidacionIngreso(fechaActual);
					validacion.setIdEmpleado(us.getIdEmpleado());
					
					validacion.setObservacion("Escaneado");
					servicioValidacion.create(validacion);//insertar fila en tabla detallevalidado
					servicioDetalleTransaccion.actualizaValidadoS(selloPend.getIdDetalleTransaccion());//setear el true
					
					
					set.add(selloPend.getIdTransaccion().getNumeroRecibo());
						
	
				}
	
			}
	
		}
		
		listaNumerosRecibos = new ArrayList<String>(set);
		// listaNumerosRecibos = contiene los recibos de remesa que si fueron escaneados
		
		
		
		
		consultaNoValidasS();
		
		guardarTransaccion();
		
		
		sellosEscaneados = new  ArrayList<>();
		
	}
	
	
	
	
	public void guardarValidacionManual(){ //guarad las validaciones cuando se hcen de forma manual
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		 Set<String> set = new HashSet<String>();
		 listaNumerosRecibos = new ArrayList<String>();
		
		for(OpeDetalleTransaccion item : sellosSeleccionados){
			validacionM.setIdValidacionDetalle(servicioValidacion.getPK());
			validacionM.setIdDetalleTransaccion(item);
			validacionM.setFechaValidacionIngreso(fechaActual);
			validacionM.setIdEmpleado(us.getIdEmpleado());
			servicioValidacion.create(validacionM);
			servicioDetalleTransaccion.actualizaValidado(item.getIdDetalleTransaccion());
			
			set.add(item.getIdTransaccion().getNumeroRecibo());
			
			
			
		}
		
listaNumerosRecibos = new ArrayList<String>(set);
		
		
		guardarTransaccion();
		
		consultaNoValidasS();
		
		sellosEscaneados = new  ArrayList<>();
		
		
	}

	public List<AdmDetalleCatalogo> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public List<OpeDetalleTransaccion> getListaNoValidadasS() {
		return listaNoValidadasS;
	}

	public void setListaNoValidadasS(List<OpeDetalleTransaccion> listaNoValidadasS) {
		this.listaNoValidadasS = listaNoValidadasS;
	}

	public List<OpeDetalleTransaccion> getlistaNoValidadasS() {
		return listaNoValidadasS;
	}

	public void setlistaNoValidadasS(List<OpeDetalleTransaccion> listaNoValidadasS) {
		this.listaNoValidadasS = listaNoValidadasS;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	
	public List<OpeDetalleTransaccion> getDetalleSeleccionado() {
		return detalleSeleccionado;
	}
	public void setDetalleSeleccionado(List<OpeDetalleTransaccion> detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<OpeDetalleTransaccion> getListaDetalleTransaccion() {
		return listaDetalleTransaccion;
	}

	public void setListaDetalleTransaccion(List<OpeDetalleTransaccion> listaDetalleTransaccion) {
		this.listaDetalleTransaccion = listaDetalleTransaccion;
	}

	public OpeDetalleTransaccion getDetalleTransaccion() {
		return detalleTransaccion;
	}

	public void setDetalleTransaccion(OpeDetalleTransaccion detalleTransaccion) {
		this.detalleTransaccion = detalleTransaccion;
	}

	public OpeTransaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(OpeTransaccion transaccion) {
		this.transaccion = transaccion;
	}

	public List<OpeDetalleTransaccion> getSellosEscaneados() {
		return sellosEscaneados;
	}

	public void setSellosEscaneados(List<OpeDetalleTransaccion> sellosEscaneados) {
		this.sellosEscaneados = sellosEscaneados;
	}

	public List<OpeDetalleTransaccion> getSellosSeleccionados() {
		return sellosSeleccionados;
	}

	public void setSellosSeleccionados(List<OpeDetalleTransaccion> sellosSeleccionados) {
		this.sellosSeleccionados = sellosSeleccionados;
	}

	public OpeValidacionDetalle getValidacionM() {
		return validacionM;
	}

	public void setValidacionM(OpeValidacionDetalle validacionM) {
		this.validacionM = validacionM;
	}
	
	


}
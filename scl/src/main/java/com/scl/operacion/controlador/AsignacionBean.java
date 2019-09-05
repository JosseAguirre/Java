package com.scl.operacion.controlador;
 
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


import com.scl.administracion.modelo.*;

import com.scl.administracion.servicio.*;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.*;



@ManagedBean
@ViewScoped
public class AsignacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//TRIPULACION
	private OpeTripulacion tripulacion;
	private List<OpeTripulacion> listaTripulacion;//entiendase como el conjunto de la asignacion ..movil..circuito..personal y vehiculo
	private List<OpeTripulacion> listaTripulacionSelecionada;
	// VEHICULOS
	
	private List<OpeVehiculo> listaVehiculo;
	private OpeVehiculo vehiculoSeleccionado; // solo un vehiculo a la vez (un blindado)
	
	
	
	//TRIPULANTES
		private AdmEmpleado etripulante;
		private List <AdmEmpleado> elistatripulante;
		private List <AdmEmpleado> elistaTripulanteSeleccionado; // puede seleccionar varios tripulantes
	
	//CIRCUITOS
	
	private List<OpeCircuito> listaCircuito;
	private List<OpeCircuito> listaCircuitoSeleccionado; // puede seleccionar varios circuito
	
	//EQUIPOS MOVILES
	
	private List<OpeEquipoMovil> listaEquipoMovil;
	private OpeEquipoMovil movilSeleccionado; // puede seleccionar un movil a la vez
	
	//DETALLE TRIPULACION
	private OpeDetalleTripulacion detalleTripulacion;
	
	
	//RUTERO
	
	private OpeRutero rutero;
	private List <Object[]> listaHojaAlistamiento;
	
	
	
	
	
	private List<AdmDetalleCatalogo> listaCiudad;
	
	
	private  List<OpeTransaccion> listaTransaccionPO;
	private  List<OpeRutero> listaRuteroPO;
	
	
	private int idCiudad;
	private int idEstado;
	private boolean bandera ;
	private int cantVehiculo;
	private Date fecha;
	private String dia;
	private String espacio;

	
	private ExcelOptions excelOpt;
    
    private PDFOptions pdfOpt;
	
	@EJB
	private ServicioAdmRolUsuario servicioRolUsuario ; // para tripulantes
	@EJB
	private ServicioVehiculo servicioVehiculo ;
	@EJB
	private ServicioCircuito servicioCircuito ;
	@EJB
	private ServicioEquipoMovil servicioEquipoMovil ;
	@EJB
	private ServicioTripulacion servicioTripulacion ;
	@EJB
	private ServicioDetalleTripulacion servicioDetalleTripulacion ;
	@EJB
	private ServicioRutero servicioRutero ;
	@EJB
	private ServicioTransaccion servicioTransaccion ;
	@EJB
	private ServicioHojaAlistamiento servicioHoja ;
	@EJB
	private ServicioAdmEmpleado servicioEmpleado ;
	
	
	@EJB
	private ServicioUsuarioCiudad servicioUsuarioCiudad;
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	
	

	@PostConstruct
	public void init() {
		cancelar();
		fecha = new Date ();
		tripulacion.setFechaOperacion(fecha);
		espacio = " ";
		
		consultaListaCiudad();
		idCiudad = listaCiudad.get(0).getIdDetalleCatalogo();
		//		
		consultaListaTripulante();
		consultaListaVehiculo();
		consultaListaCircuito();
		consultaListaMovil();
		consultaListaTripulacion();
		
		
		
			
	}
	
	
	public void cancelar() {
		
		
		tripulacion = new OpeTripulacion();
		detalleTripulacion = new OpeDetalleTripulacion();
		
		
		
		
		rutero = new OpeRutero();
		
		listaTripulacionSelecionada = new ArrayList<>();
		
		//listaTripulacionSelecionada = new ArrayList<>();
		//listaCircuitoSeleccionado = new ArrayList<>();
		//vehiculoSeleccionado = new OpeVehiculo();
		//movilSeleccionado = new OpeEquipoMovil();
		
		
		
		
		bandera = false;
		

	}
	

	public void guardar() {
			
		//obtengo la fecha y hora corriente
		Timestamp fechaActual;
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		//Guardo en la tabla ope_tripulacion
		for (OpeCircuito circuito: listaCircuitoSeleccionado){
		tripulacion.setIdTripulacion(servicioTripulacion.getPK());
		tripulacion.setIdVehiculo(vehiculoSeleccionado);
		tripulacion.setIdEquipoMovil(movilSeleccionado);
		tripulacion.setFechaAsignacion(fechaActual);
		tripulacion.setIdCircuito(circuito);
		tripulacion.setDiaOperacion(dia);
		servicioTripulacion.create(tripulacion);
			//guardo enla tabla ope_detalle_tripulacion
			for(AdmEmpleado detalle: elistaTripulanteSeleccionado){
				detalleTripulacion.setIdDetalleTripulacion(servicioDetalleTripulacion.getPK());
				detalleTripulacion.setIdTripulacion(tripulacion);
				detalleTripulacion.setIdEmpleado(detalle);
				servicioDetalleTripulacion.create(detalleTripulacion);
				
			}
		
		}
		
		FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Guardado Correctamente "));
		consultaListas();	
		guardaCircuitoRutero();
		buscarIdRutero();
		cancelar();
	} 
	
	
	public void buscarIdRutero(){
		 listaTransaccionPO = new ArrayList<>();
		 listaRuteroPO = new ArrayList<>();
		 
		 listaTransaccionPO = servicioTransaccion.buscaTransaccionNoIdRutero(tripulacion.getFechaOperacion());
		 listaRuteroPO= servicioRutero.buscaAsignaciones(tripulacion.getFechaOperacion() ,idCiudad);
		 
		 
		 for(OpeRutero r : listaRuteroPO){
			 
			 for(OpeTransaccion t : listaTransaccionPO){
				 
				 if(r.getIdAgenciaOrigen().equals(t.getIdAgenciaOrigen().getIdAgencia())){
					 
					 System.out.println("encontrado"+r.getIdRutero());
					 
					 servicioTransaccion.actualizaIdRutero(t.getIdTransaccion(),r.getIdRutero()); // actualizo el idrutero en la tabla ope_transaccion
					 
				 }
				 
				 
				 
			 }
			 
			 
			 
		 }
		 
		 
		 
		 
		 
		
		
		
	}
	
public void guardaCircuitoRutero(){ //guardo las paradas del circuito seleccionado en la tabla ope_rutero
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
	  dia = simpleDateFormat.format(tripulacion.getFechaOperacion());
	
	listaHojaAlistamiento = servicioHoja.buscaHojasCiurcuito(tripulacion.getIdCircuito().getIdCircuito(), dia, idCiudad ); //consullto las paradas del o los circuitos selecionados
	
	//recorro la lista extraida y guardo en la tabla operutero
	for(Object[] item : listaHojaAlistamiento){
		rutero.setIdRutero(servicioRutero.getPK());
		rutero.setFechaAsignacion(tripulacion.getFechaAsignacion());
		rutero.setFechaOperacion(tripulacion.getFechaOperacion());
		rutero.setIdCircuito(tripulacion.getIdCircuito().getIdCircuito());
		rutero.setIdEquipoMovil(tripulacion.getIdEquipoMovil().getIdEquipoMovil());
		rutero.setEstadoCliete(0);
		rutero.setSysdelete(0);
		
		
		
		rutero.setHoraDesde(item[0].toString());
		rutero.setHoraHasta(item[1].toString());
		rutero.setIdClienteOrigen(Integer.parseInt(item[2].toString()));
		rutero.setIdAgenciaOrigen(Integer.parseInt(item[3].toString()));
		rutero.setIdClienteDestino(Integer.parseInt(item[4].toString()));
		rutero.setIdAgenciaDestino(Integer.parseInt(item[5].toString()));
		rutero.setIdClienteFactura(Integer.parseInt(item[2].toString()));
		
		
		
		//buscaFrecuencia();
		servicioRutero.create(rutero);
	}
	
	
		
		
	}


public void eliminarTripulacion(){
	
	if(listaTripulacionSelecionada.size() == 0){
		
		FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Debe seleccionar al menos un registro "));
		
		
		
		
	}else{
	
	for(OpeTripulacion item : listaTripulacionSelecionada){
		servicioTripulacion.eliminarDetalleTripulacion(item); // elimino el detalle de la tripulacion
		
		servicioTripulacion.eliminarTripulacion(item); // elimino la tripulacion
		
		
		
	}
	
	eliminaCircuitoRutero();
	
	consultaListaTripulante(); //si
	consultaListaVehiculo(); //si
	consultaListaMovil();//SI
	consultaListaCircuito(); //si
	consultaListaTripulacion();
	
	
}
}

public void eliminaCircuitoRutero (){ // elimina el circuito asignado
	
	for (OpeCircuito circuito: listaCircuitoSeleccionado){
		
		servicioRutero.eliminaCircuitoRutero(circuito.getIdCircuito(), tripulacion.getFechaOperacion()  );
				
		}
	
	
	
	
}
	
 
	
	
	
	public void consultaListas(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
	  dia = simpleDateFormat.format(tripulacion.getFechaOperacion());
	  System.out.println("diaaaaa"+dia);
		consultaListaTripulante(); //si
		consultaListaVehiculo(); //si
		consultaListaMovil();//SI
		consultaListaCircuito(); //si
		consultaListaTripulacion();
		
		
	}
	public void consultaListaCiudad() {
	AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCatalogo.ciudadesAsignadas(usuario);
	}
	
	
	public void consultaListaTripulante() {	
		
		//listatripulante = new ArrayList<>();
		//listatripulante = servicioRolUsuario.buscaTripulante( idCiudad, tripulacion.getFechaOperacion().toString());
		
		elistatripulante = new ArrayList<>();
		elistatripulante = servicioEmpleado.buscaTripulantePrueba( idCiudad, tripulacion.getFechaOperacion().toString());
		
		
	
	}		

	public void consultaListaVehiculo() {		
		listaVehiculo = new ArrayList<>();
		//listaVehiculo = servicioVehiculo.buscaVehiculo(idCiudad, tripulacion.getFechaOperacion().toString());
		listaVehiculo = servicioVehiculo.buscaVehiculoPrueba(idCiudad, tripulacion.getFechaOperacion().toString());
		
	}
	
	public void consultaListaTripulacion() {
		
		listaTripulacion = new ArrayList<>();
		listaTripulacion = servicioTripulacion.buscaTripulacionFecha(idCiudad,tripulacion.getFechaOperacion().toString());
		
	}
	
	
	public void consultaListaMovil() {		
		listaEquipoMovil = new ArrayList<>();
		listaEquipoMovil = servicioEquipoMovil.buscaEquipoMovil(idCiudad, tripulacion.getFechaOperacion().toString());
	}
	
	public void consultaListaCircuito() {		
		listaCircuito = new ArrayList<>();
		listaCircuito = servicioCircuito.buscaCircuito(idCiudad,dia, tripulacion.getFechaOperacion().toString());
	}
	
	
 

	 public void persitir() {
	        if (bandera == true) {
	            //actualizar();
	        } else {
	        //    guardar();
	        }
	    }
	 

	


	
	 
	public List<OpeTripulacion> getListaTripulacion() {
		return listaTripulacion;
	}


	public void setListaTripulacion(List<OpeTripulacion> listaTripulacion) {
		this.listaTripulacion = listaTripulacion;
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




	
	
	

	
	

	public OpeTripulacion getTripulacion() {
		return tripulacion;
	}


	public void setTripulacion(OpeTripulacion tripulacion) {
		this.tripulacion = tripulacion;
	}


	public List<OpeTripulacion> getListaTripulacionSelecionada() {
		return listaTripulacionSelecionada;
	}


	public void setListaTripulacionSelecionada(List<OpeTripulacion> listaTripulacionSelecionada) {
		this.listaTripulacionSelecionada = listaTripulacionSelecionada;
	}


	public List<OpeCircuito> getListaCircuitoSeleccionado() {
		return listaCircuitoSeleccionado;
	}


	public void setListaCircuitoSeleccionado(List<OpeCircuito> listaCircuitoSeleccionado) {
		this.listaCircuitoSeleccionado = listaCircuitoSeleccionado;
	}


	public OpeEquipoMovil getMovilSeleccionado() {
		return movilSeleccionado;
	}


	public void setMovilSeleccionado(OpeEquipoMovil movilSeleccionado) {
		this.movilSeleccionado = movilSeleccionado;
	}


	public List<OpeCircuito> getListaCircuito() {
		return listaCircuito;
	}


	public void setListaCircuito(List<OpeCircuito> listaCircuito) {
		this.listaCircuito = listaCircuito;
	}


	public List<OpeEquipoMovil> getListaEquipoMovil() {
		return listaEquipoMovil;
	}


	public void setListaEquipoMovil(List<OpeEquipoMovil> listaEquipoMovil) {
		this.listaEquipoMovil = listaEquipoMovil;
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


	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}


	public int getCantVehiculo() {
		return cantVehiculo;
	}


	public void setCantVehiculo(int cantVehiculo) {
		this.cantVehiculo = cantVehiculo;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public AdmEmpleado getEtripulante() {
		return etripulante;
	}


	public void setEtripulante(AdmEmpleado etripulante) {
		this.etripulante = etripulante;
	}


	public List <AdmEmpleado> getElistatripulante() {
		return elistatripulante;
	}


	public void setElistatripulante(List <AdmEmpleado> elistatripulante) {
		this.elistatripulante = elistatripulante;
	}


	public List <AdmEmpleado> getElistaTripulanteSeleccionado() {
		return elistaTripulanteSeleccionado;
	}


	public void setElistaTripulanteSeleccionado(List <AdmEmpleado> elistaTripulanteSeleccionado) {
		this.elistaTripulanteSeleccionado = elistaTripulanteSeleccionado;
	}


	public String getDia() {
		return dia;
	}


	public void setDia(String dia) {
		this.dia = dia;
	}


	public String getEspacio() {
		return espacio;
	}


	public void setEspacio(String espacio) {
		this.espacio = espacio;
	}
	
	
	
	
	
	
	

}

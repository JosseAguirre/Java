package com.scl.operacion.controlador;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.PieChartModel;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmAgencia;
import com.scl.administracion.servicio.ServicioAdmCliente;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.operacion.modelo.OpeCircuito;

import com.scl.operacion.modelo.OpeEquipoMovil;
import com.scl.operacion.modelo.OpeRutero;
import com.scl.operacion.modelo.OpeTransaccion;
import com.scl.operacion.servicio.ServicioCircuito;
import com.scl.operacion.servicio.ServicioEquipoMovil;
import com.scl.operacion.servicio.ServicioRutero;
import com.scl.operacion.servicio.ServicioTransaccion;

@ManagedBean
@ViewScoped
public class MonitoreoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<OpeRutero> listaTransaccion;

	private List<Object[]> transaccionSeleccionada;

	private List<AdmDetalleCatalogo> listaCiudad;

	private List<OpeCircuito> listaCircuito;
	private List<OpeCircuito> listaCircuitoMovil;
	// private List<OpeCircuito> circuitosSeleccionados;
	private String[] circuitosSeleccionados;

	private List<Object> listaMonitoreo;
	private List<Object> transaccionFiltrada;

	private List<AdmCliente> listaClienteOrigen;
	private List<AdmAgencia> listaAgenciaOrigen;
	private List<AdmCliente> listaClienteDestino;
	private List<AdmAgencia> listaAgenciaDestino;
	private List<AdmAgencia> agenciaFiltradaO;
	private List<AdmAgencia> agenciaFiltradaD;
	private List<AdmAgencia> agenciasSeleccionadasO;
	private List<AdmAgencia> agenciasSeleccionadasD;

	private List<OpeEquipoMovil> listaEquipoMovil;

	private OpeRutero rutero;

	private float sumaEfectivo = 0;
	private float sumaCheque = 0;
	private float sumaTotalTransaccion = 0;

	private Date fecha;
	private int idCiudad;
	private int idCiudadAO;
	private int idCiudadAD;

	private String dia;
	private int idCircuito;
	private int idEquipoMovil;

	private int idClienteOrigen;
	private int idClienteDestino;

	private double sumaEfectivoTransito;
	private double sumaChequeTransito;
	private double sumaTotalTransito;

	private ExcelOptions excelOpt;

	private PDFOptions pdfOpt;
	
	private PieChartModel pieModel;

	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	@EJB
	private ServicioCircuito servicioCircuito;
	@EJB
	private ServicioRutero servicioRutero;
	@EJB
	private ServicioAdmCliente servicioCliente;
	@EJB
	private ServicioAdmAgencia servicioAgencia;
	@EJB
	private ServicioEquipoMovil servicioEquipoMovil;
	@EJB
	private ServicioTransaccion servicioTransaccion ;

	@PostConstruct
	public void init() {
		cancelar();
		rutero = new OpeRutero();
		fecha = new Date();
		rutero.setFechaOperacion(fecha);

		consultaListaCiudad();
		// consultaListas();
		idCiudad = -1;
		customizationOptions();
		 
		

		//

	}
	
	public void verEstadistica(){
		 pieModel = new PieChartModel();
		 
	        pieModel.set("A tiempo", 90);
	        pieModel.set("Atraso", 10);
	       
	 
	        pieModel.setTitle("A Tiempo / Atrasos");
	        pieModel.setLegendPosition("w");
	        pieModel.setShadow(false);
		
		
	}
	
	
	

	public void customizationOptions() {
		excelOpt = new ExcelOptions();
		excelOpt.setFacetBgColor("#F88017");
		excelOpt.setFacetFontSize("10");
		excelOpt.setFacetFontColor("#0000ff");
		excelOpt.setFacetFontStyle("BOLD");
		// excelOpt.setCellFontColor("#00ff00");
		excelOpt.setCellFontSize("8");

		pdfOpt = new PDFOptions();
		pdfOpt.setFacetBgColor("#F88017");
		pdfOpt.setFacetFontColor("#0000ff");
		pdfOpt.setFacetFontStyle("BOLD");
		pdfOpt.setCellFontSize("12");
	}

	public void cancelar() {

		idCiudadAO = -1;
		idCiudadAD = -1;
		idCircuito = -1;
		idEquipoMovil = -1;

		idClienteOrigen = -1;
		idClienteDestino = -1;

		listaClienteOrigen = new ArrayList<>();
		listaAgenciaOrigen = new ArrayList<>();
		listaClienteDestino = new ArrayList<>();
		listaAgenciaDestino = new ArrayList<>();
		agenciaFiltradaO = new ArrayList<>();
		agenciaFiltradaD = new ArrayList<>();
		agenciasSeleccionadasO = new ArrayList<>();
		agenciasSeleccionadasD = new ArrayList<>();
		
		pieModel = new PieChartModel();

	}

	public void consultaListas() {
		consultaClientesOrigen();
		consultaClientesDestino();
		equiposMovilDisponible();

	}

	public void consultaTotales() {
		sumaEfectivo = 0;
		sumaCheque = 0;
		sumaTotalTransaccion = 0;

		for (int i = 0; i < listaMonitoreo.size(); i++) {
			Object[] row = (Object[]) listaMonitoreo.get(i);
			if (row[10].toString().equals("11")) {
				sumaEfectivo = sumaEfectivo + Float.parseFloat((row[6].toString()));
				sumaCheque = sumaCheque + Float.parseFloat((row[7].toString()));
				sumaTotalTransaccion = sumaTotalTransaccion + Float.parseFloat((row[8].toString()));

			}

		}

	}

	public void consultaClientesOrigen() {
		listaClienteOrigen = new ArrayList<>();
		listaClienteOrigen = servicioCliente.buscaClientePadre2();

	}

	public void consultaClientesDestino() {

		System.out.println("id ciudad en destino " + idCiudadAD);
		setListaClienteDestino(new ArrayList<>());
		setListaClienteDestino(servicioCliente.buscaClientePadre2());
		System.out.println("wwwwwwwwwwww" + listaClienteDestino.size());
	}

	public void consultaAgenciasOrigen() {

		System.out.println("id ciudad en origen " + idCiudadAO);

		setListaAgenciaOrigen(new ArrayList<>());
		setListaAgenciaOrigen(servicioAgencia.buscaAgenciaCiudad(idCiudadAO, idClienteOrigen));
		System.out.println("xxxx" + listaAgenciaOrigen.size());

	}

	public void consultaAgenciasDestino() {

		setListaAgenciaDestino(new ArrayList<>());
		setListaAgenciaDestino(servicioAgencia.buscaAgenciaCiudad(idCiudadAD, idClienteDestino));

	}

	
	
	public void reasignarParada() {
		int a = 0;
		for (Object[] item : transaccionSeleccionada) {

			if (!item[10].toString().equals("0")) { // verfico si el id de la
													// transacion sea diferente
													// de 0

				a += 1;

			}

		}

		if (a >= 1) {

			FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso",
					"Solo se permiten paradas no visitadas"));

		} else {

			if (transaccionSeleccionada.size() == 0) {
				FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Aviso", "Debe seleccionar al menos un registro"));

			} else {

				cancelar();

				equiposMovilDisponible();
				// resetarFormulario();
				RequestContext.getCurrentInstance().execute("PF('dlgReasignarParada').show();");

				
					
				}
				
				
				
				
				
				
				

				
			}
		}
		
		
	
			
			
			
			
		

		

	

	public void agregarParada() {

		cancelar();

		consultaListas();
		// resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgAgregarParada').show();");

	}

	public void guardarParada() {
		// obtengo la fecha y hora corriente
		Timestamp fechaActual;
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());

		if (idCircuito == 0 || idEquipoMovil == 0) {

			FacesContext.getCurrentInstance().addMessage("Aviso",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Seleccione Circuito y Equipo Movil"));

		} else {

			if (agenciasSeleccionadasO.size() == 0 || (agenciasSeleccionadasD.size() == 0)) {
				FacesContext.getCurrentInstance().addMessage("Aviso",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Deber Seleccionar Origen y Destino"));

			} else {

				if ((agenciasSeleccionadasO.size() > 1) && (agenciasSeleccionadasD.size() > 1)) {
					FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Aviso", "Seleccion Multiple en Origen y Destino"));

				} else {

					for (AdmAgencia item : agenciasSeleccionadasO) {

						for (AdmAgencia item2 : agenciasSeleccionadasD) {

							rutero.setIdRutero(servicioRutero.getPK());
							rutero.setFechaAsignacion(fechaActual);
							// rutero.setFechaOperacion(.getFechaOperacion());
							rutero.setIdCircuito(idCircuito);
							rutero.setIdEquipoMovil(idEquipoMovil);
							rutero.setEstadoCliete(0);
							rutero.setSysdelete(0);

							rutero.setIdClienteOrigen(idClienteOrigen);
							rutero.setIdAgenciaOrigen(item.getIdAgencia());
							rutero.setIdClienteDestino(idClienteDestino);
							rutero.setIdAgenciaDestino(item2.getIdAgencia());
							rutero.setIdClienteFactura(idClienteOrigen);

							// buscaFrecuencia();
							servicioRutero.create(rutero);

						}

					}
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('dlgAgregarParada').hide();");

				}

			}

		}

		consultaListaMonitoreo();

	}

	public void guardaReasignarParada() { //reasigna paradas hacia otro equipos moviles
		int a = 0;
		Timestamp fechaActual;
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());

		

				

				String idRutero = null;

				List<String> ci = new ArrayList<String>();

				for (Object[] item : transaccionSeleccionada) {

					ci.add(item[0].toString());
				}
				
				idRutero = String.join(",", ci);
				
				List <OpeRutero> lstRutero = new ArrayList<>();
				lstRutero = servicioRutero.buscaTransaccionReasignar(idRutero);
				
				for(OpeRutero item:lstRutero ){
					item.setIdRutero(servicioRutero.getPK());
					item.setFechaAsignacion(fechaActual);
					item.setIdEquipoMovil(idEquipoMovil);
					item.setIdCircuito(idCircuito);
					
					servicioRutero.create(item);
					
				}
				
				
				servicioRutero.actualizaParada(idRutero);  // elimino las paradas anteriores
				
				
				
				
				
				
				
				

				FacesContext.getCurrentInstance().addMessage("Aviso",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Registros Agregados"));
				
				

				consultaListaMonitoreo();
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dlgReasignarParada').hide();");
			}
		

	

	public static void resetarFormulario() {
		RequestContext.getCurrentInstance().reset("frmCrear");
	}

	public void consultaListaCircuito() {
		circuitosSeleccionados = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
		dia = simpleDateFormat.format(rutero.getFechaOperacion());

		setListaCircuito(new ArrayList<>());
		setListaCircuito(servicioCircuito.buscaCircuitoDiaCiudad(idCiudad, dia));

	}

	public void consultaListaCiudad() {
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuario"); // obtengo el usuario logueado
		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCatalogo.ciudadesAsignadas(usuario);
	}

	public void equiposMovilDisponible() { // busca todos los moviles  disponibles, solo aquellos que tegan una fecha de operacion

		listaEquipoMovil = new ArrayList<>();
		listaEquipoMovil = servicioEquipoMovil.buscaEquipoMovilOperativos(idCiudad, rutero.getFechaOperacion());

	}

	public void consultaCircuitoPorEquipo() { // busca los circit

		setListaCircuitoMovil(new ArrayList<>());
		setListaCircuitoMovil(servicioCircuito.buscaCircuitoPorEquipo(idEquipoMovil, rutero.getFechaOperacion()));
		System.out.println("tamaño " + listaCircuitoMovil.size());

	}

	public void buscarIdRutero(){
		  List <OpeTransaccion> listaTransaccionPO = new ArrayList<>();
		  List <OpeRutero> listaRuteroPO = new ArrayList<>();
		 
		 listaTransaccionPO = servicioTransaccion.buscaTransaccionNoIdRutero(rutero.getFechaOperacion());
		 listaRuteroPO= servicioRutero.buscaAsignaciones(rutero.getFechaOperacion() ,idCiudad);
		 
		 
		 for(OpeRutero r : listaRuteroPO){
			 
			 for(OpeTransaccion t : listaTransaccionPO){
				 
				 if(r.getIdAgenciaOrigen().equals(t.getIdAgenciaOrigen().getIdAgencia())){
					 
					 System.out.println("encontrado"+r.getIdRutero());
					 
					 servicioTransaccion.actualizaIdRutero(t.getIdTransaccion(),r.getIdRutero()); // actualizo el idrutero en la tabla ope_transaccion
					 
				 }
				 
				 
				 
			 }
			 
			 
			 
		 }
		 
		 
		 
		 
		 
		
		
		
	}
	
	
	@SuppressWarnings("null")
	public void consultaListaMonitoreo() {
		buscarIdRutero();
		String concat = Arrays.toString(circuitosSeleccionados);
		concat = concat.substring(1, concat.length() - 1); // concateno los id de los circuitos seleccionados

		listaMonitoreo = servicioRutero.buscaTransaccionMonitoreo(concat, rutero.getFechaOperacion());
		
		consultaTotales();
		

	}

	public void eliminar() { // Elimina las paradas de la tabla rutero (aquellas
								// que no tienen asociada una transaccion o no
								// han sido visitados)

		int a = 0;

		for (Object[] item : transaccionSeleccionada) {

			if (!item[10].toString().equals("0")) { // verfico si el id de la
													// transacion sea diferente
													// de 0

				a += 1;

			}

		}

		if (a >= 1) {

			FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso",
					"Solo se pueden eliminar cliente no visitados"));

		} else {

			if (transaccionSeleccionada.size() == 0) {
				FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Aviso", "Debe seleccionar al menos un registro"));

			} else {

				String citiesCommaSeparated = null;

				List<String> ci = new ArrayList<String>();

				for (Object[] item : transaccionSeleccionada) {

					ci.add(item[0].toString());
				}
				citiesCommaSeparated = String.join(",", ci);
				servicioRutero.eliminaParada(citiesCommaSeparated);

				FacesContext.getCurrentInstance().addMessage("Aviso",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Registros Eliminados"));

				consultaListaMonitoreo();

			}
		}
	}

	public List<OpeRutero> getListaTransaccion() {
		return listaTransaccion;
	}

	public void setListaTransaccion(List<OpeRutero> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}

	public List<Object[]> getTransaccionSeleccionada() {
		return transaccionSeleccionada;
	}

	public void setTransaccionSeleccionada(List<Object[]> transaccionSeleccionada) {
		this.transaccionSeleccionada = transaccionSeleccionada;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public List<OpeCircuito> getListaCircuito() {
		return listaCircuito;
	}

	public void setListaCircuito(List<OpeCircuito> listaCircuito) {
		this.listaCircuito = listaCircuito;
	}

	public List<AdmDetalleCatalogo> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public OpeRutero getRutero() {
		return rutero;
	}

	public void setRutero(OpeRutero rutero) {
		this.rutero = rutero;
	}

	public List<Object> getListaMonitoreo() {
		return listaMonitoreo;
	}

	public void setListaMonitoreo(List<Object> listaMonitoreo) {
		this.listaMonitoreo = listaMonitoreo;
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

	public String[] getCircuitosSeleccionados() {
		return circuitosSeleccionados;
	}

	public void setCircuitosSeleccionados(String[] circuitosSeleccionados) {
		this.circuitosSeleccionados = circuitosSeleccionados;
	}

	public List<AdmAgencia> getAgenciaFiltradaO() {
		return agenciaFiltradaO;
	}

	public void setAgenciaFiltradaO(List<AdmAgencia> agenciaFiltradaO) {
		this.agenciaFiltradaO = agenciaFiltradaO;
	}

	public List<AdmAgencia> getAgenciaFiltradaD() {
		return agenciaFiltradaD;
	}

	public void setAgenciaFiltradaD(List<AdmAgencia> agenciaFiltradaD) {
		this.agenciaFiltradaD = agenciaFiltradaD;
	}

	public List<AdmAgencia> getAgenciasSeleccionadasO() {
		return agenciasSeleccionadasO;
	}

	public void setAgenciasSeleccionadasO(List<AdmAgencia> agenciasSeleccionadasO) {
		this.agenciasSeleccionadasO = agenciasSeleccionadasO;
	}

	public List<AdmAgencia> getAgenciasSeleccionadasD() {
		return agenciasSeleccionadasD;
	}

	public void setAgenciasSeleccionadasD(List<AdmAgencia> agenciasSeleccionadasD) {
		this.agenciasSeleccionadasD = agenciasSeleccionadasD;
	}

	public int getIdClienteOrigen() {
		return idClienteOrigen;
	}

	public void setIdClienteOrigen(int idClienteOrigen) {
		this.idClienteOrigen = idClienteOrigen;
	}

	public int getIdClienteDestino() {
		return idClienteDestino;
	}

	public void setIdClienteDestino(int idClienteDestino) {
		this.idClienteDestino = idClienteDestino;
	}

	public List<AdmCliente> getListaClienteOrigen() {
		return listaClienteOrigen;
	}

	public void setListaClienteOrigen(List<AdmCliente> listaClienteOrigen) {
		this.listaClienteOrigen = listaClienteOrigen;
	}

	public List<AdmAgencia> getListaAgenciaOrigen() {
		return listaAgenciaOrigen;
	}

	public void setListaAgenciaOrigen(List<AdmAgencia> listaAgenciaOrigen) {
		this.listaAgenciaOrigen = listaAgenciaOrigen;
	}

	public List<AdmCliente> getListaClienteDestino() {
		return listaClienteDestino;
	}

	public void setListaClienteDestino(List<AdmCliente> listaClienteDestino) {
		this.listaClienteDestino = listaClienteDestino;
	}

	public List<AdmAgencia> getListaAgenciaDestino() {
		return listaAgenciaDestino;
	}

	public void setListaAgenciaDestino(List<AdmAgencia> listaAgenciaDestino) {
		this.listaAgenciaDestino = listaAgenciaDestino;
	}

	public int getIdCircuito() {
		return idCircuito;
	}

	public void setIdCircuito(int idCircuito) {
		this.idCircuito = idCircuito;
	}

	public List<OpeEquipoMovil> getListaEquipoMovil() {
		return listaEquipoMovil;
	}

	public void setListaEquipoMovil(List<OpeEquipoMovil> listaEquipoMovil) {
		this.listaEquipoMovil = listaEquipoMovil;
	}

	public int getIdEquipoMovil() {
		return idEquipoMovil;
	}

	public void setIdEquipoMovil(int idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}

	public List<OpeCircuito> getListaCircuitoMovil() {
		return listaCircuitoMovil;
	}

	public void setListaCircuitoMovil(List<OpeCircuito> listaCircuitoMovil) {
		this.listaCircuitoMovil = listaCircuitoMovil;
	}

	public int getIdCiudadAD() {
		return idCiudadAD;
	}

	public void setIdCiudadAD(int idCiudadAD) {
		this.idCiudadAD = idCiudadAD;
	}

	public int getIdCiudadAO() {
		return idCiudadAO;
	}

	public void setIdCiudadAO(int idCiudadAO) {
		this.idCiudadAO = idCiudadAO;
	}

	public float getSumaEfectivo() {
		return sumaEfectivo;
	}

	public void setSumaEfectivo(float sumaEfectivo) {
		this.sumaEfectivo = sumaEfectivo;
	}

	public float getSumaCheque() {
		return sumaCheque;
	}

	public void setSumaCheque(float sumaCheque) {
		this.sumaCheque = sumaCheque;
	}

	public float getSumaTotalTransaccion() {
		return sumaTotalTransaccion;
	}

	public void setSumaTotalTransaccion(float sumaTotalTransaccion) {
		this.sumaTotalTransaccion = sumaTotalTransaccion;
	}

	public List<Object> getTransaccionFiltrada() {
		return transaccionFiltrada;
	}

	public void setTransaccionFiltrada(List<Object> transaccionFiltrada) {
		this.transaccionFiltrada = transaccionFiltrada;
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

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	
	

}

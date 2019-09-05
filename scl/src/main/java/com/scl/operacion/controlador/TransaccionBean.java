package com.scl.operacion.controlador;
 

import java.io.File;

import java.io.Serializable;
import java.sql.Connection;

import java.sql.SQLException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;

import com.scl.administracion.modelo.*;

import com.scl.administracion.servicio.*;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.*;


import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;




@ManagedBean
@ViewScoped
public class TransaccionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private OpeTransaccion transaccion;
	private OpeTransaccion trasaccionSeleccionada;
	
	

	private List<OpeTransaccion> listaTransaccion;
	
	
	private OpeRutero  rutero;
	
	
	private OpeParametroGeneral transaccionT;
	
	private List<OpeTransaccion> listaBusqueda;
	private List<OpeTransaccion> resultadoSeleccionado;
	private List<OpeTransaccion> transaccionesSeleccionadas; //almacena lo que el usuario selecciona para la validacion de ingreso
	
	List<OpeFrecuenciaVisita> listaPlanificada; //lista para guadar el resultado de la consulta que vera el delegado para generar una orden
	
	private  OpeFrecuenciaVisita registroSeleccionado;
	

	
	
	
	
	
	private OpeDetalleTransaccion detalleTransaccion;
	private List<OpeDetalleTransaccion> listaDetalleTransaccion;

	
	
	private List<AdmAgencia> listaAgencia;
	

	private List<AdmDetalleCatalogo> listaCiudad;
	
	private int idAgenciaOrigen;
	private int idAgenciaDestino;
	
	
	private int idTransaccion;
	private int idCiudad;

	Integer contIdDetalle;
	long sumaCheque = 0;
	long sumaEfectivo = 0;
	Integer sumaCantidad = 0;

	private Date fechaHasta;
	private Date fechaDesde;
	private Timestamp fechaActual;
	private String dia;
	
private ExcelOptions excelOpt;
    
    private PDFOptions pdfOpt;
	
	
	@Resource(lookup = "java:jboss/postgresDS")
    private DataSource ds;
    
    
	
	@EJB
	private ServicioTransaccion servicioTransaccion ;
	
	@EJB
	private ServicioDetalleTransaccion servicioDetalleTransaccion ;
	@EJB
	private ServicioAdmAgencia servicioAgencia ;
	@EJB
	private ServicioUsuarioCiudad servicioUsuarioCiudad;
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	@EJB
	private ServicioHojaAlistamiento serviciohojaAlistamiento;
	
	@EJB
	private ServicioRutero servicioRutero;
	
	

	@PostConstruct
	public void init()  {
		cancelar();
		transaccion = new OpeTransaccion ();
		trasaccionSeleccionada = new OpeTransaccion ();
		consultaListaCiudad();
		//idCiudad = listaCiudad.get(0).getIdCiudadDc().getIdDetalleCatalogo();
		
		
		 contIdDetalle = 1;
		 
		 customizationOptions();
		 
	
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
		
		
	
	detalleTransaccion = new OpeDetalleTransaccion ();
	listaDetalleTransaccion = new ArrayList<>();
	detalleTransaccion.setCantidadCheques(0);
	
	
	
		
		
	
		idAgenciaOrigen = -1;
		
		idCiudad = -1;
		
		registroSeleccionado = new OpeFrecuenciaVisita();

	}
	

	
	public void consultaPlanificada() {	
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(transaccion.getFechaOperacion());
		
		int dia = (cal.get(Calendar.DAY_OF_WEEK));
		
		 String diaSemana = null;
		
		 switch (dia) {
         case 1:
        	 
        	  diaSemana=("domingo");
        	  System.out.println("dia es "+diaSemana);
             break;
         case 2:
        	  diaSemana=("lunes");
        	  System.out.println("dia es "+diaSemana);
             break;
         case 3:
        	  diaSemana=("martes");
        	  System.out.println("dia es "+diaSemana);
             break;
         case 4:
        	 diaSemana=("miércoles");
        	 System.out.println("dia es "+diaSemana);
             break;
         case 5:
        	 diaSemana=("jueves");
        	 System.out.println("dia es "+diaSemana);
             break;
         case 6:
        	 diaSemana=("viernes");
        	 System.out.println("dia es "+diaSemana);
             break;
         case 7:
        	 diaSemana=("sábado");
        	 System.out.println("dia es "+diaSemana);
     }
		 
		listaPlanificada= new ArrayList<>();
		
		listaPlanificada =serviciohojaAlistamiento.buscaPlanificada(idCiudad, diaSemana, idAgenciaOrigen);
		
	
		
	}
		
	


	
	public void consultaListaAgencia() {
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAgencia.buscaAgenciaCiudad(idCiudad, usuario);
		System.out.println("tamañoxxxx"+listaAgencia.size());
	}
	
	public void consultaListaTransaccion(){
		listaTransaccion = new ArrayList<>();
		listaTransaccion = servicioTransaccion.findAll();
		
	}
	
	public void consultaNumerRemesa(){
		transaccionT = new OpeParametroGeneral ();
		transaccionT = servicioTransaccion.obtenerNumeroRemesa();
	}
	

	
	

	public void agregarFunda(){
		detalleTransaccion.setIdDetalleTransaccion(contIdDetalle);
		listaDetalleTransaccion.add(detalleTransaccion);
		contIdDetalle = contIdDetalle + 1;
		detalleTransaccion = new OpeDetalleTransaccion ();
		
	}
	
	
	

	public void guardar() { // Guarda la transaccion de preorden
		int n = 0;
		
		
		Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
		
		
		if(listaDetalleTransaccion.size()== 0){
			FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No hay datos para guardar"));
			
		}else{
			
			consultaNumerRemesa();
			buscaIdRutero();
			
			
			
	
		
		AdmAgencia ao = new AdmAgencia();
		ao.setIdAgencia(registroSeleccionado.getIdHojaAlistamiento().getIdAgenciaOrigen().getIdAgencia());
		
		AdmAgencia ad = new AdmAgencia();
		ad.setIdAgencia(registroSeleccionado.getIdHojaAlistamiento().getIdAgenciaDestino().getIdAgencia());
		
		AdmCliente co = new AdmCliente();
		co.setIdCliente(registroSeleccionado.getIdHojaAlistamiento().getIdClienteOrigen().getIdCliente());
		transaccion.setIdTransaccion(servicioTransaccion.getPK());
		transaccion.setIdAgenciaOrigen(ao);
		transaccion.setIdAgenciaDestino(ad);
		

			 n = Integer.parseInt(transaccionT.getValor());
			 n = n+1;
			

		String rr = transaccionT.getPrefijo() + n; 
		
		transaccion.setNumeroRecibo(rr);
		transaccion.setGeneradoPor("cliente");
		transaccion.setFechaOperacion(transaccion.getFechaOperacion());
		transaccion.setFechaInicio(fechaActual);
		transaccion.setIdRutero(rutero.getIdRutero());
		transaccion.setIdEquipoMovil(rutero.getIdEquipoMovil());
		transaccion.setIdCircuito(rutero.getIdCircuito());
		
		
		Calendar cal = Calendar.getInstance();
		fechaActual = new Timestamp(cal.getTimeInMillis());
		
		transaccion.setFechaFin(fechaActual);
		transaccion.setTotalEfectivo(sumaEfectivo);
		transaccion.setTotalCheque(sumaCheque);
		transaccion.setTotalTransaccion(sumaEfectivo+sumaCheque);
		transaccion.setTotalPaquetes(listaDetalleTransaccion.size());
		transaccion.setEstadoTransaccion(10);
		transaccion.setIdCliente(co);
		
		servicioTransaccion.create(transaccion);
		
		
		
		guardarDetalle();
		
		servicioTransaccion.actualizaNumeroRemesa(n);
		
		}
		
		

	}
	
	public void guardarDetalle(){ // Guarda el detalle de la transaccion(sellos, tulas, y valores de la funda)
		try {
			
		for(OpeDetalleTransaccion item : listaDetalleTransaccion){
			detalleTransaccion.setIdDetalleTransaccion(servicioDetalleTransaccion.getPK());
			item.setIdDetalleTransaccion(detalleTransaccion.getIdDetalleTransaccion());
			item.setIdTransaccion(transaccion);
			servicioDetalleTransaccion.create(item);
			
			
		}
		
		
			
			
		detalleTransaccion = new OpeDetalleTransaccion ();
		listaDetalleTransaccion = new ArrayList<>();
		
		//FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Datos Guardados"));
		idTransaccion = transaccion.getIdTransaccion();
		cancelar();

		RequestContext.getCurrentInstance().execute("PF('dlgImprimir').show();");


		} catch (Exception e) {
			System.out.println("Se ha producido un error" +e);
		}
		
		
		
	}
	
	public void buscaIdRutero(){
		
		rutero = new OpeRutero();
		
		rutero = servicioRutero.buscaIdRutero(idAgenciaOrigen,transaccion.getFechaOperacion(), registroSeleccionado.getHoraDesde(), registroSeleccionado.getHoraHasta());
		System.out.println("fsfs");
		
		
		
	}
	
	
	public void guardarDetalleTmp(){ // Guarda el detalle de la transaccion(sellos, tulas, y valores de la funda)
		try {
			
		for(OpeDetalleTransaccion item : listaDetalleTransaccion){
			detalleTransaccion.setIdDetalleTransaccion(servicioDetalleTransaccion.getPK());
			item.setIdDetalleTransaccion(detalleTransaccion.getIdDetalleTransaccion());
			item.setIdTransaccion(transaccion);
			servicioDetalleTransaccion.create(item);
			
			
		}
		
		
		detalleTransaccion = new OpeDetalleTransaccion ();
		listaDetalleTransaccion = new ArrayList<>();
		
		//FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Datos Guardados"));
		idTransaccion = transaccion.getIdTransaccion();
		cancelar();

		RequestContext.getCurrentInstance().execute("PF('dlgImprimir').show();");


		} catch (Exception e) {
			System.out.println("Se ha producido un error" +e);
		}
		
		
	}
	
	//////////////////////////////////////////////
	
	
	public void reimprimir (){
		
		if(trasaccionSeleccionada == null){
			FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Debe seleccionar un registro"));
			
			
		}else{
			idTransaccion = trasaccionSeleccionada.getIdTransaccion();
			try {
				imprimirReporte2();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	

	
	public void imprimirReporte2() throws SQLException{
		
		Connection conn = ds.getConnection();
			
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id_transaccion", idTransaccion);
		
		try {
			
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/operaciones/ReciboRemesa.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conn);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReport.pdf");
		ServletOutputStream stream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		FacesContext.getCurrentInstance().responseComplete();
		
		
		
			
		} catch (Exception e) {
			System.out.println("Error al exportar el PDf: " + e);
		}
		
		
		
		
		
		
		
		
		
	}
	
	public double sumarEfectivo(){
		sumaEfectivo = 0;
	
			for(OpeDetalleTransaccion item: listaDetalleTransaccion){
				sumaEfectivo += item.getEfectivo();	
			}
			
			
			
		return sumaEfectivo;
		
	}
	
	
public double sumarCheque(){
	sumaCheque = 0;
	for(OpeDetalleTransaccion item: listaDetalleTransaccion){
		sumaCheque += item.getCheque();	
	}
	
return sumaCheque;
		
		
	}
	
public Integer sumarCantidadCheque(){
	sumaCantidad = 0;
	for(OpeDetalleTransaccion item: listaDetalleTransaccion){
		sumaCantidad += item.getCantidadCheques();	
	}
	
	
	return sumaCantidad;

	
}

public void consultaListaCiudad() {
	AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCatalogo.ciudadesAsignadas(usuario);
	}





public void buscarTransaccion() { // busca e detalle de las transacciones (fundas de las preorden)
	
	AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
	Calendar cal = Calendar.getInstance(); 
	cal.setTime(fechaDesde);
	String d_anio = Integer.toString(cal.get(Calendar.YEAR));
	String d_mes = Integer.toString(cal.get(Calendar.MONTH)+1);
	String d_dia = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
	

	cal.setTime(fechaHasta);
	String h_anio = Integer.toString(cal.get(Calendar.YEAR));
	String h_mes = Integer.toString(cal.get(Calendar.MONTH)+1);
	String h_dia = Integer.toString(cal.get(Calendar.DAY_OF_MONTH)+1);
	
	
	String fechaDesde = d_anio +"-"+ d_mes +"-"+ d_dia;
	String fechaHasta = h_anio +"-"+ h_mes +"-"+ h_dia;
	
	
	
		listaBusqueda = new ArrayList<>();
		try {
			listaBusqueda = servicioTransaccion.buscaTransaccion(fechaDesde, fechaHasta, usuario);
		} catch (ParseException e) {
			System.out.println("se ha producido un error al buscar : "+e.getMessage());
			e.printStackTrace();
		}
		
	}



public void onRowToggle(ToggleEvent event) {
	transaccion =  (OpeTransaccion) event.getData();
	
	listaDetalleTransaccion = new ArrayList<>();
	listaDetalleTransaccion = servicioDetalleTransaccion.buscaDetalle(transaccion.getIdTransaccion());
   
}


	
	
 
	
	public void eliminar() {
		
		System.out.println("sssss");
		listaDetalleTransaccion.remove(detalleTransaccion);
		sumaCheque = 0;
		sumaEfectivo = 0;
		sumaCantidad = 0;		
		
	}


	
	
	
	
	public OpeTransaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(OpeTransaccion transaccion) {
		this.transaccion = transaccion;
	}
	public List<OpeTransaccion> getListaTransaccion() {
		return listaTransaccion;
	}
	public void setListaTransaccion(List<OpeTransaccion> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}
	
	public OpeDetalleTransaccion getDetalleTransaccion() {
		return detalleTransaccion;
	}
	public void setDetalleTransaccion(OpeDetalleTransaccion detalleTransaccion) {
		this.detalleTransaccion = detalleTransaccion;
	}
	public List<OpeDetalleTransaccion> getListaDetalleTransaccion() {
		return listaDetalleTransaccion;
	}
	public void setListaDetalleTransaccion(List<OpeDetalleTransaccion> listaDetalleTransaccion) {
		this.listaDetalleTransaccion = listaDetalleTransaccion;
	}

	public int getIdAgenciaOrigen() {
		return idAgenciaOrigen;
	}
	public void setIdAgenciaOrigen(int idAgenciaOrigen) {
		this.idAgenciaOrigen = idAgenciaOrigen;
	}
	public int getIdAgenciaDestino() {
		return idAgenciaDestino;
	}
	public void setIdAgenciaDestino(int idAgenciaDestino) {
		this.idAgenciaDestino = idAgenciaDestino;
	}
	public List<AdmAgencia> getListaAgencia() {
		return listaAgencia;
	}
	public void setListaAgencia(List<AdmAgencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
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



	public Date getFechaHasta() {
		return fechaHasta;
	}



	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}



	public Date getFechaDesde() {
		return fechaDesde;
	}



	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}



	public List<OpeTransaccion> getListaBusqueda() {
		return listaBusqueda;
	}



	public void setListaBusqueda(List<OpeTransaccion> listaBusqueda) {
		this.listaBusqueda = listaBusqueda;
	}



	public List<OpeTransaccion> getResultadoSeleccionado() {
		return resultadoSeleccionado;
	}



	public void setResultadoSeleccionado(List<OpeTransaccion> resultadoSeleccionado) {
		this.resultadoSeleccionado = resultadoSeleccionado;
	}



	public String getDia() {
		return dia;
	}



	public void setDia(String dia) {
		this.dia = dia;
	}



	


	public List<OpeFrecuenciaVisita> getListaPlanificada() {
		return listaPlanificada;
	}



	public void setListaPlanificada(List<OpeFrecuenciaVisita> listaPlanificada) {
		this.listaPlanificada = listaPlanificada;
	}



	public Object getRegistroSeleccionado() {
		return registroSeleccionado;
	}



	public void setRegistroSeleccionado(OpeFrecuenciaVisita registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}





	

	public List<OpeTransaccion> getTransaccionesSeleccionadas() {
		return transaccionesSeleccionadas;
	}



	public void setTransaccionesSeleccionadas(List<OpeTransaccion> transaccionesSeleccionadas) {
		this.transaccionesSeleccionadas = transaccionesSeleccionadas;
	}



	public OpeParametroGeneral getTransaccionT() {
		return transaccionT;
	}



	public void setTransaccionT(OpeParametroGeneral transaccionT) {
		this.transaccionT = transaccionT;
	}






	public OpeTransaccion getTrasaccionSeleccionada() {
		return trasaccionSeleccionada;
	}



	public void setTrasaccionSeleccionada(OpeTransaccion trasaccionSeleccionada) {
		this.trasaccionSeleccionada = trasaccionSeleccionada;
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









	





	
	
	
	
	

}

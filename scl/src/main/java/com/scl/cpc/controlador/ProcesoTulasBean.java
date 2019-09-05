package com.scl.cpc.controlador;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

import org.primefaces.context.RequestContext;

import com.scl.administracion.controlador.SesionBean;
import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmEmpleado;
import com.scl.administracion.servicio.ServicioIinicioSesion;
import com.scl.cpc.modelo.*;

import com.scl.cpc.servicio.*;

import com.scl.operacion.modelo.OpeDetalleTransaccion;
import com.scl.operacion.servicio.*;
import com.scl.sac.modelo.SacDetalleSolicitudServicio;
import com.scl.sac.modelo.SacSolicitudServicio;


@ManagedBean
@ViewScoped
public class ProcesoTulasBean implements Serializable {
	 private static final long serialVersionUID = 1L;
	
	private Timestamp fechaActual;
	private List<CpcDistribucionCaja>listTrAsignadas;
	private List<CpcDistribucionCaja>transaccionFiltrada;
	private CpcDistribucionCaja trSeleccionada;
	private CpcDistribucionCaja distribucion;
	private List<OpeDetalleTransaccion>listaSellos;
	private CpcDetalleDistribucion detalleDistribucion;
	private CpcPapeleta papeleta;
	private List<CpcPapeleta>listaPapeletas;
	private CpcPapeleta papeletaSeleccionada;
	private CpcNovedad novedadSeleccionada;
	private  CpcDetalleEspecie detalleEspecie;
	private  List<CpcDetalleEspecie> listaDetalleEspecie;
	private CpcNovedad novedad;
	private List<CpcNovedad> listaNovedades;
	private List<CpcBanco> listaBancos;
	private List<CpcTipoNovedad> listaTipoNovedad;
	private int idBanco;
	private int idDetalleTransaccion;
	private int idDetalleTransaccionN;
	private int idPapeleta;
	private int idTipoNovedad;
	private AdmUsuario usuario;

	private float sumaPapeleta;
	private float sumaNovedad;
	private float totalConteo;
	private float diferencia;
	private String identificacion;
	private int idEmpleado;
	private boolean verTblPapeleta;
	private boolean verTblNovedad;
	private List<AdmUsuario> usuariosValidadores;
	
	
	
	//variables billtes cantidad
	int b100;
	int b50;
	int b20;
	int b10;
	int b5;
	int b1;

	//variables monedas cantidad
	
	int m1;
	int m50;
	int m25;
	int m10;
	int m5;
	int m1c;
	
	//subtotal billetes
	int subb100;
	int subb50;
	int subb20;
	int subb10;
	int subb5;
	int subb1;
	
	int totalBillete;
	
	
	
	//subtotal monedas
	int subm1;
	float subm50;
	float subm25;
	float subm10;
	float subm5;
	float subm1c;
	float totalMoneda;
	
	float totalDenominacion;
	
	@EJB
	private ServicioDistribucionCaja servicioDistribucion;
	@EJB
	private ServicioDetalleDistribucion servicioDetalleDistribucion;
	@EJB
	private ServicioDetalleTransaccion servicioDetalleTransaccion;
	@EJB
	private ServicioPapeleta servicioPapeleta;
	@EJB
	private ServicioDetalleEspecie servicioDetalleEspecie;
	@EJB
	private ServicioNovedad servicioNovedad;
	@EJB
	private ServicioTipoNovedad serviciotipoNovedad;
	@EJB
	private ServicioBanco servicioBanco;
	@EJB
	private ServicioAdmEmpleado servicioEmpleado;
	@EJB
	private ServicioIinicioSesion iniciosesion;
	
	
	 @PostConstruct
	    public void init() {
	        cancelar();
	       
			 detalleDistribucion = new CpcDetalleDistribucion();
			 detalleEspecie = new CpcDetalleEspecie();
			 papeleta = new CpcPapeleta();
			 novedad = new  CpcNovedad();
			 distribucion = new CpcDistribucionCaja();
			 verTblPapeleta = false;
			verTblNovedad = false;
	        cosultaTrAsignadas();
	        consultaBancos();
	        trSeleccionada = new CpcDistribucionCaja();
	        trSeleccionada = null;
	        usuario= new AdmUsuario();
	         
	        
  
	       
	    }
	 
	 public void cancelar(){
		 

		 
		 
	 }
	 
 public void cosultaTrAsignadas(){ // consulta las transacciones asignadas al cajero para proceso
	 AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
	 setListTrAsignadas(new ArrayList<>());
	 setListTrAsignadas(servicioDistribucion.buscaTrAsignadaCajero(usuario));
	 	 
	 }
 
 public void cosultaDetalleNR(){ // consulta los numero de sellos de las transacciones asignadas al cajero
	
	 listaSellos = new ArrayList<>();
	 listaSellos = servicioDetalleTransaccion.buscaDetalle(trSeleccionada.getIdTransaccion().getIdTransaccion());
	 
	 
	 
	 
	 
	 
 }
 
 public void procesar(){
	 cosultaDetalleNR();
	 ValidaInicioProcesoTula();
	 consultaTipoNovedad();
	 consultaTotalPapeletas();
	 consultaTotalNovedad();
	 consultaTotalConteo();
	 calculaDiferencia();
	 consultaNovedad();
	
	 
	 
	 
 }
 
 public void ValidaInicioProcesoTula(){ // verifica si ya la tula empezo un proceso para que no vuelva a grabar un detalle de distribucion
	List<CpcDetalleDistribucion> listaDetalle = new ArrayList<>();
	
	listaDetalle = servicioDetalleDistribucion.buscaDetalleDistribucion(trSeleccionada.getIdTransaccion().getNumeroRecibo());
	 if(listaDetalle.isEmpty()){
		 System.out.println("PRIMERA VEZ INICIO PROCESO DE TULA");
		 insertaDetalleDistribucion();
		
		 
		
		
		 
		 
	 }else{
		 System.out.println("ya se inicio un proceso de esta tula");
		 consultaPapeletas();
		 consultaNovedad();
		
		 
		
		 
	 }
	 
	 
	 
 }

 
 public void  insertaDetalleDistribucion (){ // // inserta registros en la tabla detalledistribucion, ingresa tantas filas como sellos tenga la remesa
	 
	 // actualizo la tabla distribucion caja
	 Calendar cali = Calendar.getInstance();
		fechaActual = new Timestamp(cali.getTimeInMillis());
	 
	
		
		servicioDistribucion.actualizaInicioCaja(fechaActual, trSeleccionada.getIdTransaccion().getNumeroRecibo());
		
		

	 
	 
	 
	 for(OpeDetalleTransaccion item : listaSellos){	 //recorro la lista de sellos de la remesa seleccionada para insertar una cantidad igual de filas en cpc_detalle_distribucion
	 detalleDistribucion.setIdDetalleDistribucion(servicioDetalleDistribucion.getPK());
	 detalleDistribucion.setId_distribucion_caja(trSeleccionada);
	 detalleDistribucion.setTotalEfectivo(item.getEfectivo());
	 detalleDistribucion.setEstado(0);
	 detalleDistribucion.setIdDetalleTransaccion(item);
	 servicioDetalleDistribucion.create(detalleDistribucion);
	 }
	 
	 consultaPapeletas();
	 
	 
 }
 
 public void guardarPapeleta(){
	 int id = 0;
	 CpcBanco banco = new CpcBanco();
	 banco.setIdBanco(idBanco);
	 
	 CpcDetalleDistribucion detatlleDistribucion = new  CpcDetalleDistribucion();
	  id = servicioDetalleDistribucion.findOne(idDetalleTransaccion); 
	  detatlleDistribucion.setIdDetalleDistribucion(id);
	  
	 papeleta.setIdPapeleta(servicioPapeleta.getPK());
	 papeleta.setId_banco(banco);
	 papeleta.setIdDetalleDistribucion(detatlleDistribucion);
	 servicioPapeleta.create(papeleta);
	 consultaPapeletas();
	 consultaTotalPapeletas();
	calculaDiferencia();
	 
	 papeleta = new CpcPapeleta();
	 verTblPapeleta = true;
		  
 }
 
 public void consultaBancos(){
	 listaBancos = new ArrayList<>();
	 listaBancos = servicioBanco.buscaBancos();
	 
	 
	 
	 
 }
 
 
 public void consultaPapeletas(){
	 listaPapeletas = new ArrayList<>();
	 listaPapeletas = servicioPapeleta.buscaPapeletas(trSeleccionada.getIdTransaccion().getNumeroRecibo());
	 if(listaPapeletas.isEmpty()){
		 verTblPapeleta = false; 
	 }else {
		 
		 verTblPapeleta = true;
	 }
	 
 }
 
 
 public void cuadrarCaja(){
	 
	 if(trSeleccionada == null ){
		 
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Seleccione primero una transaccion previamente asignada " ));
		 
		 
		 
		 
	 } else{
	 
	 
	 if(diferencia != 0){
		 
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "CAJA NO CUADRA, REVISE VALORES" ));
		 
		 
	 }else{
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "CAJA CUADRADA" ));
		 actualizaDistribucionCaja();
		 diferencia = 0;
		 sumaNovedad = 0;
		 sumaPapeleta = 0;
		 totalConteo = 0;
		 idBanco = -1;
		 idDetalleTransaccion = -1;
		 novedad = new CpcNovedad();
		 listaNovedades = new ArrayList<>();
		 detalleEspecie = new CpcDetalleEspecie();
		 papeleta = new CpcPapeleta();
		 listaPapeletas = new ArrayList<>();
		 trSeleccionada = new CpcDistribucionCaja();
		 cosultaTrAsignadas(); //vuelvo a consultar la lista de transacciones asignadas
		 
		 
	 }
	 
	 }
	 
 }
 
public void actualizaDistribucionCaja(){ // actualiza la tabla despue de que la caja ha cuadrado
	Calendar cali = Calendar.getInstance();
	fechaActual = new Timestamp(cali.getTimeInMillis());
	servicioDistribucion.actualizaCuadre(fechaActual, trSeleccionada.getIdTransaccion().getNumeroRecibo());
	
	
}
 
 
 
 
 public void calculaDiferencia(){
	 diferencia = sumaNovedad  + sumaPapeleta - totalConteo ;
	 System.out.println("difererencia ess  "+diferencia);
	 
	
	 
	 
 }

 public void consultaTotalPapeletas(){
	 
	
	 
	 sumaPapeleta = servicioPapeleta.findOne(trSeleccionada.getIdTransaccion().getNumeroRecibo()); 
	 
	 
	
 
	 
 }
 
 public void consultaTotalNovedad(){
	 
	 
	 sumaNovedad= servicioNovedad.findOne(trSeleccionada.getIdTransaccion().getNumeroRecibo()); 
	 
	 
		
 
	 
 }
 
 
 
 
 public void consultaTotalConteo(){
	 totalConteo =  servicioDetalleEspecie.findOne(trSeleccionada.getIdTransaccion().getNumeroRecibo());
	 

 }
 


 public void consultaNovedad(){
	 listaNovedades = new ArrayList<>();
	 listaNovedades = servicioNovedad.buscaNovedad(trSeleccionada.getIdTransaccion().getNumeroRecibo());
	 
	 if(listaNovedades.isEmpty()){
		 verTblNovedad = false; 
	 }else {
		 
		 verTblNovedad = true;
	 }
			 
	 
	 
 }
 
 
 public void consultaTipoNovedad(){
	 listaTipoNovedad = new ArrayList<>();
	 listaTipoNovedad = serviciotipoNovedad.buscaTipoNovedad();
	 
			 
	 
	 
 }
 
 public void guardaDetalleEspecie(){
	 if(idDetalleTransaccion == 0){
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "NO hay sello Seleccionado " ));
		 
		 
	 }else{
		 
		 
		 

	 
	 
	 
	 // guardo el detalle de las denominaciones 
	    	CpcDetalleEspecie detalle;
	    	List <CpcDetalleEspecie> lista = new ArrayList<>();
	    	
	    	
	    	
 		//billetes
	    CpcEspecie id1=	new CpcEspecie();
	    id1.setIdEspecie(1);	    	
 		detalle = new CpcDetalleEspecie();
 		detalle.setIdEspecie(id1);
 		detalle.setCantidad(b100);
 		detalle.setSubtotal(subb100);
 		lista.add(detalle);
 		
 		 CpcEspecie id2=	new CpcEspecie();
 		id2.setIdEspecie(2);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id2);
 		 detalle.setCantidad(b50);
 		 detalle.setSubtotal(subb50);
 		 lista.add(detalle);
 		 
 		 CpcEspecie id3=	new CpcEspecie();
 		id3.setIdEspecie(3);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id3);
 		 detalle.setCantidad(b20);
 		 detalle.setSubtotal(subb20);
 		 lista.add(detalle);
 		 
 		 CpcEspecie id4 =	new CpcEspecie();
 		id4.setIdEspecie(4);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id4);
 		 detalle.setCantidad(b10);
 		 detalle.setSubtotal(subb10);
 		 lista.add(detalle);
 		 
 		 CpcEspecie id5=	new CpcEspecie();
 		id5.setIdEspecie(5);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id5);
 		 detalle.setCantidad(b5);
 		 detalle.setSubtotal(subb5);
 		 lista.add(detalle);
 		 
 		 CpcEspecie id6=	new CpcEspecie();
 		id6.setIdEspecie(6);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id6);
 		 detalle.setCantidad(b1);
 		 detalle.setSubtotal(subb1);
 		 lista.add(detalle);
 		 
 		 
 		////monedas 
 		 CpcEspecie id7=	new CpcEspecie();
 		id7.setIdEspecie(7);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id7);
 		 detalle.setCantidad(m1);
 		 detalle.setSubtotal(subm1);
 		 lista.add(detalle);
 		 
 		 CpcEspecie id8=	new CpcEspecie();
 		id8.setIdEspecie(8);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id8);
 		 detalle.setCantidad(m50);
 		 detalle.setSubtotal(subm50);
 		 lista.add(detalle);
 		
 		 CpcEspecie id9 =	new CpcEspecie();
 		id9.setIdEspecie(9);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id9);
 		 detalle.setCantidad(m25);
 		 detalle.setSubtotal(subm25);
 		 lista.add(detalle);
 		 
 		CpcEspecie id10 =	new CpcEspecie();
 		id10.setIdEspecie(10);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id10);
 		 detalle.setCantidad(m10);
 		 detalle.setSubtotal(subm10);
 		 lista.add(detalle);
 		
 		CpcEspecie id11 =	new CpcEspecie();
 		id11.setIdEspecie(11);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id11);
 		 detalle.setCantidad(m5);
 		 detalle.setSubtotal(subm5);
 		 lista.add(detalle);
 		 
 		CpcEspecie id12 =	new CpcEspecie();
 		id12.setIdEspecie(12);
 		 detalle = new CpcDetalleEspecie();
 		 detalle.setIdEspecie(id12);
 		 detalle.setCantidad(m1c);
 		 detalle.setSubtotal(subm1c);
 		 lista.add(detalle);
 
 		 try {
 			int idDetalle = 0;
 			 
 			 for(CpcDetalleEspecie item :lista){
 				 System.out.println("xxxxx"+item.getIdEspecie().getIdEspecie());
 				 // busco el detalledistribucion del sello seleccionado
 				CpcDetalleDistribucion detatlleDistribucion = new  CpcDetalleDistribucion();
 				idDetalle = servicioDetalleDistribucion.findOne(idDetalleTransaccion); 
 				  detatlleDistribucion.setIdDetalleDistribucion(idDetalle);

	    			detalleEspecie.setIdDetalleEspecie(servicioDetalleEspecie.getPK());
	    			detalleEspecie.setIdDetalleDistribucion(detatlleDistribucion); // se crea una consulta adicional
	    			detalleEspecie.setIdEspecie(item.getIdEspecie());
	    			detalleEspecie.setCantidad(item.getCantidad());
	    			detalleEspecie.setSubtotal(item.getSubtotal());
	    			//detalleEspecie.setTotal(item.getCantidad() * item.getSubtotal());
	    			
	 		    	
	 		    	
	 		    	servicioDetalleEspecie.create(detalleEspecie);
	    			
	    			 
	    		 }
 			FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha guardado con exito " ));
 			 
	    		 limpiar();
	    		 consultaTotalConteo();
	    		 calculaDiferencia ();
	    		 
		    	
 			 
 			 
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("exito",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Se ha producido un error al guardar " + e));
			}
 		 
	 }
 		 
	
 }
 
 public void subTotalBillete(){
 	subb100 = b100 * 100;
 	subb50 = b50 * 50;
 	subb20 = b20 * 20;
 	subb10 = b10 * 10;
 	subb5 = b5 * 5;
 	subb1 = b1 * 1;
 	totalBillete = subb100 +  subb50 + subb20 + subb10 +  subb5 + subb1 ;
 	totalDenominacion = totalBillete + totalMoneda;
 		
 	
 }
 
public void subTotalMoneda(){
subm1 = m1 * 1;
subm50 = (float) (m50 * 0.50);
subm25 = (float) (m25 * 0.25) ;
subm10 = (float) (m10 * 0.10);
subm5 = (float) (m5 * 0.05);
subm1c = (float) (m1c * 0.01);

totalMoneda = subm1 +  subm50 + subm25 + subm10 +  subm5 + subm1c ;
totalDenominacion = totalBillete + totalMoneda;
 	
 	
 }
 
 public void limpiar(){
 	//variables billtes cantidad
		 b100 = 0;
		 b50 = 0;
		 b20 = 0;
		 b10 = 0;
		 b5 = 0;
		 b1 = 0;
		
		
	
		//variables monedas cantidad
		
		 m1 = 0;
		 m50 = 0;
		 m25 = 0;
		 m10 = 0;
		 m5 = 0;
		 m1c = 0;
		
		//subtotal billetes
		 subb100 = 0;
		 subb50 = 0;
		 subb20 = 0;
		 subb10 = 0;
		 subb5 = 0;
		 subb1= 0;
		
		 totalBillete= 0;
		
		
		
		//subtotal monedas
		 subm1 = 0;
		 subm50= 0;
		 subm25= 0;
		 subm10= 0;
		 subm5= 0;
		 subm1c= 0;
		 totalMoneda= 0;
		
		 totalDenominacion= 0;
 	
 	
 }
 
 
 @SuppressWarnings("static-access")
public void validarNovedad(){
	SesionBean sesion = new  SesionBean ();
	
	 
	 usuario.setPassword(sesion.encriptarSHA512(usuario.getPassword()));
		AdmUsuario us;
		
		System.out.println("jjjj"+usuario.getUsuario());
		
		
		
			us = iniciosesion.iniciarSesion(usuario);
			if (us != null) { //compruebo que el usuario exista
				System.out.println("xxxxxxx");
				RequestContext.getCurrentInstance().execute("PF('dlgNovedad').hide();");
				guardaNovedad();
				consultaNovedad();
				consultaTotalNovedad();
				
			} else {
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales incorrectas", null));
			}
			
		
	
	 
	
 }

 
 
 public void cargaUsuariosValidadadores(){ // Busca todos los usuarios que tiengan el rol de Superviores o Jefes de CPC
	 usuariosValidadores = new ArrayList<>();
	 usuariosValidadores = servicioEmpleado.buscaUsuariosValidadores();
	
	 
	 
 }
 
 public void guardaNovedad(){
	 
	 
	
	 
	 int id = 0;
	 CpcPapeleta pa = new CpcPapeleta();
	 pa.setIdPapeleta(idPapeleta);
	 CpcTipoNovedad tn = new  CpcTipoNovedad();
	 tn.setIdTipoNovedad(idTipoNovedad);
	 
	 CpcDetalleDistribucion detatlleDistribucion = new  CpcDetalleDistribucion();
	  id = servicioDetalleDistribucion.findOne(idDetalleTransaccionN); 
	  detatlleDistribucion.setIdDetalleDistribucion(id);
	  
	 
	 
	 novedad.setIdNovedad(servicioNovedad.getPK());
	 novedad.setIdPapeleta(pa);
	 novedad.setIdTipoNovedad(tn);
	 novedad.setIdDetalleDistribucion(detatlleDistribucion);
	 servicioNovedad.create(novedad);
	 
	 consultaNovedad(); //consulto la lita de nuevo
	 
	 consultaTotalNovedad(); //consulto el total de las novedades
	 calculaDiferencia(); // calculo la diferencia de la caja
	 novedad = new CpcNovedad();
	 idDetalleTransaccionN = -1;
	 idPapeleta = -1;
	 idTipoNovedad = -1;
	 verTblNovedad = true;
	 
	 
	 
 }
 

 
 public void consultaDetalleEspecie(){ // busca el detalle de las denominaciones previamente ingresadas
	 
	 
	 
 }
 
public void nuevaNovedad(){
	 
	 if(trSeleccionada == null){
		 
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Seleccione primero una transaccion previamente asignada " ));
		 
	 }else {
		 cargaUsuariosValidadadores();
		 RequestContext.getCurrentInstance().reset("frmNovedad");
		 RequestContext.getCurrentInstance().execute("PF('dlgNovedad').show();");
		 
		 
	 }
	 
	 
	 
	 
 }
 
 
 
 
 public void nuevaPapeleta(){
	
	 
	 if(trSeleccionada == null ){
		 
		
		 
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Seleccione primero una transaccion previamente asignada " ));
		 
		 
		 
	 }else {
		 
		 
		 
		 RequestContext.getCurrentInstance().reset("frmPapeletas");
		 RequestContext.getCurrentInstance().execute("PF('dlgPapeletas').show();");
		 
		 
	 }
		 
		 
	
	 
	 
	 
	 
 }
 
 public void eliminarPapeleta (){ // Elimina una papaleta
	 if(papeletaSeleccionada == null){
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No hay Papeleta Seleccionado " ));
			
		 
		 
	 }else{
		 
		 servicioPapeleta.eliminarPapeleta(papeletaSeleccionada.getIdPapeleta());
		 consultaPapeletas();
		 
		 
	 }
	 
	 
	 
 }
 
 
 public void eliminarNovedad (){ // Elimina una novedad
	 if(novedadSeleccionada == null){
		 FacesContext.getCurrentInstance().addMessage("Aviso",	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No hay Novedad Seleccionado " ));
			
		 
		 
	 }else{
		 
		 servicioNovedad.eliminarNovedad(novedadSeleccionada.getIdNovedad());
		 consultaNovedad();
		 consultaTotalNovedad();
		 calculaDiferencia();
		 
		 
	 }
	 
	 
	 
 }
 
 
 
 
 
	 
	 
	 
	 
	 
	 
	 
	 
///////////////////GETTER AND SETTERS/////////////////////
	public Timestamp getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Timestamp fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<CpcDistribucionCaja> getListTrAsignadas() {
		return listTrAsignadas;
	}

	public void setListTrAsignadas(List<CpcDistribucionCaja> listTrAsignadas) {
		this.listTrAsignadas = listTrAsignadas;
	}

	public CpcDistribucionCaja getTrSeleccionada() {
		return trSeleccionada;
	}

	public void setTrSeleccionada(CpcDistribucionCaja trSeleccionada) {
		this.trSeleccionada = trSeleccionada;
	}

	public List<OpeDetalleTransaccion> getListaSellos() {
		return listaSellos;
	}

	public void setListaSellos(List<OpeDetalleTransaccion> listaSellos) {
		this.listaSellos = listaSellos;
	}

	public List<CpcPapeleta> getListaPapeletas() {
		return listaPapeletas;
	}

	public void setListaPapeletas(List<CpcPapeleta> listaPapeletas) {
		this.listaPapeletas = listaPapeletas;
	}

	public CpcPapeleta getPapeletaSeleccionada() {
		return papeletaSeleccionada;
	}

	public void setPapeletaSeleccionada(CpcPapeleta papeletaSeleccionada) {
		this.papeletaSeleccionada = papeletaSeleccionada;
	}

	public CpcPapeleta getPapeleta() {
		return papeleta;
	}

	public void setPapeleta(CpcPapeleta papeleta) {
		this.papeleta = papeleta;
	}

	public CpcNovedad getNovedad() {
		return novedad;
	}

	public void setNovedad(CpcNovedad novedad) {
		this.novedad = novedad;
	}

	public List<CpcBanco> getListaBancos() {
		return listaBancos;
	}

	public void setListaBancos(List<CpcBanco> listaBancos) {
		this.listaBancos = listaBancos;
	}

	public int getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(int idBanco) {
		this.idBanco = idBanco;
	}

	public int getIdDetalleTransaccion() {
		return idDetalleTransaccion;
	}

	public void setIdDetalleTransaccion(int idDetalleTransaccion) {
		this.idDetalleTransaccion = idDetalleTransaccion;
	}

	public int getB100() {
		return b100;
	}

	public void setB100(int b100) {
		this.b100 = b100;
	}

	public int getB50() {
		return b50;
	}

	public void setB50(int b50) {
		this.b50 = b50;
	}

	public int getB20() {
		return b20;
	}

	public void setB20(int b20) {
		this.b20 = b20;
	}

	public int getB10() {
		return b10;
	}

	public void setB10(int b10) {
		this.b10 = b10;
	}

	public int getB5() {
		return b5;
	}

	public void setB5(int b5) {
		this.b5 = b5;
	}

	public int getB1() {
		return b1;
	}

	public void setB1(int b1) {
		this.b1 = b1;
	}

	public int getM1() {
		return m1;
	}

	public void setM1(int m1) {
		this.m1 = m1;
	}

	public int getM50() {
		return m50;
	}

	public void setM50(int m50) {
		this.m50 = m50;
	}

	public int getM25() {
		return m25;
	}

	public void setM25(int m25) {
		this.m25 = m25;
	}

	public int getM10() {
		return m10;
	}

	public void setM10(int m10) {
		this.m10 = m10;
	}

	public int getM5() {
		return m5;
	}

	public void setM5(int m5) {
		this.m5 = m5;
	}

	public int getM1c() {
		return m1c;
	}

	public void setM1c(int m1c) {
		this.m1c = m1c;
	}

	public int getSubb100() {
		return subb100;
	}

	public void setSubb100(int subb100) {
		this.subb100 = subb100;
	}

	public int getSubb50() {
		return subb50;
	}

	public void setSubb50(int subb50) {
		this.subb50 = subb50;
	}

	public int getSubb20() {
		return subb20;
	}

	public void setSubb20(int subb20) {
		this.subb20 = subb20;
	}

	public int getSubb10() {
		return subb10;
	}

	public void setSubb10(int subb10) {
		this.subb10 = subb10;
	}

	public int getSubb5() {
		return subb5;
	}

	public void setSubb5(int subb5) {
		this.subb5 = subb5;
	}

	public int getSubb1() {
		return subb1;
	}

	public void setSubb1(int subb1) {
		this.subb1 = subb1;
	}

	public int getSubm1() {
		return subm1;
	}

	public void setSubm1(int subm1) {
		this.subm1 = subm1;
	}

	public float getSubm50() {
		return subm50;
	}

	public void setSubm50(float subm50) {
		this.subm50 = subm50;
	}

	public float getSubm25() {
		return subm25;
	}

	public void setSubm25(float subm25) {
		this.subm25 = subm25;
	}

	public float getSubm10() {
		return subm10;
	}

	public void setSubm10(float subm10) {
		this.subm10 = subm10;
	}

	public float getSubm5() {
		return subm5;
	}

	public void setSubm5(float subm5) {
		this.subm5 = subm5;
	}

	public float getSubm1c() {
		return subm1c;
	}

	public void setSubm1c(float subm1c) {
		this.subm1c = subm1c;
	}

	public int getTotalBillete() {
		return totalBillete;
	}

	public void setTotalBillete(int totalBillete) {
		this.totalBillete = totalBillete;
	}

	public float getTotalMoneda() {
		return totalMoneda;
	}

	public void setTotalMoneda(float totalMoneda) {
		this.totalMoneda = totalMoneda;
	}

	public float getTotalDenominacion() {
		return totalDenominacion;
	}

	public void setTotalDenominacion(float totalDenominacion) {
		this.totalDenominacion = totalDenominacion;
	}

	public List<CpcNovedad> getListaNovedades() {
		return listaNovedades;
	}

	public void setListaNovedades(List<CpcNovedad> listaNovedades) {
		this.listaNovedades = listaNovedades;
	}

	public CpcNovedad getNovedadSeleccionada() {
		return novedadSeleccionada;
	}

	public void setNovedadSeleccionada(CpcNovedad novedadSeleccionada) {
		this.novedadSeleccionada = novedadSeleccionada;
	}

	public int getIdDetalleTransaccionN() {
		return idDetalleTransaccionN;
	}

	public void setIdDetalleTransaccionN(int idDetalleTransaccionN) {
		this.idDetalleTransaccionN = idDetalleTransaccionN;
	}

	public int getIdPapeleta() {
		return idPapeleta;
	}

	public void setIdPapeleta(int idPapeleta) {
		this.idPapeleta = idPapeleta;
	}

	public int getIdTipoNovedad() {
		return idTipoNovedad;
	}

	public void setIdTipoNovedad(int idTipoNovedad) {
		this.idTipoNovedad = idTipoNovedad;
	}

	public List<CpcTipoNovedad> getListaTipoNovedad() {
		return listaTipoNovedad;
	}

	public void setListaTipoNovedad(List<CpcTipoNovedad> listaTipoNovedad) {
		this.listaTipoNovedad = listaTipoNovedad;
	}

	public float getSumaPapeleta() {
		return sumaPapeleta;
	}

	public void setSumaPapeleta(float sumaPapeleta) {
		this.sumaPapeleta = sumaPapeleta;
	}

	public float getSumaNovedad() {
		return sumaNovedad;
	}

	public void setSumaNovedad(float sumaNovedad) {
		this.sumaNovedad = sumaNovedad;
	}

	public float getTotalConteo() {
		return totalConteo;
	}

	public void setTotalConteo(float totalConteo) {
		this.totalConteo = totalConteo;
	}

	public float getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(float diferencia) {
		this.diferencia = diferencia;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public boolean isVerTblPapeleta() {
		return verTblPapeleta;
	}

	public void setVerTblPapeleta(boolean verTblPapeleta) {
		this.verTblPapeleta = verTblPapeleta;
	}

	public boolean isVerTblNovedad() {
		return verTblNovedad;
	}

	public void setVerTblNovedad(boolean verTblNovedad) {
		this.verTblNovedad = verTblNovedad;
	}

	public List<CpcDetalleEspecie> getListaDetalleEspecie() {
		return listaDetalleEspecie;
	}

	public void setListaDetalleEspecie(List<CpcDetalleEspecie> listaDetalleEspecie) {
		this.listaDetalleEspecie = listaDetalleEspecie;
	}

	public List<CpcDistribucionCaja> getTransaccionFiltrada() {
		return transaccionFiltrada;
	}

	public void setTransaccionFiltrada(List<CpcDistribucionCaja> transaccionFiltrada) {
		this.transaccionFiltrada = transaccionFiltrada;
	}

	

	public List<AdmUsuario> getUsuariosValidadores() {
		return usuariosValidadores;
	}

	public void setUsuariosValidadores(List<AdmUsuario> usuariosValidadores) {
		this.usuariosValidadores = usuariosValidadores;
	}

	public AdmUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(AdmUsuario usuario) {
		this.usuario = usuario;
	}

	
	

}

package com.scl.operacion.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmAgencia;
import com.scl.administracion.servicio.ServicioAdmCliente;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.administracion.servicio.ServicioUsuarioCiudad;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.ServicioFrecuenciaVisita;
import com.scl.operacion.servicio.ServicioHojaAlistamiento;

@ManagedBean
@ViewScoped
public class HojaAlistamientoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private OpeHojaAlistamiento hojaAlistamiento;
	private OpeHojaAlistamiento hojaSeleccionada;
	private List<OpeHojaAlistamiento> listaHojaAlistamiento;
	private List<OpeHojaAlistamiento> hojaFiltrada;

	private List<AdmCliente> listaCliente;
	private List<AdmAgencia> listaAgencia;

	private List<AdmDetalleCatalogo> listaCiudad;
	
	private OpeFrecuenciaVisita frecuencia;	
	private List<OpeFrecuenciaVisita> listaFrecuencia;
	private List<OpeFrecuenciaVisita> frecuenciaSeleccionada;
	private List<OpeFrecuenciaVisita> frecuencias;

	private int idClienteOrigen;
	private int idClienteDestino;
	private int idAgenciaOrigen;
	private int idAgenciaDestino;
	private String dia;
	private String horaDesde;
	private String horaHasta;

	private int idhojaAlistamientoOrigen;
	private int idhojaAlistamientoDestino;

	private int idCiudad;
	private int ciudad;
	
	private int cont;

	@EJB
	private ServicioHojaAlistamiento servicioHojaAlistamiento;
	@EJB
	private ServicioAdmAgencia servicioAgencia;

	@EJB
	private ServicioAdmCliente servicioCliente;
	@EJB
	private ServicioHojaAlistamiento serviciohojaAlistamiento;
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	@EJB
	private ServicioUsuarioCiudad servicioUsuarioCiudad;
	@EJB
	private ServicioFrecuenciaVisita servicioFrecuencia;

	@PostConstruct
	public void init() {
		cancelar();
		consultaListaCiudad();
		consultaListaCliente();

	}

	public void cancelar() {
		hojaAlistamiento = new OpeHojaAlistamiento();
		frecuencia = new OpeFrecuenciaVisita();
		frecuencias = new ArrayList<>();
		

		
		idClienteOrigen = -1;
		idClienteDestino = -1;
		idAgenciaOrigen = -1;
		idAgenciaDestino = -1;
		idCiudad = -1;
		cont = 1;
		horaDesde = null;
		horaHasta = null;
		dia = null;

	}
	

	public void consultaListaCiudad() {
		AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuario"); // obtengo el usuario logueado
		listaCiudad = new ArrayList<>();
		listaCiudad = servicioDetalleCatalogo.ciudadesAsignadas(usuario);
	}

	public void consultaListaCliente() {
		listaCliente = new ArrayList<>();
		listaCliente = servicioCliente.buscaClientePadre();

	}

	public void consultaListahojaAlistamiento() {
		setListaHojaAlistamiento(new ArrayList<>());
		setListaHojaAlistamiento(servicioHojaAlistamiento.buscaHojaCliente(idClienteOrigen, ciudad));

	}



	public void asignaCiudad() {

		ciudad = idCiudad;
		idClienteOrigen = -1;
		idClienteDestino = -1;
		listaAgencia = new ArrayList<>();

		

	}
	
	public void agregarFrecuencia(){
		
		frecuencia.setIdFrecuenciaVisita(cont);
		frecuencia.setNombreDia(dia);
		frecuencia.setHoraDesde(horaDesde);
		frecuencia.setHoraHasta(horaHasta);

		frecuencias.add(frecuencia);
		cont = cont + 1;
		frecuencia = new OpeFrecuenciaVisita();
		
		
		
		
		
	}
	
	
	
	public void eliminarFrecuencia() {
		

		for(OpeFrecuenciaVisita item: frecuenciaSeleccionada){
			servicioFrecuencia.elimina(item.getIdFrecuenciaVisita());
			//servicioFrecuencia.delete(item);
				
		}
		consultaListaFrecuencia();
		

	}
	
	

	public void consultaListaAgenciaOrigen() {

		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAgencia.buscaAgenciaCiudad(ciudad, idClienteOrigen);
		consultaListahojaAlistamiento();

	}

	public void consultaListaAgenciaDestino() {

		listaAgencia = new ArrayList<>();
		listaAgencia = servicioAgencia.buscaAgenciaCiudad(ciudad, idClienteDestino);

	}
	
	
	public void consultaListaFrecuencia() {

		listaFrecuencia = new ArrayList<>();
		listaFrecuencia = servicioFrecuencia.buscaFrecuenciaAgencia(hojaAlistamiento.getIdHojaAlistamiento());
	}
	
	
	/*
	public void guardarFrecuencia(){
		
		
		try {
			
			for(OpeFrecuenciaVisita item: frecuencias){
				
				this.frecuencia.setIdFrecuenciaVisita(servicioFrecuencia.getPK());
				frecuencia.setHoraDesde(item.getHoraDesde());
				frecuencia.setHoraHasta(item.getHoraHasta());
				frecuencia.setIdHojaAlistamiento(hojaAlistamiento);
				frecuencia.setNombreDia(item.getNombreDia());
				servicioFrecuencia.create(frecuencia);
				
				
				
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}  */
	
public void guardarFrecuencia(){
		
		
		try {
			
			
				
				this.frecuencia.setIdFrecuenciaVisita(servicioFrecuencia.getPK());
				frecuencia.setHoraDesde(horaDesde);
				frecuencia.setHoraHasta(horaHasta);
				frecuencia.setIdHojaAlistamiento(hojaAlistamiento);
				frecuencia.setNombreDia(dia);
				servicioFrecuencia.create(frecuencia);
				
				consultaListaFrecuencia();
				
				
				
				
			
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
	
	
	   
    
    public void guardar() {
    		    	
    	AdmCliente clienteOrigen = new AdmCliente();
    	clienteOrigen.setIdCliente(this.idClienteOrigen);
    	
    	AdmCliente clienteDestino = new AdmCliente();
    	clienteDestino.setIdCliente(this.idClienteDestino);
    	
    	
    	AdmAgencia agenciaOrigen = new AdmAgencia();
    	agenciaOrigen.setIdAgencia(this.idAgenciaOrigen);
    	
    	AdmAgencia agenciaDestino = new AdmAgencia();
    	agenciaDestino.setIdAgencia(this.idAgenciaDestino);
    	
    	AdmDetalleCatalogo ciudad = new AdmDetalleCatalogo();
    	ciudad.setIdDetalleCatalogo(idCiudad);
    	
    	
    	

        try {
        	this.hojaAlistamiento.setIdHojaAlistamiento(serviciohojaAlistamiento.getPK());	
        	this.hojaAlistamiento.setIdClienteOrigen(clienteOrigen); 
        	this.hojaAlistamiento.setIdClienteDestino(clienteDestino);
        	this.hojaAlistamiento.setIdAgenciaOrigen(agenciaOrigen);
        	this.hojaAlistamiento.setIdAgenciaDestino(agenciaDestino);
        	this.hojaAlistamiento.setIdCiudad(ciudad);
        
        	
        	
        	serviciohojaAlistamiento.create(this.hojaAlistamiento);
        	
        	
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Aviso", "Se ha guardado don exito "));
           
            consultaListahojaAlistamiento();
            cancelar();
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

        }

    }
    
    
    public void modificar(){
    	if(hojaSeleccionada == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
		
		hojaAlistamiento = hojaSeleccionada;
		
		
		}
    	
    	
    	
    	
    	
    	
    }
    
    
  
	

	public OpeHojaAlistamiento getHojaAlistamiento() {
		return hojaAlistamiento;
	}

	public void setHojaAlistamiento(OpeHojaAlistamiento hojaAlistamiento) {
		this.hojaAlistamiento = hojaAlistamiento;
	}

	public OpeHojaAlistamiento getHojaSeleccionada() {
		return hojaSeleccionada;
	}

	public void setHojaSeleccionada(OpeHojaAlistamiento hojaSeleccionada) {
		this.hojaSeleccionada = hojaSeleccionada;
	}

	public List<OpeHojaAlistamiento> getHojaFiltrada() {
		return hojaFiltrada;
	}

	public void setHojaFiltrada(List<OpeHojaAlistamiento> hojaFiltrada) {
		this.hojaFiltrada = hojaFiltrada;
	}

	public List<AdmCliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<AdmCliente> listaCliente) {
		this.listaCliente = listaCliente;
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

	public int getIdhojaAlistamientoOrigen() {
		return idhojaAlistamientoOrigen;
	}

	public void setIdhojaAlistamientoOrigen(int idhojaAlistamientoOrigen) {
		this.idhojaAlistamientoOrigen = idhojaAlistamientoOrigen;
	}

	public int getIdhojaAlistamientoDestino() {
		return idhojaAlistamientoDestino;
	}

	public void setIdhojaAlistamientoDestino(int idhojaAlistamientoDestino) {
		this.idhojaAlistamientoDestino = idhojaAlistamientoDestino;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public List<OpeHojaAlistamiento> getListaHojaAlistamiento() {
		return listaHojaAlistamiento;
	}

	public void setListaHojaAlistamiento(List<OpeHojaAlistamiento> listaHojaAlistamiento) {
		this.listaHojaAlistamiento = listaHojaAlistamiento;
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


	public List<AdmDetalleCatalogo> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public List<OpeFrecuenciaVisita> getListaFrecuencia() {
		return listaFrecuencia;
	}

	public void setListaFrecuencia(List<OpeFrecuenciaVisita> listaFrecuencia) {
		this.listaFrecuencia = listaFrecuencia;
	}

	public List<OpeFrecuenciaVisita> getFrecuenciaSeleccionada() {
		return frecuenciaSeleccionada;
	}

	public void setFrecuenciaSeleccionada(List<OpeFrecuenciaVisita> frecuenciaSeleccionada) {
		this.frecuenciaSeleccionada = frecuenciaSeleccionada;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public OpeFrecuenciaVisita getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(OpeFrecuenciaVisita frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}

	public String getHoraHasta() {
		return horaHasta;
	}

	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}

	public List<OpeFrecuenciaVisita> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<OpeFrecuenciaVisita> frecuencias) {
		this.frecuencias = frecuencias;
	}
	
	

}

package com.scl.operacion.controlador;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.context.FacesContext;


import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;

import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.administracion.servicio.ServicioUsuarioCiudad;
import com.scl.operacion.modelo.OpeCircuito;
import com.scl.operacion.modelo.OpeHojaAlistamiento;

import com.scl.operacion.servicio.ServicioCircuito;
import com.scl.operacion.servicio.ServicioHojaAlistamiento;


@ManagedBean
@ViewScoped
public class CircuitoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Object> listaHojaNA;
	private List<Object[]>listaHojaSeleccionadaNA;
	private List<OpeHojaAlistamiento>hojaFiltradaNA;
	

	//private List<OpeHojaAlistamiento> listaHojaSA;
	private List<Object[]> listaHojaSeleccionadaSA;
	private List<OpeHojaAlistamiento>hojaFiltradaSA;


	private List<Object>listaHojaSA;


	
	private List<OpeCircuito> listaCircuito;
	private OpeCircuito circuitoSeleccionado;
	
	private List<AdmDetalleCatalogo> listaCiudad;
	
	
	
	private String dia;
	private int idCiudad;
	
	private int idCircuito;
	
	
	@EJB
	private ServicioUsuarioCiudad servicioUsuarioCiudad;
	@EJB
	private ServicioCircuito servicioCircuito;
	@EJB
	private ServicioHojaAlistamiento serviciohojaAlistamiento;
	@EJB
	private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;

	@PostConstruct
	public void init() {
		cancelar();
		consultaListaCiudad();
		//idCiudad = listaCiudad.get(0).getIdCiudadDc().getIdDetalleCatalogo();
		
			
	}
	
	public void cancelar() {
		
		setIdCiudad(-1);
		setDia(null); 
		setIdCircuito(-1);
			
			
			

		}
	
	public void consultaListaNA() {
		
		
		System.out.println("nooo"+idCiudad+dia);
			setListaHojaNA(serviciohojaAlistamiento.buscaHojasNA(idCiudad, dia));
			
			
			
		}
		
		
			
	public void consultaListas() {
		consultaListaSA();
		consultaListaNA();
		
	}
		


	public void consultaListaSA() {
		System.out.println("siiii"+idCiudad+idCircuito+dia);
	
		setListaHojaSA(serviciohojaAlistamiento.buscaHojasSA(this.idCiudad, idCircuito, dia));
		
			
			
		}
	
	

	public void consultaListaCircuito() {
		
		setListaCircuito(new ArrayList<>());
		setListaCircuito(servicioCircuito.buscaCircuitoDiaCiudad(idCiudad, dia));
		//consultaListaNA();
		//listaHojaSA =null;
	}


public void consultaListaCiudad() {
	AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		setListaCiudad(new ArrayList<>());
		setListaCiudad(servicioDetalleCatalogo.ciudadesAsignadas(usuario));
	}


public void eliminarAsignacion() {
	for(Object[]  item : listaHojaSeleccionadaSA){	
			
		serviciohojaAlistamiento.updateElimina(item[0].toString());	
	}
	consultaListaNA();
	consultaListaSA();	
	
	
}


public void asignaraCircuito() {
	
	for(Object[] item : listaHojaSeleccionadaNA){
		
		serviciohojaAlistamiento.updateAsigna(idCircuito,  item[0].toString());	
	
	}
	
	
	consultaListaNA();
	consultaListaSA();	
}






public String getDia() {
	return dia;
}

public void setDia(String dia) {
	this.dia = dia;
}

public int getIdCiudad() {
	return idCiudad;
}

public void setIdCiudad(int idCiudad) {
	this.idCiudad = idCiudad;
}



public List<Object> getListaHojaNA() {
	return listaHojaNA;
}

public void setListaHojaNA(List<Object> listaHojaNA) {
	this.listaHojaNA = listaHojaNA;
}

public List<Object> getListaHojaSA() {
	return listaHojaSA;
}

public void setListaHojaSA(List<Object> listaHojaSA) {
	this.listaHojaSA = listaHojaSA;
}





public List<Object[]> getListaHojaSeleccionadaNA() {
	return listaHojaSeleccionadaNA;
}

public void setListaHojaSeleccionadaNA(List<Object[]> listaHojaSeleccionadaNA) {
	this.listaHojaSeleccionadaNA = listaHojaSeleccionadaNA;
}


public List<Object[]> getListaHojaSeleccionadaSA() {
	return listaHojaSeleccionadaSA;
}

public void setListaHojaSeleccionadaSA(List<Object[]> listaHojaSeleccionadaSA) {
	this.listaHojaSeleccionadaSA = listaHojaSeleccionadaSA;
}

public List<OpeHojaAlistamiento> getHojaFiltradaSA() {
	return hojaFiltradaSA;
}

public void setHojaFiltradaSA(List<OpeHojaAlistamiento> hojaFiltradaSA) {
	this.hojaFiltradaSA = hojaFiltradaSA;
}

public List<OpeHojaAlistamiento> getHojaFiltradaNA() {
	return hojaFiltradaNA;
}

public void setHojaFiltradaNA(List<OpeHojaAlistamiento> hojaFiltradaNA) {
	this.hojaFiltradaNA = hojaFiltradaNA;
}



public List<AdmDetalleCatalogo> getListaCiudad() {
	return listaCiudad;
}

public void setListaCiudad(List<AdmDetalleCatalogo> listaCiudad) {
	this.listaCiudad = listaCiudad;
}

public List<OpeCircuito> getListaCircuito() {
	return listaCircuito;
}

public void setListaCircuito(List<OpeCircuito> listaCircuito) {
	this.listaCircuito = listaCircuito;
}

public OpeCircuito getCircuitoSeleccionado() {
	return circuitoSeleccionado;
}

public void setCircuitoSeleccionado(OpeCircuito circuitoSeleccionado) {
	this.circuitoSeleccionado = circuitoSeleccionado;
}

public int getIdCircuito() {
	return idCircuito;
}

public void setIdCircuito(int idCircuito) {
	this.idCircuito = idCircuito;
}

	
	
	
	

}

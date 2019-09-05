package com.bivi.controladores;
 
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
import org.primefaces.event.RowEditEvent;

import com.bivi.modelos.*;

import com.bivi.servicios.*;






@ManagedBean
@ViewScoped
public class BitacoraBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FisDetalleBitacora detalleBitacora;
    private List<FisDetalleBitacora> listaDetalleBitacora;
    



	@EJB
	private ServicioFisDetalleBitacora servicioDetalleBit ; 

	
	
	

	@PostConstruct
	public void init() {
		cancelar();
		
		consultaDetalleBitacora();
		
		
		
	}

	
	
	
	public void cancelar() {
		
		
		
		


	}
	

	
	
	public void consultaDetalleBitacora() {	
		
	
		listaDetalleBitacora = new ArrayList<>();
		listaDetalleBitacora = servicioDetalleBit.buscaBitacora();
		
		
	
	}




	public FisDetalleBitacora getDetalleBitacora() {
		return detalleBitacora;
	}




	public void setDetalleBitacora(FisDetalleBitacora detalleBitacora) {
		this.detalleBitacora = detalleBitacora;
	}




	public List<FisDetalleBitacora> getListaDetalleBitacora() {
		return listaDetalleBitacora;
	}




	public void setListaDetalleBitacora(List<FisDetalleBitacora> listaDetalleBitacora) {
		this.listaDetalleBitacora = listaDetalleBitacora;
	}
	
	
	
	
	
	
	
	

	



	
	
	

}

package com.scl.operacion.controlador;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.ServicioMotivoNoGestion;


@ManagedBean
@ViewScoped
public class MotivoNoGestionBean implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 private OpeMotivoNoGestion motivoNoGestion;
	 private OpeMotivoNoGestion motivoNoGestionSeleccionado;
	 private List<OpeMotivoNoGestion> listaMotivoNoGestion;
	 private List<OpeMotivoNoGestion> motivoNoGestionFiltrado;
	 private ExcelOptions excelOpt;
	 private PDFOptions pdfOpt;
	 private boolean bandera;
	 private Date date;
	 
	 @EJB
	    private ServicioMotivoNoGestion servicioMotivoNoGestion;
	 
	 @PostConstruct
	    public void init() {
		 cancelar();
		 consultaListaMotivoNoGestion();
		 date = new Date();
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
	 
	 
	//metodo que instancia nuevos objetos de una clase
     
	    public void cancelar() {
	    	motivoNoGestion = new OpeMotivoNoGestion();
	    	bandera = false;
	    }
	
	//metodo que guarda un motivo de no gestion nuevo
	     
	    public void guardar() {

	        try {
	        	motivoNoGestion.setIdMotivo(servicioMotivoNoGestion.getPK());
	        	motivoNoGestion.setFechaCreacion(date);
	       
	        	servicioMotivoNoGestion.create(motivoNoGestion);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "Aviso", "Se ha guardado con exito "));
	            consultaListaMotivoNoGestion();
	            cancelar();
	        } catch (Exception e) {

	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));

	        }

	    }
	    
	  //metodo para eliminar un motivo de no gestion
	     
	    public void eliminar() {
	    	if(motivoNoGestionSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {	
				motivoNoGestion = motivoNoGestionSeleccionado;
			
				servicioMotivoNoGestion.update(motivoNoGestion);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
			consultaListaMotivoNoGestion();
			cancelar();		
			}

	    }
	    
	    public void actualizar() {
			
	    	servicioMotivoNoGestion.update(motivoNoGestion);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Actualizado Correctamente "));
			cancelar();
			
		}
	    
	    public void modificar(){
			if(motivoNoGestionSeleccionado == null){
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
						
			}else {
				bandera =	true;
				motivoNoGestion = motivoNoGestionSeleccionado;
			
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosMotivoNoGestion').show();");
			}
		}
	    
	    
	    public void nuevo(){
	    	bandera =	false;
	    	motivoNoGestion = new OpeMotivoNoGestion();
			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosMotivoNoGestion').show();");
			
		}
		
		public static void resetarFormulario() {
	        RequestContext.getCurrentInstance().reset("frmCrear");
		}
	    
	    
	    
	  //metodo que llena una lista con los registros provenientes de la base de datos
	     
	    public void consultaListaMotivoNoGestion() {

	    	listaMotivoNoGestion = new ArrayList<>(); // Creo una lista para mostrar todo los datos en el datatable
	    	listaMotivoNoGestion = servicioMotivoNoGestion.findAll();

	        
	    }
	    
	  //metodo que verifica si la accion es de guardar un nuevo motivo o actualizar uno ya existente
	    public void persistir() {

	        if (bandera == true) {

	            actualizar();

	        } else {
	            guardar();

	        }
	    }
	    
	    
	    
	    
	    
	    public OpeMotivoNoGestion getMotivoNoGestion() {
			return motivoNoGestion;
		}

		public void setMotivoNoGestion(OpeMotivoNoGestion motivoNoGestion) {
			this.motivoNoGestion = motivoNoGestion;
		}

		public OpeMotivoNoGestion getMotivoNoGestionSeleccionado() {
			return motivoNoGestionSeleccionado;
		}

		public void setMotivoNoGestionSeleccionado(OpeMotivoNoGestion motivoNoGestionSeleccionado) {
			this.motivoNoGestionSeleccionado = motivoNoGestionSeleccionado;
		}

		public List<OpeMotivoNoGestion> getListaMotivoNoGestion() {
			return listaMotivoNoGestion;
		}

		public void setListaMotivoNoGestion(List<OpeMotivoNoGestion> listaMotivoNoGestion) {
			this.listaMotivoNoGestion = listaMotivoNoGestion;
		}

		public List<OpeMotivoNoGestion> getMotivoNoGestionFiltrado() {
			return motivoNoGestionFiltrado;
		}

		public void setMotivoNoGestionFiltrado(List<OpeMotivoNoGestion> motivoNoGestionFiltrado) {
			this.motivoNoGestionFiltrado = motivoNoGestionFiltrado;
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

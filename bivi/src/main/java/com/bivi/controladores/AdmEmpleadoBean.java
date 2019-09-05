package com.bivi.controladores;

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

import com.bivi.modelos.*;
import com.bivi.servicios.*;



@ManagedBean
@ViewScoped

public class AdmEmpleadoBean implements  Serializable {
	// creo clases y listas para obtener la informacion que necesito
	private static final long serialVersionUID = 1L;
	private AdmEmpleado empleado;
	private AdmEmpleado empleadoSeleccionado;
	public List<AdmEmpleado> listaEmpleado;
	private List<AdmEmpleado> empleadoFiltrado;
	private ExcelOptions excelOpt;
	private PDFOptions pdfOpt;
	private boolean bandera;
	private Date date;

	// establesco conección a los servicios por medio de los ejb
	@EJB
	private ServicioAdmEmpleado servicioempleado;

	
	// metodo que inicia el proceso
	@PostConstruct
	public void init() {
		cancelar();
		consultaListaEmpleado();
		setDate(new Date());
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
    	empleado = new AdmEmpleado();
    	bandera = false;
    }
    
    //metodo que llena una lista con los registros provenientes de la base de datos
    
    public void consultaListaEmpleado() {

    	listaEmpleado = new ArrayList<>(); // Creo una lista para mostrar todo los datos en el datatable
    	listaEmpleado = servicioempleado.findAll();
    }
    
    //metodo que guarda un empleado nuevo
    public void guardar() {
    	
    	try{
    		empleado.setIdEmpleado(servicioempleado.getPK());
    		
    		servicioempleado.create(empleado);
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso", "Se ha guardado con exito "));
    		consultaListaEmpleado();
    		cancelar();
    	} catch(Exception e) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));
    	}
    }
    
  	//metodo para eliminar empleado
    public void eliminar() {
    	if(empleadoSeleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {	
			servicioempleado.delete(empleadoSeleccionado);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
		consultaListaEmpleado();
		cancelar();		
		}

    }
    
    public void actualizar() {
		
    	servicioempleado.update(empleado);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Actualizado Correctamente "));
		cancelar();
		
	}
    
    @SuppressWarnings("deprecation")
	public void modificar(){
		if(empleadoSeleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
			bandera =	true;
			empleado = empleadoSeleccionado;
		
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosEmpleado').show();");
		}
	}
    
    @SuppressWarnings("deprecation")
	public void nuevo(){
    	bandera =	false;
    	empleado = new AdmEmpleado();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosEmpleado').show();");
		
	}
    
    @SuppressWarnings("deprecation")
	public static void resetarFormulario() {
        RequestContext.getCurrentInstance().reset("frmCrear");
	}
    
    //metodo que verifica si la accion es de guardar un nuevo motivo o actualizar uno ya existente
    public void persistir() {

        if (bandera == true) {

            actualizar();

        } else {
            guardar();

        }
    }
	
	
    
    public AdmEmpleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(AdmEmpleado empleado) {
		this.empleado = empleado;
	}

	public AdmEmpleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(AdmEmpleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}

	public List<AdmEmpleado> getListaEmpleado() {
		return listaEmpleado;
	}

	public void setListaEmpleado(List<AdmEmpleado> listaEmpleado) {
		this.listaEmpleado = listaEmpleado;
	}

	public List<AdmEmpleado> getEmpleadoFiltrado() {
		return empleadoFiltrado;
	}

	public void setEmpleadoFiltrado(List<AdmEmpleado> empleadoFiltrado) {
		this.empleadoFiltrado = empleadoFiltrado;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

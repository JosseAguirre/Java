package com.scl.operacion.controlador;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

import org.primefaces.context.RequestContext;

import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.administracion.servicio.ServicioAdmDetalleCatalogo;
import com.scl.operacion.modelo.*;
import com.scl.operacion.servicio.ServicioReporte;
import com.scl.operacion.servicio.*;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


@ManagedBean
@ViewScoped
public class ReportesBean implements  Serializable {
	
	 private static final long serialVersionUID = 1L;
	 
	 	private OpeTransaccion transaccion; 
	 	private List<Object> listaTransaccionOD;
	 	private List<AdmDetalleCatalogo> listaCiudad;
	 	
	 	private int idCiudad;
	 	
	 	
	 	private boolean vertblRptTransaccionOD;
	
	    

	    
	    @Resource(lookup = "java:jboss/postgresDS")
	    private DataSource ds;
	    @EJB
	    private ServicioReporte servicioReporte;
	    @EJB
		private ServicioAdmDetalleCatalogo servicioDetalleCatalogo;
	   
	     
	    @PostConstruct
	    public void init() {
	        cancelar();
	        
	        consultaListaCiudad();
	        
	      
	       

	       
	    }
	    
	    public void consultaListaCiudad() {
			AdmUsuario usuario = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("usuario"); // obtengo el usuario logueado
			setListaCiudad(new ArrayList<>());
			setListaCiudad(servicioDetalleCatalogo.ciudadesAsignadas(usuario));
		}
	    
	    public void consultarTransaccionOD(){
	    	vertblRptTransaccionOD = true;
	    	listaTransaccionOD = servicioReporte.buscaTransaccionOD(transaccion.getFechaOperacion());
	    	
	    	
	    }

	    
	    public void rptTransaccionOrigendestino() throws SQLException{
	    	Connection conn = ds.getConnection();
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fechaoperacion", transaccion.getFechaOperacion());
			
			try {
				
			File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/operaciones/transacciones.jasper"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conn);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition","attachment; filename=jsfReport.pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
			FacesContext.getCurrentInstance().responseComplete();
			
			
			
				
			} catch (Exception e) {
				System.out.println("Error al exportar el PDF: " + e);
			}
			
			
			
	    	
	    	
	    	
	    }
	    //metodo que instancia nuevos objetos de una clase
	     
	    public void cancelar() {
	    	
	    	transaccion = new OpeTransaccion(); 
	    	vertblRptTransaccionOD = false;
	        

	    }


		public OpeTransaccion getTransaccion() {
			return transaccion;
		}


		public void setTransaccion(OpeTransaccion transaccion) {
			this.transaccion = transaccion;
		}

		public List<Object> getListaTransaccionOD() {
			return listaTransaccionOD;
		}

		public void setListaTransaccionOD(List<Object> listaTransaccionOD) {
			this.listaTransaccionOD = listaTransaccionOD;
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

		public boolean isVertblRptTransaccionOD() {
			return vertblRptTransaccionOD;
		}

		public void setVertblRptTransaccionOD(boolean vertblRptTransaccionOD) {
			this.vertblRptTransaccionOD = vertblRptTransaccionOD;
		}


	    //metodo que guarda un usuario nuevo
	     
	  
	    


}






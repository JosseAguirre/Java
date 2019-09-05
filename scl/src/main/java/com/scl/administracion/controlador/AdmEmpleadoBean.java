package com.scl.administracion.controlador;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import com.scl.administracion.modelo.*;
import com.scl.administracion.servicio.*;


@ManagedBean
@ViewScoped

public class AdmEmpleadoBean implements  Serializable {
	// creo clases y listas para obtener la informacion que necesito
	private static final long serialVersionUID = 1L;
	private AdmEmpleado admempleado;
	private AdmEmpleado empleadoseleccionado;
	public static List<AdmEmpleado> listaempleado;
	private List<AdmEmpleado> empleadofiltrado;

	private List<AdmDetalleCatalogo> listaciudad;
	private List<AdmDetalleCatalogo> listasexo;
	private List<AdmDetalleCatalogo> listaestado;
	private List<AdmDetalleCatalogo> listacargo;
	private List<AdmDetalleCatalogo> listaclaseempleado;
	private List<AdmDetalleCatalogo> listadepartamento;
	private List<AdmDetalleCatalogo> listalineanegocio;
	private List<AdmDetalleCatalogo> listatipoempleado;
	private List<AdmDetalleCatalogo> listaEstadosCliente;

	// creo variables
	private boolean bandera ;
	private int iddetallecatalogo;
	private Integer idtipoempleado;
	private int idciudad;
	private int idsexo;
	private int idestado;
	private int idcargo;
	private int idlineanegocio;
	private int idclaseempleado;
	private int iddepartamento;
	private int idEstadoCliente;
	private Date date;

	// establesco conección a los servicios por medio de los ejb
	@EJB
	private ServicioAdmEmpleado servicioempleado;
	@EJB
	private ServicioAdmDetalleCatalogo serviciodetallecatalogo;

	// metodo que inicia el proceso

	@PostConstruct
	public void init() {
		cancelar();
		date = new Date();
		
		consultaListaEmpleados();
		
		
		

		/*
		 * String strDateFormat1 = "yyyy-mm-dd"; DateFormat dateFormat1 = new
		 * SimpleDateFormat(strDateFormat1); String formattedDate1=
		 * dateFormat1.format(date); System.out.
		 * println("Current time of the day using Date - 12 hour format: " +
		 * formattedDate1);
		 */
	}
	
	public void cancelar() {
		
		admempleado = new AdmEmpleado();	
		bandera = false;	
		idtipoempleado = -1;
		idciudad = -1;
		idsexo = -1;	
		idcargo = -1;
		idlineanegocio = -1;
		iddepartamento = -1;
		idEstadoCliente = -1;
	}
	
	
	public void consultaListasCombos(){
		listaciudad = new ArrayList<>();
		listasexo = new ArrayList<>();
		listacargo = new ArrayList<>();
		listadepartamento = new ArrayList<>();
		listalineanegocio = new ArrayList<>();
		listatipoempleado = new ArrayList<>();
		listaEstadosCliente = new ArrayList<>();
		
		listaciudad = serviciodetallecatalogo.ciudades();		
		listasexo = serviciodetallecatalogo.sexos();			
		listacargo = serviciodetallecatalogo.cargos();		
		listadepartamento = serviciodetallecatalogo.departamentos();
		listalineanegocio = serviciodetallecatalogo.lineasnegocio();	
		listatipoempleado = serviciodetallecatalogo.tiposempleado();
		listaEstadosCliente = serviciodetallecatalogo.estadosEmpleados();
		
		
	}
	
	
	

	// metdo para guardar el empleado

	public void guardar() {
		AdmDetalleCatalogo tipoEmpleado = new AdmDetalleCatalogo();
		tipoEmpleado.setIdDetalleCatalogo(idtipoempleado);
		admempleado.setIdTipoEmpleadoDc(tipoEmpleado);
		
		AdmDetalleCatalogo ciudad = new AdmDetalleCatalogo();
		ciudad.setIdDetalleCatalogo(idciudad);
		admempleado.setIdTipoEmpleadoDc(ciudad);
		
		AdmDetalleCatalogo sexo = new AdmDetalleCatalogo();
		sexo.setIdDetalleCatalogo(idsexo);
		admempleado.setIdSexoDc(sexo);
		
		AdmDetalleCatalogo estadoCliente = new AdmDetalleCatalogo();
		estadoCliente.setIdDetalleCatalogo(idEstadoCliente);
		admempleado.setIdEstadoDc(estadoCliente);
		
		AdmDetalleCatalogo cargo = new AdmDetalleCatalogo();
		cargo.setIdDetalleCatalogo(idcargo);
		admempleado.setIdCargoDc(cargo);
		
		AdmDetalleCatalogo dep = new AdmDetalleCatalogo();
		dep.setIdDetalleCatalogo(iddepartamento);
		admempleado.setIdDepartamentoDc(dep);
		
		AdmDetalleCatalogo linea = new AdmDetalleCatalogo();
		linea.setIdDetalleCatalogo(idlineanegocio);
		admempleado.setIdDepartamentoDc(linea);
				
		
		
	

		admempleado.setIdEmpleado(servicioempleado.getPK());
		//admempleado.setSysdelete(false);
		admempleado.setFechaRegistro(date);

		//buscaIdCombos();
		servicioempleado.create(admempleado);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Emppleado guardado correctamente "));
		consultaListaEmpleados();
		cancelar();
	}

	// metodo que busca la información necesaria para llenar el campo crear
	// empleado
	
	
	public void buscaIdCombos() {

		//detallecatalogo = serviciodetallecatalogo.findOne(idtipoempleado);
		//admempleado.setIdTipoEmpleadoDc(detallecatalogo);

		//detallecatalogo = serviciodetallecatalogo.findOne(idciudad);
		//admempleado.setIdCiudadDc(detallecatalogo);

		//detallecatalogo = serviciodetallecatalogo.findOne(idsexo);
		//admempleado.setIdSexoDc(detallecatalogo);
		
		//detallecatalogo = serviciodetallecatalogo.findOne(idEstadoCliente);
		//admempleado.setIdEstadoDc(detallecatalogo);

		

		//detallecatalogo = serviciodetallecatalogo.findOne(idcargo);
		//admempleado.setIdCargoDc(detallecatalogo);

		//detallecatalogo = serviciodetallecatalogo.findOne(idclaseempleado);
		//admempleado.setIdClaseEmpleadoDc(detallecatalogo);

		//detallecatalogo = serviciodetallecatalogo.findOne(iddepartamento);
		//admempleado.setIdDepartamentoDc(detallecatalogo);

		//detallecatalogo = serviciodetallecatalogo.findOne(idlineanegocio);
		//admempleado.setIdLineaNegocioDc(detallecatalogo);

	}

	// metodo para eliminar al empleado

	public void eliminar() {
		if(empleadoseleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {	
			admempleado = empleadoseleccionado;
		//responsable.setSysdelete(true);
		servicioempleado.update(admempleado);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
		consultaListaEmpleados();
		cancelar();		
		}
	}

	// metodo para actualizar el empleado

	public void actualizar() {
		buscaIdCombos();

		servicioempleado.update(admempleado);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Empleado Actualizado Correctamente "));
		cancelar();
		
	}
	
	
	public void modificar(){
		if(empleadoseleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
		bandera =	true;
		admempleado = empleadoseleccionado;
		
		idciudad = admempleado.getIdCiudadDc().getIdDetalleCatalogo();
		 idsexo = admempleado.getIdSexoDc().getIdDetalleCatalogo();
		 idEstadoCliente = admempleado.getIdEstadoDc().getIdDetalleCatalogo();
		 idcargo = admempleado.getIdCargoDc().getIdDetalleCatalogo();
	     idlineanegocio = admempleado.getIdLineaNegocioDc().getIdDetalleCatalogo();
		 //idclaseempleado = admempleado.getIdClaseEmpleadoDc().getIdDetalleCatalogo();
		iddepartamento = admempleado.getIdDepartamentoDc().getIdDetalleCatalogo();
		idtipoempleado = admempleado.getIdTipoEmpleadoDc().getIdDetalleCatalogo();
		resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosEmpleado').show();");
		}
	}
	
	public void nuevo(){	
		bandera =	false;
		admempleado = new AdmEmpleado ();
		resetarFormulario();
		consultaListasCombos();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosEmpleado').show();");
		
	}
	
	public static void resetarFormulario() {
        RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	public void consultaListaEmpleados() {
		listaempleado = new ArrayList<>();
		listaempleado = servicioempleado.findAll();
		
	}
	
	public void onRowSelect(SelectEvent event) {
		admempleado =(AdmEmpleado) event.getObject();
		
		
		
		
    }


	
	

	public void empleadoSeleccionado() {

	}

	// metodo para diferenciar entre guardar y actualizar
	public void persistir() {
		if (bandera == true) {

			actualizar();
			

		} else {
			guardar();
			
		}
	}

	public AdmEmpleado getAdmEmpleado() {
		return admempleado;
	}

	public void setAdmEmpleado(AdmEmpleado admempleado) {
		this.admempleado = admempleado;
	}

	public List<AdmEmpleado> getListaempleado() {
		return listaempleado;
	}

	public void setListaempleado(List<AdmEmpleado> listaempleado) {
		this.listaempleado = listaempleado;
	}

	
	
	public List<AdmDetalleCatalogo> getListaEstadosCliente() {
		return listaEstadosCliente;
	}




	public void setListaEstadosCliente(List<AdmDetalleCatalogo> listaEstadosCliente) {
		this.listaEstadosCliente = listaEstadosCliente;
	}




	public List<AdmDetalleCatalogo> getListaciudad() {
		return listaciudad;
	}

	public void setListaciudad(List<AdmDetalleCatalogo> listaciudad) {
		this.listaciudad = listaciudad;
	}

	public List<AdmDetalleCatalogo> getListasexo() {
		return listasexo;
	}

	public void setListasexo(List<AdmDetalleCatalogo> listasexo) {
		this.listasexo = listasexo;
	}

	public List<AdmDetalleCatalogo> getListaestado() {
		return listaestado;
	}

	public void setListaestado(List<AdmDetalleCatalogo> listaestado) {
		this.listaestado = listaestado;
	}

	public List<AdmDetalleCatalogo> getListacargo() {
		return listacargo;
	}

	public void setListacargo(List<AdmDetalleCatalogo> listacargo) {
		this.listacargo = listacargo;
	}

	public List<AdmDetalleCatalogo> getListaclaseempleado() {
		return listaclaseempleado;
	}

	public void setListaclaseempleado(List<AdmDetalleCatalogo> listaclaseempleado) {
		this.listaclaseempleado = listaclaseempleado;
	}

	public List<AdmDetalleCatalogo> getListadepartamento() {
		return listadepartamento;
	}

	public void setListadepartamento(List<AdmDetalleCatalogo> listadepartamento) {
		this.listadepartamento = listadepartamento;
	}

	public List<AdmDetalleCatalogo> getListalineanegocio() {
		return listalineanegocio;
	}

	public void setListalineanegocio(List<AdmDetalleCatalogo> listalineanegocio) {
		this.listalineanegocio = listalineanegocio;
	}

	public List<AdmDetalleCatalogo> getListatipoempleado() {
		return listatipoempleado;
	}

	public void setListatipoempleado(List<AdmDetalleCatalogo> listatipoempleado) {
		this.listatipoempleado = listatipoempleado;
	}



	public int getIddetallecatalogo() {
		return iddetallecatalogo;
	}

	public void setIddetallecatalogo(int iddetallecatalogo) {
		this.iddetallecatalogo = iddetallecatalogo;
	}

	public int getIdtipoempleado() {
		return idtipoempleado;
	}

	public void setIdtipoempleado(int idtipoempleado) {
		this.idtipoempleado = idtipoempleado;
	}

	public int getIdciudad() {
		return idciudad;
	}

	
	public int getIdEstadoCliente() {
		return idEstadoCliente;
	}




	public void setIdEstadoCliente(int idEstadoCliente) {
		this.idEstadoCliente = idEstadoCliente;
	}




	public void setIdciudad(int idciudad) {
		this.idciudad = idciudad;
	}

	public int getIdestado() {
		return idestado;
	}

	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}

	public int getIdcargo() {
		return idcargo;
	}

	public void setIdcargo(int idcargo) {
		this.idcargo = idcargo;
	}

	public int getIdlineanegocio() {
		return idlineanegocio;
	}

	public void setIdlineanegocio(int idlineanegocio) {
		this.idlineanegocio = idlineanegocio;
	}

	public int getIdclaseempleado() {
		return idclaseempleado;
	}

	public void setIdclaseempleado(int idclaseempleado) {
		this.idclaseempleado = idclaseempleado;
	}

	public int getIddepartamento() {
		return iddepartamento;
	}

	public void setIddepartamento(int iddepartamento) {
		this.iddepartamento = iddepartamento;
	}

	public int getIdsexo() {
		return idsexo;
	}

	public void setIdsexo(int idsexo) {
		this.idsexo = idsexo;
	}

	public List<AdmEmpleado> getEmpleadofiltrado() {
		return empleadofiltrado;
	}

	public void setEmpleadofiltrado(List<AdmEmpleado> empleadofiltrado) {
		this.empleadofiltrado = empleadofiltrado;
	}

	public AdmEmpleado getEmpleadoseleccionado() {
		return empleadoseleccionado;
	}

	public void setEmpleadoseleccionado(AdmEmpleado empleadoseleccionado) {
		this.empleadoseleccionado = empleadoseleccionado;
	}

}

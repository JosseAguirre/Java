package com.bivi.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.bivi.modelos.*;
import com.bivi.servicios.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class TurnoBean implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	private FisTurno turno;
	private FisTurno turnoSeleccionado;
	private List<FisTurno> listaTurno;
	private List<FisTurno> turnoFiltrado;
	
	private List<AdmPuesto> listaPuesto;
    private AdmPuesto puesto;
    private List<AdmPuesto> puestoSeleccionados;
    
    private List<AdmDetalleCatalogo> listaDias;
    private AdmDetalleCatalogo dia;
    private List<AdmDetalleCatalogo> diasSeleccionados;
    
    private FisPuestoTurno puestoTurno;
    private List<FisPuestoTurno> puestoTurnoSeleccionados;
    private List<FisPuestoTurno> puestoTurnoAsignados;
    
    private FisTurnoDia turnoDia;
    private List<FisTurnoDia> turnoDiaSeleccionados;
    private List<FisTurnoDia> turnoDiaAsignados;
    
    
    private int idTurno;
    private int idPuesto;
    private int idPuestoTurno;
    private int idDia;
    private boolean bandera;
    
    
    @EJB
    private ServicioFisTurno servicioFisTurno;
    @EJB
    private ServicioAdmPuestos servicioAdmPuestos;
    @EJB
    private ServicioFisPuestoTurno servicioFisPuestoTurno;
    @EJB
    private ServicioFisTurnoDia servicioFisTurnoDia;
    @EJB
    private ServicioAdmDetalleCatalogo servicioAdmDetalleCatalogo;
    
    
    @PostConstruct
	public void init() {
		cancelar();
		consultaListaTurnos();
		consultaListaPuestos();
		consultaListaDias();
		turnoSeleccionado = new FisTurno();
	}

	// metodo que instancia nuevos objetos de una clase
	public void cancelar() {
		turno = new FisTurno();
		bandera = false;
		puestoSeleccionados = new ArrayList<>();
		diasSeleccionados = new ArrayList<>();
	}
    
    // metodo que llena una lista con los registros provenientes d ela base de datos
 	public void consultaListaTurnos() {
 		listaTurno = new ArrayList<>();
 		listaTurno = servicioFisTurno.findAll();
 	}
 	
 	public void consultaListaPuestos() {
		if (turnoSeleccionado != null) {
			listaPuesto = new ArrayList<>();
			listaPuesto = servicioAdmPuestos.findAll();
		} else {
			listaPuesto = new ArrayList<>();
		}
	}
 	
 	public void consultaListaDias() {
		if (turnoSeleccionado != null) {
			listaDias = new ArrayList<>();
			listaDias = servicioAdmDetalleCatalogo.dias();
		} else {
			listaDias = new ArrayList<>();
		}
	}
    
    
 	// metodo que guarda un usuario turno
  	public void guardar() {
  		try {
  			turno.setIdTurno((int)servicioFisTurno.getPK());		

  			servicioFisTurno.create(turno);
  			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso","Se ha realizado el registro: "));
  			consultaListaTurnos();
  			cancelar();
  		} catch (Exception e) {
  			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Se ha producido un error al guardar"));
  		}
  	}
  	
  	// metodo para eliminar un usuario
  	public void eliminar() {
  		if (turnoSeleccionado == null) {
  			FacesContext.getCurrentInstance().addMessage(null,
  					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
  		} else {
  			turno = turnoSeleccionado;

  			servicioFisTurno.update(turno);
  			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));
  			consultaListaTurnos();
  			cancelar();
  		}
  	}
  	
	public void actualizar() {
		try {
			servicioFisTurno.update(turno);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Regitro actualizado con exito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Se produjo un error al actualizar :" + e));
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public void modificar() {
		if (turnoSeleccionado == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
		} else {
			bandera = true;
			turno = turnoSeleccionado;
			consultaListaPuestos();
			consultaListaDias();
			consultaPuestosAsignados();
			consultaDiasAsignados();

			resetarFormulario();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosTurno').show();");
		}
	}

	public void cargarDatosUsuario() {
		cancelar();
		consultaPuestosAsignados();
		consultaDiasAsignados();
		consultaListaPuestos();
		consultaListaDias();
	}

	@SuppressWarnings("deprecation")
	public void nuevo() {
		bandera = false;
		// admusuario = new AdmUsuario ();
		cancelar();
		consultaPuestosAsignados();
		consultaListaPuestos();
		consultaDiasAsignados();
		consultaListaDias();
		// resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosTurno').show();");
	}

	@SuppressWarnings("deprecation")
	public static void resetarFormulario() {
		RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	
	public void persistir() {
		System.out.println("bandera esss :" + bandera);
		if (bandera == true) {
			actualizar();
		} else {
			guardar();
		}
	}
	
	//------------------------------------------------------------------------------------------------
	// Aqui ya es Turno - dia
	
	public void consultaDiasAsignados() {
		if (turnoSeleccionado != null) {
			turnoDiaAsignados = new ArrayList<>();
			turnoDiaAsignados = servicioFisTurnoDia.buscaTurnoDia(turnoSeleccionado);
		} else {
			turnoDiaAsignados = new ArrayList<>();
		}
	}
	
	public void asignarDia() {
		AdmDetalleCatalogo d = new AdmDetalleCatalogo();
		d.setIdDetalleCatalogo(idDia);

		FisTurno us = new FisTurno();
		us.setIdTurno(turnoSeleccionado.getIdTurno());

		turnoDia = new FisTurnoDia();
		turnoDia.setIdTurnoDia(servicioFisTurnoDia.getPK());
		turnoDia.setFisTurno(us);
		turnoDia.setIdDia(d);
		servicioFisTurnoDia.create(turnoDia);

		consultaDiasAsignados();
		cancelar();
	}
	
	public void eliminarDia() {
		for (FisTurnoDia item : turnoDiaSeleccionados) {
			servicioFisTurnoDia.eliminarTurnoDia(item.getIdTurnoDia());
		}
		consultaDiasAsignados();
	}
	
	//-------------------------------------------------------------------------------------------------
	// Aqui ya es Puesto - Turno

	public void consultaPuestosAsignados() {
		if (turnoSeleccionado != null) {
			puestoTurnoAsignados = new ArrayList<>();
			puestoTurnoAsignados = servicioFisPuestoTurno.buscaPuestoTurno(turnoSeleccionado);
		} else {
			puestoTurnoAsignados = new ArrayList<>();
		}
	}
	
	public void asignarPuesto() {
		AdmPuesto d = new AdmPuesto();
		d.setIdPuesto(idPuesto);

		FisTurno us = new FisTurno();
		us.setIdTurno(turnoSeleccionado.getIdTurno());

		puestoTurno = new FisPuestoTurno();
		puestoTurno.setIdPuestoTurno(servicioFisPuestoTurno.getPK());
		puestoTurno.setFisTurno(us);
		puestoTurno.setIdPuesto(d);
		servicioFisPuestoTurno.create(puestoTurno);

		consultaPuestosAsignados();
		cancelar();
	}
	
	public void eliminarPuesto() {
		for (FisPuestoTurno item : puestoTurnoSeleccionados) {
			servicioFisPuestoTurno.eliminarPuestoTurno(item.getIdPuestoTurno());
		}
		consultaPuestosAsignados();
	}

    
    
    
    
    
	public FisTurno getTurno() {
		return turno;
	}
	public void setTurno(FisTurno turno) {
		this.turno = turno;
	}
	public FisTurno getTurnoSeleccionado() {
		return turnoSeleccionado;
	}
	public void setTurnoSeleccionado(FisTurno turnoSeleccionado) {
		this.turnoSeleccionado = turnoSeleccionado;
	}
	public List<FisTurno> getListaTurno() {
		return listaTurno;
	}
	public void setListaTurno(List<FisTurno> listaTurno) {
		this.listaTurno = listaTurno;
	}
	public List<FisTurno> getTurnoFiltrado() {
		return turnoFiltrado;
	}
	public void setTurnoFiltrado(List<FisTurno> turnoFiltrado) {
		this.turnoFiltrado = turnoFiltrado;
	}
	public List<AdmPuesto> getListaPuesto() {
		return listaPuesto;
	}
	public void setListaPuesto(List<AdmPuesto> listaPuesto) {
		this.listaPuesto = listaPuesto;
	}
	public AdmPuesto getPuesto() {
		return puesto;
	}
	public void setPuesto(AdmPuesto puesto) {
		this.puesto = puesto;
	}
	public List<AdmPuesto> getPuestoSeleccionados() {
		return puestoSeleccionados;
	}

	public void setPuestoSeleccionados(List<AdmPuesto> puestoSeleccionados) {
		this.puestoSeleccionados = puestoSeleccionados;
	}

	public FisPuestoTurno getPuestoTurno() {
		return puestoTurno;
	}
	public void setPuestoTurno(FisPuestoTurno puestoTurno) {
		this.puestoTurno = puestoTurno;
	}
	public List<FisPuestoTurno> getPuestoTurnoSeleccionados() {
		return puestoTurnoSeleccionados;
	}
	public void setPuestoTurnoSeleccionados(List<FisPuestoTurno> puestoTurnoSeleccionados) {
		this.puestoTurnoSeleccionados = puestoTurnoSeleccionados;
	}
	public List<FisPuestoTurno> getPuestoTurnoAsignados() {
		return puestoTurnoAsignados;
	}
	public void setPuestoTurnoAsignados(List<FisPuestoTurno> puestoTurnoAsignados) {
		this.puestoTurnoAsignados = puestoTurnoAsignados;
	}
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public int getIdPuestoTurno() {
		return idPuestoTurno;
	}
	public void setIdPuestoTurno(int idPuestoTurno) {
		this.idPuestoTurno = idPuestoTurno;
	}
	public boolean isBandera() {
		return bandera;
	}
	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	public FisTurnoDia getTurnoDia() {
		return turnoDia;
	}

	public void setTurnoDia(FisTurnoDia turnoDia) {
		this.turnoDia = turnoDia;
	}

	public List<FisTurnoDia> getTurnoDiaSeleccionados() {
		return turnoDiaSeleccionados;
	}

	public void setTurnoDiaSeleccionados(List<FisTurnoDia> turnoDiaSeleccionados) {
		this.turnoDiaSeleccionados = turnoDiaSeleccionados;
	}

	public List<FisTurnoDia> getTurnoDiaAsignados() {
		return turnoDiaAsignados;
	}

	public void setTurnoDiaAsignados(List<FisTurnoDia> turnoDiaAsignados) {
		this.turnoDiaAsignados = turnoDiaAsignados;
	}

	public int getIdDia() {
		return idDia;
	}

	public void setIdDia(int idDia) {
		this.idDia = idDia;
	}

	public List<AdmDetalleCatalogo> getListaDias() {
		return listaDias;
	}

	public void setListaDias(List<AdmDetalleCatalogo> listaDias) {
		this.listaDias = listaDias;
	}

	public AdmDetalleCatalogo getDia() {
		return dia;
	}

	public void setDia(AdmDetalleCatalogo dia) {
		this.dia = dia;
	}

	public List<AdmDetalleCatalogo> getDiasSeleccionados() {
		return diasSeleccionados;
	}

	public void setDiasSeleccionados(List<AdmDetalleCatalogo> diasSeleccionados) {
		this.diasSeleccionados = diasSeleccionados;
	}

}

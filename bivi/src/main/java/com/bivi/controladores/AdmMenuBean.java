package com.bivi.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.bivi.modelos.*;
import com.bivi.servicios.*;


@ManagedBean
@ViewScoped
public class AdmMenuBean implements  Serializable {
	private static final long serialVersionUID = 1L;
	private AdmMenu admmenu;
	private List<AdmMenu>listamenu;
	private List<AdmMenu>menufiltrado;
	private int bandera ;
	private AdmMenu menuSeleccionado;

	@EJB
	private ServicioAdmMenu serviciomenu;


	@PostConstruct
	public void init() {
		cancelar();

	}
	
	@SuppressWarnings("deprecation")
	public void nuevo(){
		//bandera =	false;
		//admusuario = new AdmUsuario ();
		cancelar();
		
		//resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgDatosMenu').show();");
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void modificar(){
		if(menuSeleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
		
			admmenu = menuSeleccionado;
			//idestado = admusuario.getIdEstadoDc().getIdDetalleCatalogo();
			//idsexo = admempleado.getIdSexoDc().getIdDetalleCatalogo();
			RequestContext.getCurrentInstance().execute("PF('dlgDatosMenu').show();");
		}
	}
	

	
	public void guardar() {
		admmenu.setIdMenu(serviciomenu.getPK());
		serviciomenu.create(admmenu);
		cancelar();
	}

	
	public void eliminar() {
		admmenu = menuSeleccionado;
		serviciomenu.delete(admmenu);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha eliminado el registro"));
		cancelar();
	}


	
	
	//metodo que llena una lista con los menues y submenues provenientes d ela base de datos
	
	public void consultar() {
		setListamenu(new ArrayList<>());
		setListamenu(serviciomenu.findAll());
	}

	
	public void cancelar() {
		admmenu = new AdmMenu();
		consultar();
		bandera = 0;
	}
	
	
	public void onRowSelect(SelectEvent event) {
		admmenu =(AdmMenu) event.getObject();
		
		bandera = 1;	
		
		System.out.println("en rowselect  bandera vale : "+bandera);	
    }

	

	public AdmMenu getAdmMenu() {
		return admmenu;
	}

	public void setAdmMenu(AdmMenu admmenu) {
		this.admmenu = admmenu;
	}

	public List<AdmMenu> getMenufiltrado() {
		return menufiltrado;
	}

	public void setMenufiltrado(List<AdmMenu> menufiltrado) {
		this.menufiltrado = menufiltrado;
	}

	public List<AdmMenu> getListamenu() {
		return listamenu;
	}

	public void setListamenu(List<AdmMenu> listamenu) {
		this.listamenu = listamenu;
	}

	public AdmMenu getMenuSeleccionado() {
		return menuSeleccionado;
	}

	public void setMenuSeleccionado(AdmMenu menuSeleccionado) {
		this.menuSeleccionado = menuSeleccionado;
	}
		
}

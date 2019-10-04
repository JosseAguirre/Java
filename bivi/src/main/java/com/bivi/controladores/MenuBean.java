package com.bivi.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.bivi.modelos.*;
import com.bivi.servicios.*;



@SuppressWarnings("unused")
@ManagedBean
@SessionScoped
public class MenuBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	private List<AdmRol>listarol;
	private List<AdmRolMenu>listarolmenu;
   
	
	private List<AdmRolUsuario>listarolusuario;
	
	
	public static List<AdmMenu>listamenuusuario;
	private  List<AdmMenu>listaSubMenu;
	private List<AdmMenu>listaSubMenuItem;
	private MenuModel model;

	
	@EJB
	private ServicioIinicioSesion iniciosesion;
	@EJB
	private ServicioAdmMenu serviciomenu;
	
	@EJB
	private ServicioAdmRolMenu serviciorolmenu;
	@EJB
	private ServicioAdmRolUsuario serviciorolusuario;
	
	
	
	@PostConstruct
	public void init() {
		model = new DefaultMenuModel();
		cancelar(); 
		establecerPermisos();
	}
	
	public String mostrarUsuarioLogeado() {
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		return "User: "+us.getIdEmpleado().getNombres() +" "+ us.getIdEmpleado().getApellidos(); // retorno el nombre + apellido
	}

	
	public void consultarMenu() {

		listamenuusuario = new ArrayList<>();
		listamenuusuario = serviciomenu.buscaMenu();	
	}
	
	public void subMenu(int id){
		for(AdmMenu us : listamenuusuario ){
					
			}	
	}
		
	public void subItem(){
		
			
	}
	
	public void cancelar(){
		 consultarMenu();
		
	}
	
	public void establecerPermisos() { //MENU DE TRES NIVELES
		model = new DefaultMenuModel();

		int essubmenu = 0;
		DefaultSubMenu Submenu1 = null;
		DefaultSubMenu Submenu2 = null;
		DefaultMenuItem item2 = null;

		for (AdmMenu m : listamenuusuario) {// Lista nivel 1
			if (m.getIdPadre() == null) {
				Submenu1 = new DefaultSubMenu(m.getNombre());
				//System.out.println("los papa son " + m.getNombre());
				for (AdmMenu m2 : listamenuusuario) { // Lista Nivel 2
					if (m.getIdMenu().equals(m2.getIdPadre())) {
						//System.out.println("hijos de " + m.getNombre() + " son " + m2.getNombre());
						for (AdmMenu m3 : listamenuusuario) {// Lista Nivel 3
							if (m2.getIdMenu().equals(m3.getIdPadre())) {
								if (essubmenu == 0) {
									Submenu2 = new DefaultSubMenu(m2.getNombre());
									essubmenu = 1;
								}

								item2 = new DefaultMenuItem(m3.getNombre());
								item2.setUrl(m3.getUrlPagina());
								Submenu2.addElement(item2);
							}
						}
						if (essubmenu == 1) {
							Submenu1.addElement(Submenu2);
							Submenu2 = null;

							essubmenu = 0;

						} else {
							DefaultMenuItem item = new DefaultMenuItem(m2.getNombre());
							item.setUrl(m2.getUrlPagina());
							Submenu1.addElement(item);
						}
					}
				}
				model.addElement(Submenu1);
			}

		}

	}
	
	
	


	public List<AdmRol> getListarol() {
		return listarol;
	}
	
	public void setListarol(List<AdmRol> listarol) {
		this.listarol = listarol;
	}
	
	public List<AdmMenu> getListaMenuUsuario() {
		return listamenuusuario;
	}
	
	public void setListaMenuUsuario(List<AdmMenu> listamenu) {
		MenuBean.listamenuusuario = listamenu;
	}
	
	public List<AdmRolMenu> getListarolmenu() {
		return listarolmenu;
	}
	
	public void setListarolmenu(List<AdmRolMenu> listarolmenu) {
		this.listarolmenu = listarolmenu;
	}
	
	public List<AdmRolUsuario> getListarolusuario() {
		return listarolusuario;
	}
	
	public void setListarolusuario(List<AdmRolUsuario> listarolusuario) {
		this.listarolusuario = listarolusuario;
	}
	
	public MenuModel getModel() {
		return model;
	}
	
	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	public AdmUsuario getUser() {
		return (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}
	
	public List<AdmMenu> getListaSubMenu() {
		return listaSubMenu;
	}
	
	public void setListaSubMenu(List<AdmMenu> listaSubMenu) {
		this.listaSubMenu = listaSubMenu;
	}
	
	public List<AdmMenu> getListaSubMenuItem() {
		return listaSubMenuItem;
	}
	
	public void setListaSubMenuItem(List<AdmMenu> listaSubMenuItem) {
		this.listaSubMenuItem = listaSubMenuItem;
	}	

}

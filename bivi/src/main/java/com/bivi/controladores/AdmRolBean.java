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
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.TreeNode;

import com.bivi.modelos.*;
import com.bivi.servicios.*;



@ManagedBean
@ViewScoped
public class AdmRolBean implements  Serializable {
	private static final long serialVersionUID = 1L;
	
	private AdmRol admrol;

	private List<AdmRol> listaRol;
	private List<AdmRol> rolFiltrado;
	private AdmRol rolSeleccionado;
	private List<AdmRolMenu> menuAsignados;
	private AdmRolMenu rolMenu;
	
	private List<AdmMenu> menuSeleccionado;
	private List<AdmMenu> listaMenu;
	private AdmMenu menu;
	
	List <AdmMenu>obj = new ArrayList<>();
	 
	private List<AdmMenu> menuAgregar;
	private List<AdmMenu> menuQuitar;
	 
	
	@EJB
	private ServicioAdmRol servicioRol ;
	@EJB
	private ServicioAdmRolMenu servicioRolmenu ;
	@EJB
	private ServicioAdmMenu servicioMenu ;
	
	
	private TreeNode rootNode;
	private boolean bandera ;

	@PostConstruct
	public void init() {
		cancelar();
		consultaRoles();
		consultaMenu();
	}
	


	public void cancelar() {
		admrol = new AdmRol();
		menuAgregar = new ArrayList<>();
		menuQuitar = new ArrayList<>();
		rolMenu = new AdmRolMenu();
		bandera = false;
	}
	
	
	public void consultaMenu(){
		listaMenu = new ArrayList<>();
		listaMenu = servicioMenu.findAll();
		    
	    for (AdmMenu doc : listaMenu) {
	    	if(doc.getIdPadre()== null){
	    		obj.add(doc);		    
	    		 for(AdmMenu x : listaMenu){
	    			 if(doc.getIdMenu()== x.getIdPadre()){
	    				 obj.add(x);
	    				 
	    			 }
	    		}
	    	}
	    }
	}
	
	
	public void onRowSelect(SelectEvent event) {
		menu = new AdmMenu();
		menu = (AdmMenu) event.getObject();
		menuAgregar.add(menu);
	}

	
	public void onRowUnselect(UnselectEvent event) {
		
		menu = (AdmMenu) event.getObject();
		menuQuitar.add(menu); 
	}
	
	@SuppressWarnings("deprecation")
	public void asignarMenu(){
		
		if(menuAgregar.size()== 0){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un menu "));
		} else{
			
			for(AdmMenu item : menuAgregar){
	
				
				rolMenu.setIdRolMenu(servicioRolmenu.getPK());
				rolMenu.setIdMenu(item);
				rolMenu.setIdRol(rolSeleccionado);		
				servicioRolmenu.create(rolMenu);	
				
			}
		
			for(AdmMenu item : menuQuitar){
		
				servicioRolmenu.quitarMenuAsignado(rolSeleccionado, item);		
			}
		
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgRol').hide();");
	
			consultaMenuAsignados();
		}
	
	}
	
	public void eliminarMenuAsignado(){
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void nuevo(){
	menuSeleccionado = new ArrayList<>();

		RequestContext.getCurrentInstance().execute("PF('dlgRol').show();");
		
	}

	@SuppressWarnings("deprecation")
	public void modificar(){
	
		if(rolSeleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {
		
			consultaMenuAsignados();
			bandera =	true;
			admrol = rolSeleccionado;
		
			resetarFormulario();
		RequestContext.getCurrentInstance().execute("PF('dlgRol').show();");
		}
	}


	public void consultaMenuAsignados(){
	
		menuSeleccionado = new ArrayList<>();
		//Consulto todos los menu asignados al rol de 
		menuAsignados = new ArrayList<>();
		menuAsignados = servicioRolmenu.menuAsignados(rolSeleccionado);
		
		//marco solo menu asignads al rol
		
		for(AdmMenu item : listaMenu ){ //recorro la lista de todos los menu
			//hasta qui me quede
			for(AdmRolMenu rm : menuAsignados){
				if(item.getIdMenu() == rm.getIdMenu().getIdMenu()){
					
					menuSeleccionado.add(rm.getIdMenu());
		
				}		
			}
		}
	}

	public void guardar() {
		
		if(admrol.equals(null)){
			
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Debe ingresar un nombre "));
			
		}else{
			
			admrol.setIdRol(servicioRol.getPK());
			servicioRol.create(admrol);
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Rol Guardado Correctamente "));
			consultaRoles();
			cancelar();
		}
	}
 
	
	public void eliminarRol() {
		if(rolSeleccionado == null){
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe selecionar un Registro "));
					
		}else {	
		admrol = rolSeleccionado;
		servicioRol.delete(admrol);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos Eliminado Correctamente "));		
		consultaRoles();
		consultaMenu();
		cancelar();		
		}
	}

	
	public void actualizar() {
		servicioRol.update(admrol);
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Rol Modificado Correctamente "));
		cancelar();
	}

	
	public void consultaRoles() {
		listaRol = new ArrayList<>();
		listaRol = servicioRol.findAll();
	}
	
	@SuppressWarnings("deprecation")
	public static void resetarFormulario() {
	    RequestContext.getCurrentInstance().reset("frmCrear");
	}
	
	public void persistir() {
	    if (bandera == true) {
	        actualizar();
	    } else {
	        guardar();
	    }
	}
	
	
	
	
	
		


	public AdmRol getAdmrol() {
		return admrol;
	}

	public void setAdmrol(AdmRol admrol) {
		this.admrol = admrol;
	}

	public List<AdmRol> getRolFiltrado() {
		return rolFiltrado;
	}

	public void setRolFiltrado(List<AdmRol> rolFiltrado) {
		this.rolFiltrado = rolFiltrado;
	}

	public AdmRol getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(AdmRol rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public List<AdmRol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<AdmRol> listaRol) {
		this.listaRol = listaRol;
	}

	public List<AdmRolMenu> getMenuAsignados() {
		return menuAsignados;
	}

	public void setMenuAsignados(List<AdmRolMenu> menuAsignados) {
		this.menuAsignados = menuAsignados;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public List<AdmMenu> getObj() {
		return obj;
	}
	public List<AdmMenu> getMenuSeleccionado() {
		return menuSeleccionado;
	}
	
	public void setMenuSeleccionado(List<AdmMenu> menuSeleccionado) {
		this.menuSeleccionado = menuSeleccionado;
	}

	public void setObj(List<AdmMenu> obj) {
		this.obj = obj;
	}

	public AdmRolMenu getRolMenu() {
		return rolMenu;
	}

	public void setRolMenu(AdmRolMenu rolMenu) {
		this.rolMenu = rolMenu;
	}

	public List<AdmMenu> getMenuAgregar() {
		return menuAgregar;
	}

	public void setMenuAgregar(List<AdmMenu> menuAgregar) {
		this.menuAgregar = menuAgregar;
	}

}

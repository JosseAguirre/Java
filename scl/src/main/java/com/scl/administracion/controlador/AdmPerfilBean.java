package com.scl.administracion.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import com.scl.administracion.modelo.*;
import com.scl.administracion.servicio.*;

@ManagedBean(name = "admPerfilBean")
@ViewScoped

public class AdmPerfilBean implements Serializable {

    private static final long serialVersionUID = 1L;
    public static List<AdmUsuario> listausuario;
    public static List<AdmRol> listarol;
	public static List<AdmMenu> listamenu;
    private List<AdmRol> listaorigen;
    private List<AdmRol> listadestino;
    private DualListModel<AdmRol> dualListRoles;

    private DualListModel<AdmMenu> dualListMenu;
    private List<AdmMenu> listaOrigenMenu;
    private List<AdmMenu> listaDestinoMenu;

    private AdmUsuario admusuario;
    private AdmRol admrol;
    private AdmMenu admmenu;
    private AdmRolUsuario admrolusuario;
    private AdmRolMenu admrolmenu;

    private List<AdmRolUsuario> listarolasignado;
    private List<AdmRolMenu> listamenuasignado;
    
   
    private List<AdmRol> rolseleccionado;
    private AdmRolUsuario rolusuarioselecionado;
    private List<AdmMenu> menuseleccionado;

    private List<AdmUsuario> usuariofiltrado;
    private List<AdmRol> rolfiltrado;
    
    int tipoTransferencia;
    
    int idRol;

    @EJB
    private ServicioAdmRol serviciorol;

    @EJB
    private ServicioAdmUsuario serviciousuario;

    @EJB
    private ServicioAdmRolUsuario serviciorolusuario;

    @EJB
    private ServicioAdmMenu serviciomenu;

    @EJB
    private ServicioAdmRolMenu serviciorolmenu;

    @PostConstruct
    public void init() {
        cancelar();

        listaorigen = new ArrayList<>();
        listadestino = new ArrayList<>();
        dualListRoles = new DualListModel<>();

        listaOrigenMenu = new ArrayList<>();
        listaDestinoMenu = new ArrayList<>();
        dualListMenu = new DualListModel<>();

    }

    // Metodo que carga las listas 
    public void consultar() {
        listausuario = new ArrayList<>();
        listausuario = serviciousuario.findAll(); // carga la lista de todos los usuarios

      

        listamenu = new ArrayList<>();
        listamenu = serviciomenu.findAll(); // carga la lista de todos los menues

    }
    
    public void consultarRoles() {
    	  listarol = new ArrayList<>();
          listarol = serviciorol.findAll(); // carga la lista de todos los roles
    	
    }
    
    

    //Metodo que instancia nuevos objetos
    public void cancelar() {
        admrolusuario = new AdmRolUsuario();
        admusuario = new AdmUsuario();
        admrol = new AdmRol();
        admrolmenu = new AdmRolMenu();
        admmenu = new AdmMenu();

        tipoTransferencia = 0;
        rolseleccionado = new ArrayList<>();
        menuseleccionado = new ArrayList<>();
       

    }

    //####### CODIGOS  EN REFRENCIA A LA RELACION ROLES-USUARIOS###########
    // Metodo para guardar uno o varios roles del usuario
    public void guardarRol() {

        if (tipoTransferencia == 0) {
            if (rolseleccionado.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "No existen Roles Seleccionados"));
            } else {
                for (AdmRol rol : rolseleccionado) { // Recorro la lista de los roles  seleccionados en la vista 
                    admrolusuario.setIdRolUsuario(serviciorolusuario.getPK());
                    admrolusuario.setIdRol(rol);
                    admrolusuario.setIdUsuario(admusuario);
                    serviciorolusuario.create(admrolusuario);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Roles Asignados Correctamente "));
            }
            rolseleccionado = new ArrayList<>();
            rolesAsignados();
        } else {
            eliminarRolusuario();
        }

    }

    public void onTransferRol(TransferEvent event) {
        if (event.isAdd()) {
            tipoTransferencia = 0;

            for (Object item : event.getItems()) {

                rolseleccionado.add((AdmRol) item);
            }

        }

        if (event.isRemove()) {
            tipoTransferencia = 1;
            for (Object item : event.getItems()) {

                rolseleccionado.add((AdmRol) item);
            }

        }

    }

    
    // Metodo para eliminar rol asociado a un usuario
    public void eliminarRolusuario() {

        for (AdmRol rol : rolseleccionado) {
            admrolusuario.setIdRol(rol);
            admrolusuario.setIdUsuario(admusuario);

            admrolusuario.setIdRolUsuario(serviciorolusuario.getIdRolUsuario(rol, admusuario));
            serviciorolusuario.delete(admrolusuario);

        }
        rolseleccionado = new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Roles Eliminados Correctamente"));
        rolesAsignados();
       

    }

    // Metodo que obtiene el usuario seleccionado proveniente del autocomplete
    public void onSelectItemUsuario(SelectEvent event) {
        //activarBtnAgregarRol = false;
        admusuario = (AdmUsuario) event.getObject(); // Cast del evento al tipo AdmUsuario
        rolesAsignados();

    }

    public void rolesAsignados() {
        //listarolasignado = new ArrayList<>();
        //listarolasignado = serviciorolusuario.rolesAsignados(admusuario);

        listaorigen = serviciorol.findAll();

        listadestino = serviciorol.rolesAsignados(admusuario);

        if (listadestino.isEmpty()) {

            dualListRoles = new DualListModel<>(listaorigen, listadestino);
        } else {
            List<AdmRol> rolesDisponibles;
            rolesDisponibles = new ArrayList<>();
            rolesDisponibles = serviciorol.rolesDisponibles(admusuario);

            dualListRoles = new DualListModel<>(rolesDisponibles, listadestino);

        }

    }

    // Busca en el usuario en una lista de usuarios segun las letras que digite en la vista
    public List<AdmUsuario> usuarioBuscado(String query) {
        usuariofiltrado = new ArrayList<>();
        for (int i = 0; i < listausuario.size(); i++) {
            AdmUsuario us = listausuario.get(i);
            if (us.getUsuario().contains(query.toLowerCase())) { // Comparo si el nombre contiene la caadena escrita en la vista
                usuariofiltrado.add(us);
            }
        }

        return usuariofiltrado; // devuelve los usuarios encontrado segun lo escrito en la vista
    }

    //---CODIGO REFRENCIA LA LA RELACION ROLES-MENU-----
    // Metodo para guardar uno o varios menu del rol
    public void guardarMenu() {
        for (AdmMenu menu : menuseleccionado) { // Recorro la lista de los menu  seleccionados en la vista
            admrolmenu.setIdRolMenu(serviciorolmenu.getPK()); //obtengo el ultimo ID
            admrolmenu.setIdMenu(menu);
            admrolmenu.setIdRol(admrol);
            serviciorolmenu.create(admrolmenu);

        }
        menuesAsignados();

    }

    // Metodo para eliminar menu asociado a un usuario

    public void eliminarRolmenu() {
       for (AdmMenu menu : menuseleccionado) {
            admrolmenu.setIdMenu(menu);
            admrolmenu.setIdRol(admrol);

            admrolmenu.setIdRolMenu(serviciorolmenu.getIdRolmenu(menu, admrol));
            serviciorolmenu.delete(admrolmenu);

        }
        menuseleccionado = new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Menues Eliminados Correctamente"));
        menuesAsignados();

    }


    // Metodo que obtiene el rol seleccionado proveniente del autocomplete
    public void onSelectItemRol(SelectEvent event) {

        admrol = (AdmRol) event.getObject(); // Cast del evento al tipo AdmRol
        menuesAsignados();

    }
    //Metodo que carga la lista de los menues asignadaos al rol

    public void menuesAsignados() {
        listaOrigenMenu = serviciomenu.findAll();
        listaDestinoMenu = serviciomenu.menuAsignados(admrol);
        if (listaDestinoMenu.isEmpty()) {
            dualListMenu = new DualListModel<>(listaOrigenMenu, listaDestinoMenu);
        } else {
            List<AdmMenu> menuDisponibles;
            menuDisponibles = new ArrayList<>();
            menuDisponibles = serviciomenu.menuDisponibles(admrol);
            dualListMenu = new DualListModel<>(menuDisponibles, listaDestinoMenu);
        }

    }

    // Busca en el usuario en una lista de roles segun las letras que digite en la vista
    public List<AdmRol> rolBuscado(String query) {
        rolfiltrado = new ArrayList<>();
        for (int i = 0; i < listarol.size(); i++) {
            AdmRol rol = listarol.get(i);
            if (rol.getNombre().contains(query.toUpperCase())) { // Comparo si el nombre contiene la caadena escrita en la vista
                rolfiltrado.add(rol);
            }
        }

        return rolfiltrado; // devuelve los usuarios encontrado segun lo escrito en la vista
    }

    public void onTransferMenu(TransferEvent event) {
        if (event.isAdd()) {
            tipoTransferencia = 0;
            for (Object item : event.getItems()) {
                menuseleccionado.add((AdmMenu) item);
            }
        }
        if (event.isRemove()) {
            tipoTransferencia = 1;
            for (Object item : event.getItems()) {
                menuseleccionado.add((AdmMenu) item);
            }
        }
    }

    public void guardarRolMenu() {
        if (tipoTransferencia == 0) {
            if (menuseleccionado.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "No existen Menues Seleccionados"));
            } else {
                for (AdmMenu menu : menuseleccionado) { // Recorro la lista de los roles  seleccionados en la vista 
                    admrolmenu.setIdRolMenu(serviciorolmenu.getPK());
                    admrolmenu.setIdMenu(menu);
                    admrolmenu.setIdRol(admrol);
                    serviciorolmenu.create(admrolmenu);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Menues Asignados Correctamente "));
            }
            menuseleccionado = new ArrayList<>();
            menuesAsignados();
        } else {
            eliminarRolmenu();
        }
    }

    public AdmUsuario getAdmUsuario() {
        return admusuario;
    }

    public void setAdmUsuario(AdmUsuario admusuario) {
        this.admusuario = admusuario;
    }

    public List<AdmRolUsuario> getListarolasignado() {
        return listarolasignado;
    }

    public void setListarolasignado(List<AdmRolUsuario> listarolasignado) {
        this.listarolasignado = listarolasignado;
    }

    public List<AdmRol> getRolseleccionado() {
        return rolseleccionado;
    }

    public void setRolseleccionado(List<AdmRol> rolseleccionado) {
        this.rolseleccionado = rolseleccionado;
    }

    public AdmRolUsuario getAdmRolUsuario() {
        return admrolusuario;
    }

    public void setAdmRolUsuario(AdmRolUsuario admrolusuario) {
        this.admrolusuario = admrolusuario;
    }

    public AdmRol getAdmRol() {
        return admrol;
    }

    public void setAdmRol(AdmRol admrol) {
        this.admrol = admrol;
    }

    public List<AdmRol> getListarol() {
        return listarol;
    }

    public void setListarol(List<AdmRol> listarol) {
        AdmPerfilBean.listarol = listarol;
    }

    public AdmMenu getAdmMenu() {
        return admmenu;
    }

    public void setAdmMenu(AdmMenu admmenu) {
        this.admmenu = admmenu;
    }

    public List<AdmMenu> getListamenu() {
        return listamenu;
    }

    public void setListamenu(List<AdmMenu> listamenu) {
        this.listamenu = listamenu;
    }

    public List<AdmRolMenu> getListamenuasignado() {
        return listamenuasignado;
    }

    public void setListamenuasignado(List<AdmRolMenu> listamenuasignado) {
        this.listamenuasignado = listamenuasignado;
    }

    public AdmRolMenu getAdmRolMenu() {
        return admrolmenu;
    }

    public void setAdmRolMenu(AdmRolMenu admrolmenu) {
        this.admrolmenu = admrolmenu;
    }

    public List<AdmMenu> getMenuseleccionado() {
        return menuseleccionado;
    }

    public void setMenuseleccionado(List<AdmMenu> menuseleccionado) {
        this.menuseleccionado = menuseleccionado;
    }

    public AdmRolUsuario getRolusuarioselecionado() {
        return rolusuarioselecionado;
    }

    public void setRolusuarioselecionado(AdmRolUsuario rolusuarioselecionado) {
        this.rolusuarioselecionado = rolusuarioselecionado;
    }

    public List<AdmRol> getRolfiltrado() {
        return rolfiltrado;
    }

    public void setRolfiltrado(List<AdmRol> rolfiltrado) {
        this.rolfiltrado = rolfiltrado;
    }

    public List<AdmRol> getListaorigen() {
        return listaorigen;
    }

    public void setListaorigen(List<AdmRol> listaorigen) {
        this.listaorigen = listaorigen;
    }

    public List<AdmRol> getListadestino() {
        return listadestino;
    }

    public void setListadestino(List<AdmRol> listadestino) {
        this.listadestino = listadestino;
    }

    public DualListModel<AdmRol> getDualListRoles() {
        return dualListRoles;
    }

    public void setDualListRoles(DualListModel<AdmRol> dualListRoles) {
        this.dualListRoles = dualListRoles;
    }

    public DualListModel<AdmMenu> getDualListMenu() {
        return dualListMenu;
    }

    public void setDualListMenu(DualListModel<AdmMenu> dualListMenu) {
        this.dualListMenu = dualListMenu;
    }

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
    
    

}

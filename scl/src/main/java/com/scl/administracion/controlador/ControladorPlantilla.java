package com.scl.administracion.controlador;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import com.scl.administracion.modelo.*;


@ManagedBean
@ViewScoped
public class ControladorPlantilla implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	
	//metodo que verifica si existe una sesion abierta
	public void verificarSesion()  {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			AdmUsuario usuario = (AdmUsuario) context.getExternalContext().getSessionMap().get("usuario"); // obtengo el usuario de la sesion que se haya logueado
			
			if(usuario == null){ // compruebo si el usuario es nulo
				System.out.println("el usuer es nulo");
				 
				context.getExternalContext().redirect("/scl/login.xhtml"); //si es nulo redirecciono a la pagina prinicpal
			}
			
			
			
		} catch(Exception e) {
			
			
		}finally {}}}

package com.scl.administracion.controlador;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import com.scl.administracion.modelo.*;
import com.scl.administracion.servicio.*;

//
@ManagedBean
@SessionScoped
public class SesionBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private AdmUsuario usuario;
        
        



	
	@EJB
	private ServicioIinicioSesion iniciosesion;
	

	@PostConstruct
	public void init() {
		cancelar();
                 
                

	}

	
	
	public void cancelar()  {
		usuario = new AdmUsuario();
                
           
        
        
		
		

	}
	
	public static String encriptarSHA512(String palabra) {
        StringBuilder h = null;
        try {
            if (palabra != null) {
                if (!palabra.equals("")) {
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    byte[] b = md.digest(palabra.getBytes());
                   int size = b.length;
                    h = new StringBuilder(size);
                    for (int i = 0; i < size; i++) {
                        int u = b[i] & 255; // unsigned conversion
                        if (u < 16) {
                            h.append("0");
                            h.append(Integer.toHexString(u));
                            //h.append("0"+Integer.toOctalString(u));
                        } else {
                            h.append(Integer.toHexString(u));
                            // h.append(Integer.toOctalString(u));
                        }
                    }
                    System.out.println("cifrado es. "+ h);
                    return h.toString();
                }
            }
           
        } catch (NoSuchAlgorithmException ex) {
            //JOptionPane.showMessageDialog(null, "Error en Encriptacion" + ex);
        }
        return null;
    }
	
	
	
	public String iniciarSesion(){
		usuario.setPassword(encriptarSHA512(usuario.getPassword()));
		AdmUsuario us;
		String redireccion = null;
		//System.out.println("sale" + usuario.getNombreusuario() + usuario.getContrasenia());
		try {
			us = iniciosesion.iniciarSesion(usuario);
			if (us != null) { //compruebo que el usuario exista
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us); // guardo el usuario de la sesion
				redireccion = "index?faces-redirect=true"; // almaceno la direccion de la pagina que debe abrirse despues de loguearse
			} else {
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales incorrectas", null));
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Se ha poducido un error al iniciar sesion", null));
		}
		
		return redireccion;
	}
	
	
	
	public String cerrarSesion(){
		String redireccion = null;
		try {
			redireccion = "index?faces-redirect=true";
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();	//invalido la sesion actual
			
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL, "Se ha poducido un error al iniciar sesion", null));
		}
		return redireccion;
	}


	
	public AdmUsuario getUsuario() {
		return usuario;
	}


	public void setUsuario(AdmUsuario usuario) {
		this.usuario = usuario;
	}


	public String mostrarUsuarioLogeado() {
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		return us.getIdEmpleado().getNombres() +" "+ us.getIdEmpleado().getApellidos(); // retorno el nombre + apellido
	}
	
	public String mostrarnombre() {
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		return "User: "+us.getIdEmpleado().getNombres() +" "; // retorno el nombre 
	}
	
	public String mostrarapellido() {
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //obtengo el usuario logueado
		return "User: "+ us.getIdEmpleado().getApellidos() +" "; // retorno el apellido
	}
	
	
}

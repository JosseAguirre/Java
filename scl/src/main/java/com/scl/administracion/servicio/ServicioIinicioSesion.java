package com.scl.administracion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;


@Stateless
public class ServicioIinicioSesion {
	
	AdmUsuario usuario;
	List <AdmUsuario> listausuarios;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	public AdmUsuario iniciarSesion (AdmUsuario usuario){
		
		this.usuario= null;	
		try {
			// consulto en la bdd el username y passsword
			Query query = em.createQuery("select a from AdmUsuario a where a.usuario = ?1 and a.password = ?2");
			query.setParameter(1, usuario.getUsuario()); //paso como parametros el nombre de usuario
			query.setParameter(2, usuario.getPassword()); ////paso como parametros la contraseña
			listausuarios = query.getResultList(); // añado a lista el resultado de la consulta
			
			if(!listausuarios.isEmpty()){ // comparo si la lista no esta vacia
				this.usuario = listausuarios.get(0); // obtengo el primer item de la lista
	
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		
		return this.usuario;
		
	}
	

}

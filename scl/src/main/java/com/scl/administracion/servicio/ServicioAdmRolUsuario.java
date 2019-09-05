package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;
import com.scl.operacion.modelo.OpeVehiculo;

@Stateless
public class ServicioAdmRolUsuario {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmRolUsuario admrolusuario) {
		em.persist(admrolusuario);
	}
	
	public void delete(AdmRolUsuario admrolusuario) {
		em.remove(em.merge(admrolusuario));
	}
	
	public void update(AdmRolMenu admrolusuario){
	
		em.merge(admrolusuario);
		
		
	}
	
	public void eliminarRol(int idRol){
		
	 em.createNativeQuery("delete from java.adm_rol_usuario where id_rol_usuario ="+idRol+" ").executeUpdate();
		
		
	}
	
	
	
	

	@SuppressWarnings("unchecked")
	public List<AdmRolUsuario> rolesAsignados(AdmUsuario us) {
		List <AdmRolUsuario> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select ru.id_rol_usuario, r.nombre from java.adm_rol_usuario as ru "+
"inner join java.adm_rol as r on r.id_rol = ru.id_rol "+
"where ru.id_usuario = "+us.getIdUsuario()); 
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmRolUsuario e = new AdmRolUsuario();
			e.setIdRolUsuario(Integer.parseInt(row[0].toString()));
			
			AdmRol r = new AdmRol();			
			r.setNombre(row[1].toString());
			
			e.setIdRol(r);
			
			lista.add(e);
			
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmRolUsuario> findAll() {
		Query q = em.createQuery("select c from AdmRolUsuario c ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmRolUsuario ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public Integer getIdRolUsuario(AdmRol rol, AdmUsuario us) {
		Integer codigo = 0;
		Query q = em.createQuery("select c.idrolusuario from AdmRolUsuario c where c.idrol = "+rol.getIdRol()+" and c.idusuario ="+us.getIdUsuario());
		codigo = (Integer) q.getSingleResult();
		
		return codigo;
	}
	
	public AdmRolUsuario findOne(Integer codigorolmmenu){
		Query q = em.createQuery("select c from AdmRolUsuario c where c.idrolmenu = " + codigorolmmenu);
		return (AdmRolUsuario) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmRolUsuario> buscaTripulante(int idCiudad, String fecha) {
		Query q = em.createQuery("select a from AdmRolUsuario a where a.idRol = 2 and a.idUsuario not in ( select q.idUsuario from AdmUsuario q where q.idEmpleado in (select c.idEmpleado from OpeDetalleTripulacion c where c.idTripulacion in (select m.idTripulacion from OpeTripulacion m  where m.fechaOperacion ='"+fecha+"'))) and a.idUsuario.idEmpleado.idCiudadDc = "+idCiudad+")");
		return q.getResultList();
	}
	
	
	
	
	
	
	



}

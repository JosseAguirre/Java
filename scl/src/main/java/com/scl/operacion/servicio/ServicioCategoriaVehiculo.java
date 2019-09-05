package com.scl.operacion.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioCategoriaVehiculo {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeCategoriaVehiculo categoria) {
		em.persist(categoria);
	}
	
	public void delete(OpeCategoriaVehiculo categoria) {
		em.remove(em.merge(categoria));
	}
	
	public void update(OpeCategoriaVehiculo categoria){
	
		em.merge(categoria);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeCategoriaVehiculo> findAll() {
		Query q = em.createQuery("select a from OpeCategoriaVehiculo a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeCategoriaVehiculo ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeCategoriaVehiculo findOne(Integer idCategoriaVehiculo){
		Query q = em.createQuery("select a from OpeCategoriaVehiculo a where a.idCategoriaVehiculo = " + idCategoriaVehiculo);
		return (OpeCategoriaVehiculo) q.getSingleResult();
	}

	



}


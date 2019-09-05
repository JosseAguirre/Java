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
public class ServicioSeguroVehiculo {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeSeguroVehiculo seguroVehiculo) {
		em.persist(seguroVehiculo);
	}
	
	public void delete(OpeSeguroVehiculo seguroVehiculo) {
		em.remove(em.merge(seguroVehiculo));
	}
	
	public void update(OpeSeguroVehiculo seguroVehiculo){
	
		em.merge(seguroVehiculo);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeSeguroVehiculo> findAll() {
		Query q = em.createQuery("select a from OpeSeguroVehiculo a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeSeguroVehiculo ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeSeguroVehiculo findOne(Integer idSeguroVehiculo){
		Query q = em.createQuery("select a from OpeSeguroVehiculo a where a.idResponsable = " + idSeguroVehiculo);
		return (OpeSeguroVehiculo) q.getSingleResult();
	}

	



}


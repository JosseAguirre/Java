package com.scl.operacion.servicio;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.*;

@Stateless
public class ServicioDetalleTripulacion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeDetalleTripulacion detalleTripulacion) {
		em.persist(detalleTripulacion);
	}
	
	public void delete(OpeDetalleTripulacion detalleTripulacion) {
		em.remove(em.merge(detalleTripulacion));
	}
	
	public void update(OpeDetalleTripulacion detalleTripulacion){
	
		em.merge(detalleTripulacion);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTripulacion> findAll() {
		Query q = em.createQuery("select a from OpeDetalleTripulacion a");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeDetalleTripulacion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeDetalleTripulacion findOne(Integer idDetalleTripulacion){
		Query q = em.createQuery("select a from OpeDetalleTripulacion a where a.idDetalleTripulacion = " + idDetalleTripulacion);
		return (OpeDetalleTripulacion) q.getSingleResult();
	}

		

	



}


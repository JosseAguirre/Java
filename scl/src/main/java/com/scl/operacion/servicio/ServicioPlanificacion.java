package com.scl.operacion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.OpePlanificacion;



@Stateless
public class ServicioPlanificacion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpePlanificacion planificada) {
		em.persist(planificada);
	}
	
	public void delete(OpePlanificacion planificada) {
		em.remove(em.merge(planificada));
	}
	
	public void update(OpePlanificacion planificada){
	
		em.merge(planificada);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpePlanificacion> findAll() {
		Query q = em.createQuery("select a from OpePlanificacion a");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpePlanificacion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpePlanificacion findOne(Integer idplanificada){
		Query q = em.createQuery("select a from OpePlanificacion a where a.idPlanificacion = " + idplanificada);
		return (OpePlanificacion) q.getSingleResult();
	}

	
	@SuppressWarnings("unchecked")
	public List<OpePlanificacion> buscaPlanificadas(int idCircuito) {
		Query q = em.createQuery("select a from OpePlanificacion a where a.idCircuito = "+idCircuito+"");
		return q.getResultList();
	}
	




}

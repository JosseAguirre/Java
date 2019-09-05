package com.scl.operacion.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.*;

@Stateless
public class ServicioMotivoNoGestion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeMotivoNoGestion motivoNoGestion) {
		em.persist(motivoNoGestion);
	}
	
	public void delete(OpeMotivoNoGestion motivoNoGestion) {
		em.remove(em.merge(motivoNoGestion));
	}
	
	public void update(OpeMotivoNoGestion motivoNoGestion){
	
		em.merge(motivoNoGestion);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeMotivoNoGestion> findAll() {
		Query q = em.createQuery("select a from OpeMotivoNoGestion a");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeMotivoNoGestion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeMotivoNoGestion findOne(Integer idMotivoNoGestion){
		Query q = em.createQuery("select a from OpeMotivoNoGestion a where a.idMotivo = " + idMotivoNoGestion);
		return (OpeMotivoNoGestion) q.getSingleResult();
	}

	



}


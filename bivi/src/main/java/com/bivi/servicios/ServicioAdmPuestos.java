package com.bivi.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioAdmPuestos {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmPuesto admpuesto) {
		em.persist(admpuesto);
	}
	
	public void delete(AdmPuesto admpuesto) {
		em.remove(em.merge(admpuesto));
	}
	
	public void update(AdmPuesto admpuesto){
		em.merge(admpuesto);	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmPuesto> findAll() { // Busca todo de la tabla AdmPuesto
		Query q = em.createQuery("select b from AdmPuesto b   ");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla AdmPuesto y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmPuesto ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from AdmPuesto");
		return (Integer) q.getSingleResult();
	}
	
	public AdmAgencia findOne(Integer idPuesto){
		Query q = em.createQuery("select a from AdmPuesto a where a.idPuesto = " + idPuesto);
		return (AdmAgencia) q.getSingleResult();
	}
	
}

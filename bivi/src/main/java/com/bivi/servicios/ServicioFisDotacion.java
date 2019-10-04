package com.bivi.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisDotacion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisDotacion fisdotacion) {
		em.persist(fisdotacion);
	}
	
	public void delete(FisDotacion fisdotacion) {
		em.remove(em.merge(fisdotacion));
	}
	
	public void update(FisDotacion fisdotacion){
		em.merge(fisdotacion);	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FisDotacion> findAll() { // Busca todo de la tabla FisDotacion
		Query q = em.createQuery("select b from FisDotacion b order by b.idDotacion ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla FisDotacion y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisDotacion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from FisDotacion");
		return (Integer) q.getSingleResult();
	}
	
	public FisDotacion findOne(Integer idDotacion){
		Query q = em.createQuery("select a from FisDotacion a where a.idDotacion = " + idDotacion);
		return (FisDotacion) q.getSingleResult();
	}
	
}

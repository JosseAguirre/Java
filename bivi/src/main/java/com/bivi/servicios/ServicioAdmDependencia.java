package com.bivi.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.AdmDependencia;;

@Stateless
public class ServicioAdmDependencia {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmDependencia admdependencia) {
		em.persist(admdependencia);
	}
	
	public void delete(AdmDependencia admdependencia) {
		em.remove(em.merge(admdependencia));
	}
	
	public void update(AdmDependencia admdependencia){
		em.merge(admdependencia);	
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmDependencia> findAll() { // Busca todo de la tabla AdmDependencia
		Query q = em.createQuery("select b from AdmDependencia b order by b.idDependencia ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla AdmPuesto y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmDependencia");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from AdmDependencia");
		return (Integer) q.getSingleResult();
	}
	
	public AdmDependencia findOne(Integer idDependencia){
		Query q = em.createQuery("select a from AdmDependencia a where a.idDependencia = " + idDependencia);
		return (AdmDependencia) q.getSingleResult();
	}

}

package com.bivi.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.AdmResponsablesDependencia;

@Stateless
public class ServicioAmdResponsableDependencia {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmResponsablesDependencia admresponsabledependencia) {
		em.persist(admresponsabledependencia);
	}
	
	public void delete(AdmResponsablesDependencia admresponsabledependencia) {
		em.remove(em.merge(admresponsabledependencia));
	}
	
	public void update(AdmResponsablesDependencia admresponsabledependencia){
		em.merge(admresponsabledependencia);	
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmResponsablesDependencia> findAll() { // Busca todo de la tabla AdmResponsablesDependencia
		Query q = em.createQuery("select b from AdmResponsablesDependencia b order by b.idResponsableDependencia ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla AdmResponsablesDependencia y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmResponsablesDependencia ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from AdmResponsablesDependencia");
		return (Integer) q.getSingleResult();
	}
	
	public AdmResponsablesDependencia findOne(Integer idResponsableDependencia){
		Query q = em.createQuery("select a from AdmResponsablesDependencia a where a.idResponsableDependencia = " + idResponsableDependencia);
		return (AdmResponsablesDependencia) q.getSingleResult();
	}

}

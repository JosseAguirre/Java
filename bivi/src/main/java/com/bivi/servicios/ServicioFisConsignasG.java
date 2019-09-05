package com.bivi.servicios;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisConsignasG {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisConsignasGenerales consignasGenreales) {
		em.persist(consignasGenreales);
	}
	
	public void delete(FisConsignasGenerales consignasGenreales) {
		em.remove(em.merge(consignasGenreales));
	}
	
	public void update(FisConsignasGenerales consignasGenreales){
	
		em.merge(consignasGenreales);
		
		
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<FisConsignasGenerales> findConsigGen() {
		Query q = em.createQuery("select a from FisConsignasGenerales a   ");

		
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisConsignasGenerales ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmRol findOne(Integer codigoAdmRol){
		Query q = em.createQuery("select c from FisConsignasGenerales c where c.idConsignaG = " + codigoAdmRol);
		return (AdmRol) q.getSingleResult();
	}


	
	


}

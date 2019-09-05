package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisConsignasE {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisConsignasEspecificasPuesto consignasEspecificas) {
		em.persist(consignasEspecificas);
	}
	
	public void delete(FisConsignasEspecificasPuesto consignasEspecificas) {
		em.remove(em.merge(consignasEspecificas));
	}
	
	public void update(FisConsignasEspecificasPuesto consignasEspecificas){
	
		em.merge(consignasEspecificas);
		
		
	}
	

	
	
	@SuppressWarnings("unchecked")
	public List<FisConsignasEspecificasPuesto> findConsigEsp() {
		Query q = em.createQuery("select a from FisConsignasEspecificasPuesto a   ");

		
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisConsignasEspecificasPuesto ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmRol findOne(Integer codigoAdmRol){
		Query q = em.createQuery("select c from FisConsignasEspecificasPuesto c where c.idConsignaEspecificaPuesto = " + codigoAdmRol);
		return (AdmRol) q.getSingleResult();
	}


	
	


}

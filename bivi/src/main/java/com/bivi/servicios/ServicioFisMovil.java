package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisMovil {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisMovil movil) {
		em.persist(movil);
	}
	
	public void delete(FisMovil movil) {
		em.remove(em.merge(movil));
	}
	
	public void update(FisMovil movil){
	
		em.merge(movil);
		
		
	}
	

	
	
	@SuppressWarnings("unchecked")
	public List<FisMovil> buscaMovil() {
		Query q = em.createQuery("select a from FisMovil a   ");

		
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisMovil ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmRol findOne(Integer codigoAdmRol){
		Query q = em.createQuery("select c from FisMovil c where c.idConsignaEspecificaPuesto = " + codigoAdmRol);
		return (AdmRol) q.getSingleResult();
	}


	
	


}

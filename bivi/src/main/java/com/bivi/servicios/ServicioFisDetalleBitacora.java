package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisDetalleBitacora {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisDetalleBitacora detalleBitacora) {
		em.persist(detalleBitacora);
	}
	
	public void delete(FisDetalleBitacora detalleBitacora) {
		em.remove(em.merge(detalleBitacora));
	}
	
	public void update(FisDetalleBitacora detalleBitacora){
		em.merge(detalleBitacora);
	}
	

	
	
	@SuppressWarnings("unchecked")
	public List<FisDetalleBitacora> buscaBitacora() {
		Query q = em.createQuery("select a from FisDetalleBitacora a  order by a.idDetalleBitacora ASC ");
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisDetalleBitacora ");
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

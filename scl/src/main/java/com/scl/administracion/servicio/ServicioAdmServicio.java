package com.scl.administracion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;





@Stateless
public class ServicioAdmServicio {//prueba
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmServicio admServicio) {
		em.persist(admServicio);
	}
	
	public void delete(AdmServicio admServicio) {
		em.remove(em.merge(admServicio));
	}
	
	public void update(AdmServicio admServicio){
	
		em.merge(admServicio);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmServicio> findAll() {
		Query q = em.createQuery("select c from AdmServicio c where c.sysdelete = false");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmServicio");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmServicio findOne(Integer codigoAdmServicio){
		Query q = em.createQuery("select c from AdmServicio c where c.idservicio = " + codigoAdmServicio);
		return (AdmServicio) q.getSingleResult();
	}
	
	
	

}

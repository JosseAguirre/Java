package com.scl.administracion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;


@Stateless
public class ServicioAdmTipoServicio {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmTipoServicio admTipoServicio) {
		em.persist(admTipoServicio);
	}
	
	public void delete(AdmTipoServicio admTipoServicio) {
		em.remove(em.merge(admTipoServicio));
	}
	
	public void update(AdmTipoServicio admTipoServicio){
	
		em.merge(admTipoServicio);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmTipoServicio> findAll() {
		Query q = em.createQuery("select c from AdmTipoServicio c");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmTipoServicio ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmTipoServicio findOne(Integer codigoAdmTipoServicio){
		Query q = em.createQuery("select c from AdmTipoServicio c where c.idtiposervicio = " + codigoAdmTipoServicio);
		return (AdmTipoServicio) q.getSingleResult();
	}
	
	public AdmTipoServicio findSolicitud(){
		Query q = em.createQuery("select c from AdmTipoServicio c where c.idTipoServicio = 3");
		return (AdmTipoServicio) q.getSingleResult();
	}	
	
	public AdmTipoServicio findReclamo(){
		Query q = em.createQuery("select c from AdmTipoServicio c where c.idTipoServicio = 2");
		return (AdmTipoServicio) q.getSingleResult();
	}	
	

}

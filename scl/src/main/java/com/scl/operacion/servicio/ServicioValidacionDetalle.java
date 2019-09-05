package com.scl.operacion.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioValidacionDetalle {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeValidacionDetalle validacion) {
		em.persist(validacion);
	}
	
	public void delete(OpeValidacionDetalle validacion) {
		em.remove(em.merge(validacion));
	}
	
	public void update(OpeValidacionDetalle validacion){
	
		em.merge(validacion);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<OpeValidacionDetalle> findAll() {
		Query q = em.createQuery("select a from OpeValidacionDetalle a ");
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeValidacionDetalle ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeValidacionDetalle findOne(Integer idtipoTarifa){
		Query q = em.createQuery("select a from OpeTipoTarifa a where a.idTipoTarifa = " + idtipoTarifa);
		return (OpeValidacionDetalle) q.getSingleResult();
	}

	



}


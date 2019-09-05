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
public class ServicioEstadoTransaccion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeEstadoTransaccion estadoTransaccion) {
		em.persist(estadoTransaccion);
	}
	
	public void delete(OpeEstadoTransaccion estadoTransaccion) {
		em.remove(em.merge(estadoTransaccion));
	}
	
	public void update(OpeEstadoTransaccion estadoTransaccion){
	
		em.merge(estadoTransaccion);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<OpeEstadoTransaccion> findAll() {
		Query q = em.createQuery("select a from OpeEstadoTransaccion a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeEstadoTransaccion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeEstadoTransaccion findOne(Integer idEstado){
		Query q = em.createQuery("select a from OpeEstadoTransaccion a where a.idEstado = " + idEstado);
		return (OpeEstadoTransaccion) q.getSingleResult();
	}

	



}


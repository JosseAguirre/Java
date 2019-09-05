package com.scl.operacion.servicio;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.*;

@Stateless
public class ServicioDetalleTransaccionTmp {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeDetalleTransaccionTmp detalleTransaccion) {
		em.persist(detalleTransaccion);
	}
	
	public void delete(OpeDetalleTransaccionTmp detalleTransaccion) {
		em.remove(em.merge(detalleTransaccion));
	}
	
	public void update(OpeDetalleTransaccionTmp detalleTransaccion){
	
		em.merge(detalleTransaccion);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTransaccionTmp> findAll() {
		Query q = em.createQuery("select a from OpeDetalleTransaccion a");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTransaccionTmp> buscaDetalle(Integer idTransaccion) {
		Query q = em.createQuery("select a from OpeDetalleTransaccion a where a.idTransaccion ="+idTransaccion);
		return q.getResultList();
	}
	

	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeDetalleTransaccionTmp ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeDetalleTransaccionTmp findOne(Integer idDetalleTransaccion){
		Query q = em.createQuery("select a from OpeDetalleTransaccionTmp a where a.idDetalleTransaccion = " + idDetalleTransaccion);
		return (OpeDetalleTransaccionTmp) q.getSingleResult();
	}



	



}


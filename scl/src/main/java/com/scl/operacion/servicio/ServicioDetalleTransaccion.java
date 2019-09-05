package com.scl.operacion.servicio;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.*;

@Stateless
public class ServicioDetalleTransaccion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeDetalleTransaccion detalleTransaccion) {
		em.persist(detalleTransaccion);
	}
	
	public void delete(OpeDetalleTransaccion detalleTransaccion) {
		em.remove(em.merge(detalleTransaccion));
	}
	
	public void update(OpeDetalleTransaccion detalleTransaccion){
	
		em.merge(detalleTransaccion);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTransaccion> findAll() {
		Query q = em.createQuery("select a from OpeDetalleTransaccion a");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTransaccion> buscaDetalle(Integer idTransaccion) {
		Query q = em.createQuery("select a from OpeDetalleTransaccion a where a.idTransaccion ="+idTransaccion);
		return q.getResultList();
	}
	
	public void actualizaValidado( int selloPend){
		 em.createNativeQuery("update  java.ope_detalle_transaccion set validado_ingreso = true where id_detalle_transaccion  in ("+selloPend+")").executeUpdate();
		
		
	}
	
	
	public void actualizaValidadoS( int selloPend){
		 em.createNativeQuery("update  java.ope_detalle_transaccion set validado_salida = true where id_detalle_transaccion  in ("+selloPend+")").executeUpdate();
		
		
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeDetalleTransaccion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeDetalleTransaccion findOne(Integer idDetalleTransaccion){
		Query q = em.createQuery("select a from OpeDetalleTransaccion a where a.idDetalleTransaccion = " + idDetalleTransaccion);
		return (OpeDetalleTransaccion) q.getSingleResult();
	}



	



}


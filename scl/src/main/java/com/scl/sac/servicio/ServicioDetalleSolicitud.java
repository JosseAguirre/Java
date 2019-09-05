package com.scl.sac.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;
import com.scl.sac.modelo.SacDetalleSolicitudServicio;

@Stateless
public class ServicioDetalleSolicitud {
	@PersistenceContext
	private EntityManager em;
	
	public void create(SacDetalleSolicitudServicio detalleSolicitud) {
		em.persist(detalleSolicitud);
	}
	
	public void delete(SacDetalleSolicitudServicio detalleSolicitud) {
		em.remove(em.merge(detalleSolicitud));
	}
	
	public void update(SacDetalleSolicitudServicio detalleSolicitud){
	
		em.merge(detalleSolicitud);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<SacDetalleSolicitudServicio> findAll() {
		Query q = em.createQuery("select a from SacDetalleSolicitudServicio a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from SacDetalleSolicitudServicio ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public SacDetalleSolicitudServicio findOne(Integer iddetalleSolicitud){
		Query q = em.createQuery("select a from SacDetalleSolicitudServicio a where a.idTipoTarifa = " + iddetalleSolicitud);
		return (SacDetalleSolicitudServicio) q.getSingleResult();
	}

	



}


package com.scl.sac.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;
import com.scl.sac.modelo.SacSolicitudServicio;

@Stateless
public class ServicioSolicitud {
	@PersistenceContext
	private EntityManager em;
	
	public void create(SacSolicitudServicio solicitud) {
		em.persist(solicitud);
	}
	
	public void delete(SacSolicitudServicio solicitud) {
		em.remove(em.merge(solicitud));
	}
	
	public void update(SacSolicitudServicio solicitud){
	
		em.merge(solicitud);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<SacSolicitudServicio> findAll() {
		Query q = em.createQuery("select a from SacSolicitudServicio a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from SacSolicitudServicio ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public SacSolicitudServicio findOne(Integer idsolicitud){
		Query q = em.createQuery("select a from SacSolicitudServicio a where a.idTipoTarifa = " + idsolicitud);
		return (SacSolicitudServicio) q.getSingleResult();
	}

	



}


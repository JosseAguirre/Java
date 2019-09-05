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
public class ServicioResponsableAgencia {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeResponsable responsable) {
		em.persist(responsable);
	}
	
	public void delete(OpeResponsable responsable) {
		em.remove(em.merge(responsable));
	}
	
	public void update(OpeResponsable responsable){
	
		em.merge(responsable);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeResponsable>  buscaResponsableCiudad(AdmUsuarioCiudad idCiudad) {
		Query q = em.createQuery("select a from OpeResponsable a where a.idEstadoDc = 93 and a.idAgencia.idCiudadDc = "+ idCiudad.getIdCiudadDc().getIdDetalleCatalogo());
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeResponsable>  buscaResponsableCiudad(int idCiudad) {
		Query q = em.createQuery("select a from OpeResponsable a where a.idEstadoDc = 93 and a.idAgencia.idCiudadDc = "+ idCiudad);
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeResponsable ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeResponsable findOne(Integer idResponsable){
		Query q = em.createQuery("select a from OpeResponsable a where a.idResponsable = " + idResponsable);
		return (OpeResponsable) q.getSingleResult();
	}

	



}


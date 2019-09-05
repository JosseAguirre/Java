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
public class ServicioTipoTarifa {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeTipoTarifa tipoTarifa) {
		em.persist(tipoTarifa);
	}
	
	public void delete(OpeTipoTarifa tipoTarifa) {
		em.remove(em.merge(tipoTarifa));
	}
	
	public void update(OpeTipoTarifa tipoTarifa){
	
		em.merge(tipoTarifa);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<OpeTipoTarifa> findAll() {
		Query q = em.createQuery("select a from OpeTipoTarifa a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeTipoTarifa ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeTipoTarifa findOne(Integer idtipoTarifa){
		Query q = em.createQuery("select a from OpeTipoTarifa a where a.idTipoTarifa = " + idtipoTarifa);
		return (OpeTipoTarifa) q.getSingleResult();
	}

	



}


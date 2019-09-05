package com.bivi.servicios;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioAdmUsuario {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmUsuario admusuario) {
		em.persist(admusuario);
	}
	
	public void delete(AdmUsuario admusuario) {
		em.remove(em.merge(admusuario));
	}
	
	public void update(AdmUsuario admusuario){
	
		em.merge(admusuario);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmUsuario> findAll() { // Busca todo de la tabla AdmUsuario
		Query q = em.createQuery("select b from AdmUsuario b order by b.idUsuario ASC");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmUsuario ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmUsuario findOne(Integer codigoadmusuario){
		Query q = em.createQuery("select a from AdmUsuario a where a.idusuario = " + codigoadmusuario);
		return (AdmUsuario) q.getSingleResult();
	}

	

	

}

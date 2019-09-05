package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisGuardiaAgencia {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisGuardiaAgencia guardiaAgencia) {
		em.persist(guardiaAgencia);
	}
	
	public void delete(FisGuardiaAgencia guardiaAgencia) {
		em.remove(em.merge(guardiaAgencia));
	}
	
	public void update(FisGuardiaAgencia guardiaAgencia){
	
		em.merge(guardiaAgencia);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmRol> findAll() {
		List <AdmRol> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_rol, nombre from java.adm_rol  ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmRol e = new AdmRol();
			e.setIdRol(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<FisGuardiaAgencia> buscaAsigancion() {
		Query q = em.createQuery("select a from FisGuardiaAgencia a   ");

		
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisGuardiaAgencia ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmRol findOne(Integer codigoAdmRol){
		Query q = em.createQuery("select c from AdmRol c where c.idempleado = " + codigoAdmRol);
		return (AdmRol) q.getSingleResult();
	}


	
	


}

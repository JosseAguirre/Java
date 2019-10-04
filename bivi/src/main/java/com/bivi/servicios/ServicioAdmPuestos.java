package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioAdmPuestos {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmPuesto admpuesto) {
		em.persist(admpuesto);
	}
	
	public void delete(AdmPuesto admpuesto) {
		em.remove(em.merge(admpuesto));
	}
	
	public void update(AdmPuesto admpuesto){
		em.merge(admpuesto);	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmPuesto> findAll() { // Busca todo de la tabla AdmPuesto
		Query q = em.createQuery("select b from AdmPuesto b order by b.idPuesto ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla AdmPuesto y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmPuesto ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from AdmPuesto");
		return (Integer) q.getSingleResult();
	}
	
	public AdmPuesto findOne(Integer idPuesto){
		Query q = em.createQuery("select a from AdmPuesto a where a.idPuesto = " + idPuesto);
		return (AdmPuesto) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmPuesto> buscaPuestoTurno(AdmPuesto puesto) {
		Query q = em.createQuery("select f from AdmPuesto f  where f.idPuesto in (select a.idPuesto from FisPuestoTurno a where a.idUsuario = "+puesto.getIdPuesto()+")");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmPuesto> buscaPuestoTurno(int idPuesto) {
		
		List <AdmPuesto> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT  id_puesto, nombre_puesto FROM bivi.adm_puestos a where  id_puesto in "+
				"(select id_puesto from bivi.fis_puesto_turno  where id_puesto = "+idPuesto+")");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmPuesto e = new AdmPuesto();
			e.setIdPuesto(Integer.parseInt(row[0].toString()));
			e.setNombrePuesto(row[1].toString());
			
			lista.add(e);
		}
		return lista;
	}
	
}

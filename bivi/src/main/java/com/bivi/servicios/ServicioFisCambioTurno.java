package com.bivi.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisCambioTurno {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisCambioTurno cambioturno) {
		em.persist(cambioturno);
	}
	
	public void delete(FisCambioTurno cambioturno) {
		em.remove(em.merge(cambioturno));
	}
	
	public void update(FisCambioTurno cambioturno){
		em.merge(cambioturno);	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FisCambioTurno> findAll() { // Busca todo de la tabla FisDotacion
		Query q = em.createQuery("select b from FisCambioTurno b order by b.idCambioTurno ASC");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<FisCambioTurno> buscaCambiosPorPersona(AdmUsuario u) { // Busca todo de la tabla FisDotacion
		Query q = em.createQuery("select b from FisCambioTurno b where b.idUsuarioEntrega = "+u.getIdUsuario()+" order by b.idCambioTurno ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla FisDotacion y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisCambioTurno ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from FisCambioTurno");
		return (Integer) q.getSingleResult();
	}
	
	public FisCambioTurno findOne(Integer idCambioTurno){
		Query q = em.createQuery("select a from FisCambioTurno a where a.idCambioTurno = " + idCambioTurno);
		return (FisCambioTurno) q.getSingleResult();
	}
	
}

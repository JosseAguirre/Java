package com.bivi.servicios;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.FisTurno;



@Stateless
public class ServicioFisTurno {
	@PersistenceContext
	private EntityManager em;

	public void create(FisTurno fisTurno) {
		em.persist(fisTurno);
	}

	public void delete(FisTurno fisTurno) {
		em.remove(em.merge(fisTurno));
	}

	public void update(FisTurno fisTurno) {
		em.merge(fisTurno);
	}

	
	@SuppressWarnings("unchecked")
	public List<FisTurno> findAll() { // Busca todo de la tabla FisTurno
		Query q = em.createQuery("select b from FisTurno b order by b.idTurno ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla FisTurno y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisTurno ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from FisTurno");
		return (Integer) q.getSingleResult();
	}
	
	public FisTurno findOne(Integer idTurno){
		Query q = em.createQuery("select a from FisTurno a where a.idTurno = " + idTurno);
		return (FisTurno) q.getSingleResult();
	}
	
}

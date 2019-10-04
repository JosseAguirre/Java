package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.FisPuestoTurno;
import com.bivi.modelos.AdmPuesto;
import com.bivi.modelos.FisTurno;


@Stateless
public class ServicioFisPuestoTurno {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisPuestoTurno puestoTurno) {
		em.persist(puestoTurno);
	}
	
	public void delete(FisPuestoTurno puestoTurno) {
		em.remove(em.merge(puestoTurno));
	}
	
	public void update(FisPuestoTurno puestoTurno){
		em.merge(puestoTurno);	
	}
	
	
	public void eliminarPuestoTurno(int idPuestoTurno) {
	 em.createNativeQuery("delete from bivi.fis_puesto_turno where id_puesto_turno = "+idPuestoTurno+" ").executeUpdate();	
	}
	
	@SuppressWarnings("unchecked")
	public List<FisPuestoTurno> findAll() {
		Query q = em.createQuery("select c from FisPuestoTurno c ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisPuestoTurno ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public FisPuestoTurno findOne(Integer idPuestoTurno){
		Query q = em.createQuery("select c from FisPuestoTurno c where c.idPuestoTurno = " + idPuestoTurno);
		return (FisPuestoTurno) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")  
	public List<FisPuestoTurno> buscaPuestoTurno(FisTurno t) {
		
		List <FisPuestoTurno> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select pt.id_puesto_turno, p.nombre_puesto, tu.nombre from bivi.fis_puesto_turno as pt "+
				"inner join bivi.adm_puestos as p on p.id_puesto = pt.id_puesto "+
				"inner join bivi.fis_turno as tu on tu.id_turno = pt.id_turno "+
				"where tu.id_turno = "+t.getIdTurno()+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			FisPuestoTurno e = new FisPuestoTurno();
			e.setIdPuestoTurno(Integer.parseInt(row[0].toString()));
			
			AdmPuesto a = new AdmPuesto();
			a.setNombrePuesto(row[1].toString());
			
			FisTurno c = new FisTurno();
			c.setNombre(row[2].toString());
			
			e.setFisTurno(c);
			
			
			e.setIdPuesto(a);
			
			
			lista.add(e);
			
		}
		return lista;	
		
	}

}

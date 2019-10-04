package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.FisTurnoDia;
import com.bivi.modelos.AdmDetalleCatalogo;
import com.bivi.modelos.FisTurno;



@Stateless
public class ServicioFisTurnoDia {
	@PersistenceContext
	private EntityManager em;

	public void create(FisTurnoDia fisturnodia) {
		em.persist(fisturnodia);
	}

	public void delete(FisTurnoDia fisturnodia) {
		em.remove(em.merge(fisturnodia));
	}

	public void update(FisTurnoDia fisturnodia) {
		em.merge(fisturnodia);
	}

	
	@SuppressWarnings("unchecked")
	public List<FisTurnoDia> findAll() { // Busca todo de la tabla FisTurnoDia
		Query q = em.createQuery("select b from FisTurnoDia b order by b.idTurnoDia ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla FisTurnoDia y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisTurnoDia ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from FisTurnoDia");
		return (Integer) q.getSingleResult();
	}
	
	public FisTurnoDia findOne(Integer idTurnoDia){
		Query q = em.createQuery("select a from FisTurnoDia a where a.idTurnoDia = " + idTurnoDia);
		return (FisTurnoDia) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked") //Busca los turnos asignados al dia
	public List<FisTurnoDia> buscaTurno(FisTurno turno) {
		Query q = em.createQuery("select f from FisTurnoDia f  where f.idTurno = "+turno.getIdTurno()+" ");
		return q.getResultList();
	}
	
	public void eliminarTurnoDia(int idTurnoDia) {
	 em.createNativeQuery("delete from bivi.fis_turno_dia where id_turno_dia = "+idTurnoDia+" ").executeUpdate();	
	}
	
	@SuppressWarnings("unchecked")  
	public List<FisTurnoDia> buscaTurnoDia(FisTurno t) {
		
		List <FisTurnoDia> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select td.id_turno_dia, dt.descripcion, tu.nombre from bivi.fis_turno_dia as td "+
				"inner join bivi.adm_detalle_catalogo as dt on dt.id_detalle_catalogo = td.id_catalogo_dia "+
				"inner join bivi.fis_turno as tu on tu.id_turno = td.id_turno "+
				"where tu.id_turno = "+t.getIdTurno()+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			FisTurnoDia e = new FisTurnoDia();
			e.setIdTurnoDia(Integer.parseInt(row[0].toString()));
			
			AdmDetalleCatalogo a = new AdmDetalleCatalogo();
			a.setDescripcion(row[1].toString());
			
			FisTurno c = new FisTurno();
			c.setNombre(row[2].toString());
			
			e.setFisTurno(c);
			
			
			e.setIdDia(a);
			
			
			lista.add(e);
			
		}
		return lista;	
		
	}
	
}

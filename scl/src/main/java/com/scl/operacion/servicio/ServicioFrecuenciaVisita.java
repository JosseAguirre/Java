package com.scl.operacion.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.operacion.modelo.OpeFrecuenciaVisita;
import com.scl.operacion.modelo.OpeHojaAlistamiento;






@Stateless
public class ServicioFrecuenciaVisita {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeFrecuenciaVisita frecVisita) {
		em.persist(frecVisita);
	}
	
	public void delete(OpeFrecuenciaVisita frecVisita) {
		em.remove(em.merge(frecVisita));
	}
	
	public void update(OpeFrecuenciaVisita frecVisita){
	
		em.merge(frecVisita);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<OpeFrecuenciaVisita> findAll() {
		Query q = em.createQuery("select a from OpeFrecuenciaVisita a ");
		return q.getResultList();
	}
	
	
	public void elimina(int idFrecuenciaVisita ) {
		System.out.println("ssfs");
		 em.createNativeQuery("delete from java.ope_frecuencia_visita where id_frecuencia_visita = "+idFrecuenciaVisita+" ").executeUpdate();
		
	}
	
	
//	@SuppressWarnings("unchecked")
//	public List<OpeFrecuenciaVisita> buscaFrecuenciaAgencia(int idHojaAlistamiento) {
//		Query q = em.createQuery("select a from OpeFrecuenciaVisita a  where  a.idHojaAlistamiento = "+idHojaAlistamiento+"");
//		return q.getResultList();
//	}
	
	@SuppressWarnings("unchecked")
	public List<OpeFrecuenciaVisita> buscaFrecuenciaAgencia(int idHojaAlistamiento) {
		List <OpeFrecuenciaVisita> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_frecuencia_visita, nombre_dia, hora_desde, hora_hasta from java.ope_frecuencia_visita   where  id_hoja_alistamiento = "+idHojaAlistamiento+"");
	
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeFrecuenciaVisita e = new OpeFrecuenciaVisita();
			e.setIdFrecuenciaVisita(Integer.parseInt(row[0].toString()));
			
			
			e.setNombreDia(row[1].toString());
			e.setHoraDesde(row[2].toString());
			e.setHoraHasta(row[3].toString());
			
		
			lista.add(e);
			
		}
		return lista;
		
		
		
	}
	
	 /*
	@SuppressWarnings("unchecked")
	public List<OpeFrecuenciaVisita> test(int idHojaAlistamiento) {
		Query q = em.createNamedQuery("SELECT fv.hora_desde, fv.hora_hasta, fv.nombre_dia FROM java.ope_frecuencia_visita as fv inner join java.ope_hoja_alistamiento as ha on fv.id_hoja_alistamiento = ha.id_hoja_alistamiento where fv.id_hoja_alistamiento = "+idHojaAlistamiento+"");
		//List<Object[]> authors = q.getResultList();
		
		for (OpeFrecuenciaVisita f : authors) {
		    OpeFrecuenciaVisita frec = new OpeFrecuenciaVisita();
		    frec.setHoraDesde(a.);
		}
		
		
		return q.getResultList();
	}*/
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeFrecuenciaVisita ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeFrecuenciaVisita findOne(Integer idfrecuencia){
		Query q = em.createQuery("select a from OpeFrecuenciaVisita a where a.idFrecuenciaVisita = " + idfrecuencia);
		return (OpeFrecuenciaVisita) q.getSingleResult();
	}

	



	


}

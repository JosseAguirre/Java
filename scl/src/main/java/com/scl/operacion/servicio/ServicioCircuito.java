package com.scl.operacion.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioCircuito {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeCircuito circuito) {
		em.persist(circuito);
	}
	
	public void delete(OpeCircuito circuito) {
		em.remove(em.merge(circuito));
	}
	
	public void update(OpeCircuito circuito){
	
		em.merge(circuito);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeCircuito> findAll() {
		Query q = em.createQuery("select a from OpeCircuito a ");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeCircuito> buscaCircuitoDiaCiudad(int idCiudad, String dia) {
	
		
		Query q = em.createQuery("select a from OpeCircuito a where a.idCiudadDc = "+idCiudad+" and a.diaAsociado = '"+dia+"'");	
		return q.getResultList();
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<OpeCircuito> buscaCircuitoPorEquipo(int idMovil, Date fecha) {
	
		List <OpeCircuito> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select distinct ci.id_circuito, ci.nombre FROM java.ope_rutero as ru "+
"inner join java.ope_equipo_movil as em on em.id_equipo_movil = ru.id_equipo_movil "+
"inner join java.ope_circuito as ci on ci.id_circuito = ru.id_circuito "+
"where ru.id_equipo_movil = "+idMovil+" and ru.fecha_operacion = '"+fecha+"'");	
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeCircuito e = new OpeCircuito();
			e.setIdCircuito(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
		
		
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeCircuito ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeCircuito findOne(Integer idCircuito){
		Query q = em.createQuery("select a from OpeCircuito a where a.idCircuito = " + idCircuito);
		return (OpeCircuito) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")//
	public List<OpeCircuito> buscaCircuito(int idCiudad,String dia, String fecha) {
		List <OpeCircuito> lista = new ArrayList<>();
		
		Query q = em.createNativeQuery("select a.id_circuito, a.nombre from java.ope_circuito a where a.id_Ciudad_Dc = "+idCiudad+" and a.dia_asociado = '"+dia+"' and a.id_Circuito not in "+
"(select c.id_Circuito from java.ope_tripulacion c where c.fecha_Operacion ='"+fecha+"') ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeCircuito e = new OpeCircuito();
			e.setIdCircuito(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
	}

	



}


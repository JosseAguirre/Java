package com.scl.operacion.servicio;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.*;

@Stateless
public class ServicioTripulacion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeTripulacion tripulacion) {
		em.persist(tripulacion);
	}
	
	public void delete(OpeTripulacion tripulacion) {
		em.remove(em.merge(tripulacion));
	}
	
	public void update(OpeTripulacion tripulacion){
	
		em.merge(tripulacion);
		
		
	}
	
	public void eliminarTripulacion(OpeTripulacion tripulacion){
		
		 em.createNativeQuery("delete from java.ope_tripulacion where id_tripulacion = "+tripulacion.getIdTripulacion()+"").executeUpdate();
			
	}
	
	
	public void eliminarDetalleTripulacion(OpeTripulacion tripulacion){
		
		 em.createNativeQuery("delete from java.ope_detalle_tripulacion where id_tripulacion = "+tripulacion.getIdTripulacion()+"").executeUpdate();
			
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeTripulacion> findAll() {
		Query q = em.createQuery("select a from OpeTripulacion a");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeTripulacion> buscaTripulacionFecha(int idCiudad, String fecha) {
		List <OpeTripulacion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select tr.id_tripulacion, tr.fecha_operacion, ve.descripcion, ci.nombre, mo.nombre as equipo  from java.ope_tripulacion as tr "+
"inner join java.ope_vehiculo as ve on ve.id_vehiculo = tr.id_vehiculo "+
"inner join java.ope_circuito as ci on ci.id_circuito = tr.id_circuito  "+
"inner join java.ope_equipo_movil as mo on mo.id_equipo_movil = tr.id_equipo_movil "+
"where tr.fecha_operacion = '"+fecha+"' and mo.id_Equipo_Movil in  "+
"(select b.id_Equipo_Movil from java.ope_equipo_movil b where b.id_ciudad_dc = "+idCiudad+")");
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeTripulacion e = new OpeTripulacion();
			e.setIdTripulacion(Integer.parseInt(row[0].toString()));
			e.setFechaOperacion((Date)row[1]);
			
			OpeVehiculo v = new OpeVehiculo ();
			v.setDescripcion(row[2].toString());
			
			OpeCircuito c = new OpeCircuito();
			c.setNombre(row[3].toString());
			
			OpeEquipoMovil m = new OpeEquipoMovil ();
			m.setNombre(row[4].toString());
			
			e.setIdVehiculo(v);
			e.setIdCircuito(c);
			e.setIdEquipoMovil(m);
			
			lista.add(e);
			
		}
		return lista;
		
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeTripulacion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeTripulacion findOne(Integer idTripulacion){
		Query q = em.createQuery("select a from OpeTripulacion a where a.idTripulacion = " + idTripulacion);
		return (OpeTripulacion) q.getSingleResult();
	}

		

	



}


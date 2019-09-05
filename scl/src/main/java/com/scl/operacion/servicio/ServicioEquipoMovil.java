package com.scl.operacion.servicio;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioEquipoMovil {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeEquipoMovil equipoMovil) {
		em.persist(equipoMovil);
	}
	
	public void delete(OpeEquipoMovil equipoMovil) {
		em.remove(em.merge(equipoMovil));
	}
	
	public void update(OpeEquipoMovil equipoMovil){
	
		em.merge(equipoMovil);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeEquipoMovil> findAll() {
		Query q = em.createQuery("select a from OpeEquipoMovil a ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeEquipoMovil ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeEquipoMovil findOne(Integer idEquipoMovil){
		Query q = em.createQuery("select a from OpeEquipoMovil a where a.idEquipoMovil = " + idEquipoMovil);
		return (OpeEquipoMovil) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<OpeEquipoMovil> buscaEquipoMovil(int idCiudad, String fecha) {
		
		List <OpeEquipoMovil> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select a.id_equipo_movil, a.nombre from java.ope_equipo_movil a where a.id_ciudad_dc = "+idCiudad+" and a.id_Equipo_Movil not in "+ 
"(select c.id_equipo_movil from java.ope_tripulacion c where c.fecha_operacion ='"+fecha+"')");
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeEquipoMovil e = new OpeEquipoMovil();
			e.setIdEquipoMovil(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
		
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<OpeEquipoMovil> buscaEquipoMovil2(int idCiudad, String fecha) { // metodo usado en reasignacion
		
		List <OpeEquipoMovil> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT  distinct mov.id_equipo_movil, mov.nombre FROM java.ope_tripulacion as tri  "+
	"inner join java.ope_equipo_movil as mov on mov.id_equipo_movil = tri.id_equipo_movil "+
	"where tri.fecha_operacion = '"+fecha+"' and  mov.id_ciudad_dc = "+idCiudad+""); 
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeEquipoMovil e = new OpeEquipoMovil();
			e.setIdEquipoMovil(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
		
		
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<OpeEquipoMovil> buscaEquipoMovilOperativos(int idCiudad, Date fecha) {
		
		List <OpeEquipoMovil> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select a.id_equipo_movil, a.nombre from java.ope_equipo_movil a where a.id_ciudad_dc = "+idCiudad+" and id_equipo_movil in ( "+
"select distinct id_equipo_movil from java.ope_rutero where fecha_operacion = '"+fecha+"')");
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeEquipoMovil e = new OpeEquipoMovil();
			e.setIdEquipoMovil(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
		
		
		
	}
	

	



}


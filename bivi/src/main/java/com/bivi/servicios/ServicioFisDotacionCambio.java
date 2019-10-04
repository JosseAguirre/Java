package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.FisDotacionCambio;
import com.bivi.modelos.FisCambioTurno;
import com.bivi.modelos.FisDotacion;
import com.bivi.modelos.AdmPuesto;
import com.bivi.modelos.AdmUsuario;


@Stateless
public class ServicioFisDotacionCambio {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisDotacionCambio dotacionCambio) {
		em.persist(dotacionCambio);
	}
	
	public void delete(FisDotacionCambio dotacionCambio) {
		em.remove(em.merge(dotacionCambio));
	}
	
	public void update(FisDotacionCambio dotacionCambio){
		em.merge(dotacionCambio);	
	}
	
	
	public void eliminarDotacionCambio(int idDotacionCambio) {
	 em.createNativeQuery("delete from bivi.fis_dotacion_cambio where id_dotacion_cambio = "+idDotacionCambio+" ").executeUpdate();	
	}
	
	@SuppressWarnings("unchecked")
	public List<FisDotacionCambio> findAll() {
		Query q = em.createQuery("select c from FisDotacionCambio c ");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisDotacionCambio ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public FisDotacionCambio findOne(Integer idDotacionCambio){
		Query q = em.createQuery("select c from FisDotacionCambio c where c.idDotacionCambio = " + idDotacionCambio);
		return (FisDotacionCambio) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")  
	public List<FisDotacionCambio> buscaDotacionCambio(FisDotacion d) {
		
		List <FisDotacionCambio> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select dt.id_dotacion_cambio, p.nombre_puesto, us.usuario, d.descripcion from bivi.fis_dotacion_cambio as dt"+
				"inner join bivi.fis_cambio_turno as ct on ct.id_cambio_turno = dt.id_cambio_turno"+
				"inner join bivi.adm_puestos as p on p.id_puesto = ct.id_puesto"+
				"inner join bivi.adm_usuario as us on us.id_usuario = ct.id_usuario_recibe"+
				"inner join bivi.fis_dotacion as d on d.id_dotacion = dt.id_dotacion"+
				"where d.id_dotacion = "+d.getIdDotacion()+" and ct.fecha_cambio = current_date");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			FisDotacionCambio dc = new FisDotacionCambio();
			dc.setIdDotacionCambio(Integer.parseInt(row[0].toString()));
			
			AdmPuesto p = new AdmPuesto();
			p.setNombrePuesto(row[1].toString());
			
			AdmUsuario us = new AdmUsuario();
			us.setUsuario(row[2].toString());
			
			FisCambioTurno ca = new FisCambioTurno();
			ca.setIdPuesto(p);
			ca.setIdUsuarioRecibe(us);
			
			FisDotacion dota = new FisDotacion();
			dota.setDescripcion(row[3].toString());
			
			dc.setIdCambioTurno(ca);			
			
			dc.setIdDotacion(dota);
			
			
			lista.add(dc);
			
		}
		return lista;	
		
	}

}

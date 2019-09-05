package com.scl.cpc.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.cpc.modelo.CpcDetalleDistribucion;
import com.scl.cpc.modelo.CpcDetalleDistribucion;

@Stateless
public class ServicioDetalleDistribucion {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcDetalleDistribucion detalleDistribucion) {
		em.persist(detalleDistribucion);
	}
	
	public void delete(CpcDetalleDistribucion detalleDistribucion) {
		em.remove(em.merge(detalleDistribucion));
	}
	
	public void update(CpcDetalleDistribucion detalleDistribucion){
	
		em.merge(detalleDistribucion);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<CpcDetalleDistribucion> findAll() {
		Query q = em.createQuery("select a from CpcDetalleDistribucion a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcDetalleDistribucion> buscaDetalleDistribucion( String remesa) {
		List <CpcDetalleDistribucion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select * from java.cpc_detalle_distribucion  where id_distribucion_caja in ( "+
       "select id_distribucion_caja from java.cpc_distribucion_caja as dc "+
       "inner join java.ope_transaccion as tr on tr.id_transaccion = dc.id_transaccion "+
       "where tr.numero_recibo = '"+remesa+"' )");
		
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcDetalleDistribucion c = new CpcDetalleDistribucion();
			
			
			lista.add(c);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcDetalleDistribucion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	@SuppressWarnings("null")
	public Integer findOne(Integer iddetalleTRansaccion){
	
		Integer codigo = 0;
		Query q = em.createNativeQuery("select id_detalle_distribucion from java.cpc_detalle_distribucion a where a.id_detalle_transaccion = " + iddetalleTRansaccion);
		codigo = (Integer) q.getSingleResult();
		return codigo;
	}

	

}

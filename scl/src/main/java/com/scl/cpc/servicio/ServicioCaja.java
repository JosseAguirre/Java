package com.scl.cpc.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.cpc.modelo.*;
@Stateless
public class ServicioCaja {

	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcCaja caja) {
		em.persist(caja);
	}
	
	public void delete(CpcCaja caja) {
		em.remove(em.merge(caja));
	}
	
	public void update(CpcCaja caja){
	
		em.merge(caja);
		
		
	}
	
	public void eliminaCaja(CpcCaja caja) {
		 em.createNativeQuery("delete from java.cpc_caja where id_caja = "+caja.getIdCaja()+"").executeUpdate();
		
	
	}
	

	
	@SuppressWarnings("unchecked")
	public List<CpcCaja> findAll() {
		Query q = em.createQuery("select a from CpcCaja a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcCaja> buscaCajas( Date fecha) {
		List <CpcCaja> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_caja, em.nombres, em.apellidos, ca.cubiculo, ca.tiempo from java.cpc_caja as ca "+ 
  "inner join java.adm_empleado as em on em.id_empleado = ca.id_empleado where fecha = '"+fecha+"'");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcCaja c = new CpcCaja();
			c.setIdCaja(Integer.parseInt(row[0].toString()));
			
			AdmEmpleado em = new AdmEmpleado();
			em.setNombres(row[1].toString());
			em.setApellidos(row[2].toString());
			c.setIdEmpleado(em);
			
			c.setCubiculo(Integer.parseInt(row[3].toString()));
			c.setTiempo(Integer.parseInt(row[4].toString()));
			
			lista.add(c);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcCaja ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public CpcCaja findOne(Integer idcaja){
		Query q = em.createQuery("select a from CpcCaja a where a.idTipoTarifa = " + idcaja);
		return (CpcCaja) q.getSingleResult();
	}

	


	
}

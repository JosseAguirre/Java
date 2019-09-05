package com.scl.cpc.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.cpc.modelo.CpcDetalleEspecie;

@Stateless
public class ServicioDetalleEspecie {
	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcDetalleEspecie detalleEspecie) {
		em.persist(detalleEspecie);
	}
	
	public void delete(CpcDetalleEspecie detalleEspecie) {
		em.remove(em.merge(detalleEspecie));
	}
	
	public void update(CpcDetalleEspecie detalleEspecie){
	
		em.merge(detalleEspecie);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<CpcDetalleEspecie> findAll() {
		Query q = em.createQuery("select a from CpcDetalleEspecie a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcDetalleEspecie> buscaDetalleEspecie( Date fecha) {
		List <CpcDetalleEspecie> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_detalleEspecie, em.nombres, em.apellidos, ca.cubiculo, ca.tiempo from java.cpc_detalleEspecie as ca "+ 
  "inner join java.adm_empleado as em on em.id_empleado = ca.id_empleado where fecha = '"+fecha+"'");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcDetalleEspecie c = new CpcDetalleEspecie();
			c.setIdDetalleEspecie(Integer.parseInt(row[0].toString()));
			
			
			
			lista.add(c);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcDetalleEspecie ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public Float findOne(String remesa){
		Query q = em.createNativeQuery("select case when sum(case when id_detalle_distribucion in "+
				"( select  dd.id_detalle_distribucion  from  java.cpc_detalle_distribucion  as dd "+
						"inner join java.ope_detalle_transaccion as dtg on dtg.id_detalle_transaccion = dd. id_detalle_transaccion  "+
						"inner join java.ope_transaccion  as tg on tg.id_transaccion = dtg.id_transaccion  "+
						"where tg.numero_recibo = '"+remesa+"')  then subtotal else 0 end) is null then 0.00  else "+
						"sum(case when id_detalle_distribucion in "+
						"( select  dd.id_detalle_distribucion  from  java.cpc_detalle_distribucion  as dd "+
						"inner join java.ope_detalle_transaccion as dtg on dtg.id_detalle_transaccion = dd. id_detalle_transaccion "+
						"inner join java.ope_transaccion  as tg on tg.id_transaccion = dtg.id_transaccion "+
						"where tg.numero_recibo = '"+remesa+"')  then subtotal else 0 end) end as suma  from  java.cpc_detalle_especie ");
		return (Float) q.getSingleResult();
	}


}

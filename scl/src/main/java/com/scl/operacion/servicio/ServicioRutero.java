package com.scl.operacion.servicio;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmDetalleCatalogo;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioRutero {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeRutero rutero) {
		em.persist(rutero);
	}
	
	public void create2(Object[] item) {
		
		Query q = em.createNativeQuery("insert into ");
	}
	
	public void delete(OpeRutero rutero) {
		em.remove(em.merge(rutero));
	}
	
	public void update(OpeRutero rutero){
	
		em.merge(rutero);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeRutero> findAll() {
		Query q = em.createQuery("select a from OpeRutero a");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public  List<OpeRutero> buscaTransaccionReasignar(String id){
		Query q = em.createQuery("select a from OpeRutero a where a.idRutero in ("+id+")");
		return q.getResultList();
		
		
	}
	
	public void eliminaCircuitoRutero(int idCircuito, Date fecha){
		
		 em.createNativeQuery("delete from java.ope_rutero  where id_circuito = "+idCircuito+" and fecha_operacion = '"+fecha+"' ").executeUpdate();
		
		
		
	}
	
	
	public  void actualizaParada(String id){
		 em.createNativeQuery("update java.ope_rutero set sysdelete = 1   where id_rutero in ("+id+")").executeUpdate();
		
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public  List<OpeRutero> buscaAsignaciones( Date fecha, int idCiudad) {
		List <OpeRutero> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select ru.id_rutero, ru.id_agencia_origen, ru.id_equipo_movil, ru.id_circuito from java.ope_rutero as ru  "+ 
"inner join java.adm_agencia as ag on ag.id_agencia = ru.id_agencia_origen " +
"where fecha_operacion = '"+fecha+"' and ag.id_ciudad_dc = "+idCiudad+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
				
			OpeRutero r = new OpeRutero();
			r.setIdRutero(Integer.parseInt(row[0].toString()));
			r.setIdAgenciaOrigen(Integer.parseInt(row[1].toString()));
			r.setIdEquipoMovil(Integer.parseInt(row[2].toString()));
			r.setIdCircuito(Integer.parseInt(row[3].toString()));
			
			lista.add(r);
			
		}
		return lista;
	
	}
	

	public void eliminaParada( String idrutero) {
	em.createNativeQuery("update java.ope_rutero set sysdelete = 1  where id_rutero in  ( "+idrutero+")").executeUpdate();
	
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object> buscaTransaccionMonitoreo(String circuitos, Date fecha) {
		Query q = em.createNativeQuery(
				"select "+
"0 as rutero,  "+						
"'' as desde,  "+
"'' as hasta, "+
"t.numero_recibo as recibo, "+
"ao.nombre as Origen, "+
"ad.nombre as Destino, "+
"t.total_efectivo as totalefectivo, "+
"t.total_cheque as cheque, "+
"t.total_transaccion as totaltransaccion, "+
"t.total_paquetes as paquetes, "+
"t.estado_transaccion as estadotransaccion "+
"from java.ope_transaccion as t "+
"inner join java.ope_rutero as r on t.id_rutero = r.id_rutero "+
"inner join java.adm_agencia ao on ao.id_agencia = t.id_agencia_origen "+
"inner join java.adm_agencia ad on ad.id_agencia = t.id_agencia_destino "+
"where r.estado_cliente = 1 "+
"and t.fecha_operacion = '"+fecha+"' "+
"and t.id_transaccion in "+
"(select max(tr.id_transaccion) from java.ope_transaccion as tr "+
"where tr.numero_recibo =t.numero_recibo and tr.estado_transaccion in (10,11,12,15,20,19) )"+

"union "+

"select "+
"ru.id_rutero as rutero, "+
"ru.hora_desde as desde, "+
"ru.hora_hasta as hasta, "+
"'' as recibo, "+
"ao.nombre as Origen,  "+
"ad.nombre as Destino, "+
"0.00 as totalefectivo,"+ 
"0.00 as cheque, "+
"0.00 as totaltransaccion, "+
"0 as paquetes, "+
"0 as estadotransaccion "+
"from java.ope_rutero as ru "+ 
"inner join java.adm_agencia ao on ao.id_agencia = ru.id_agencia_origen "+
"inner join java.adm_agencia ad on ad.id_agencia = ru.id_agencia_destino "+
"where ru.estado_cliente = 0 "+
"and ru.fecha_operacion = '"+fecha+"'  "+
"and ru.sysdelete = 0  and ru.id_circuito IN( "+circuitos+") ");
		
		
		return q.getResultList();
	}
	
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeRutero ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	
	
	public OpeRutero buscaIdRutero(int idAgenciaOrigen, Date fecha, String desde, String hasta){
		OpeRutero e = new OpeRutero();
		Query q = em.createNativeQuery("SELECT id_rutero, id_equipo_movil, id_circuito FROM java.ope_rutero where id_agencia_origen = "+idAgenciaOrigen+" and hora_desde = '"+desde+"' and hora_hasta = '"+hasta+"' and fecha_operacion = '"+fecha+"'");

		
		
			
			@SuppressWarnings("unchecked")
			List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
			
			
			
			
			e.setIdRutero(Integer.parseInt(row[0].toString()));
			e.setIdEquipoMovil(Integer.parseInt(row[1].toString()));
			e.setIdCircuito(Integer.parseInt(row[2].toString()));
			
			}

			
			
		
		
		
		return e;
		
	}
	
	public OpeRutero buscaIdRutero2(int idAgenciaOrigen, Date fecha){ 
		OpeRutero r = new OpeRutero();
		Query q = em.createNativeQuery("SELECT id_rutero FROM java.ope_rutero where id_agencia_origen = "+idAgenciaOrigen+" and fecha_operacion = '"+fecha+"'");
		
		
		Object obj =  q.getSingleResult();
		r.setIdRutero(Integer.parseInt(obj.toString()));
		
		return r;
	}
	
	
	
	
	public OpeRutero findOne(Integer idrutero){
		Query q = em.createQuery("select a from OpeRutero a where a.idrutero = " + idrutero);
		return (OpeRutero) q.getSingleResult();
	}

	
	



}


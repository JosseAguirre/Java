package com.scl.operacion.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmCliente;
import com.scl.operacion.modelo.OpeCircuito;
import com.scl.operacion.modelo.OpeFrecuenciaVisita;
import com.scl.operacion.modelo.OpeHojaAlistamiento;



@Stateless
public class ServicioHojaAlistamiento {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeHojaAlistamiento hojaAlistamiento) {
		em.persist(hojaAlistamiento);
	}
	
	public void delete(OpeHojaAlistamiento hojaAlistamiento) {
		em.remove(em.merge(hojaAlistamiento));
	}
	
	public void update(OpeHojaAlistamiento hojaAlistamiento){
	
		em.merge(hojaAlistamiento);
		
		
	}
	

	public void updateAsigna(int idCircuito, String idfrecuencia) {
	em.createNativeQuery("update java.ope_frecuencia_visita set id_circuito = "+idCircuito+" where id_frecuencia_visita = "+idfrecuencia+"").executeUpdate();
	
		
	}
	
	public void updateElimina(String idHojaAlistamiento) {
		em.createNativeQuery("update java.ope_frecuencia_visita set id_circuito = null where id_frecuencia_visita = "+idHojaAlistamiento+"").executeUpdate();
		
			
		}
	

	
	@SuppressWarnings("unchecked")
	public List<OpeHojaAlistamiento> findAll() {
		Query q = em.createQuery("select a from OpeHojaAlistamiento a where", OpeHojaAlistamiento.class);
		//q.setHint("javax.persistence.loadgraph",
		//em.getEntityGraph("HojaAlistamiento-Frecuencia")); 
		 
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object> buscaHojasNA(int idCiudad, String dia) {
		Query q = em.createNativeQuery("select fv.id_frecuencia_visita, fv.nombre_dia, fv.hora_desde, fv.hora_hasta,  ao.nombre as origen, ad.nombre as destino "+
"FROM java.ope_frecuencia_visita as fv "+
"inner join java.ope_hoja_alistamiento as ha on ha.id_hoja_alistamiento = fv.id_hoja_alistamiento "+
"inner join java.adm_agencia as ao on ao.id_agencia = ha.id_agencia_origen  "+
"inner join java.adm_agencia as ad on ad.id_agencia = ha.id_agencia_destino  "+
"where fv.nombre_dia = '"+dia+"' and  fv.id_circuito isnull and ha.id_ciudad = "+idCiudad+"");
	
		return q.getResultList();
	}
	
	
/*	@SuppressWarnings("unchecked") // metodo del delegado
	public List<Object> buscaPlanificada(int idCiudad, String dia, int idAgencia) {
		Query q = em.createNativeQuery("select fv.id_frecuencia_visita, fv.nombre_dia, fv.hora_desde, fv.hora_hasta,  ao.nombre as origen, ad.nombre as destino, ao.id_agencia as idAgenciaOrigen, ad.id_agencia as idAgenciaDestino  "+
"FROM java.ope_frecuencia_visita as fv "+
"inner join java.ope_hoja_alistamiento as ha on ha.id_hoja_alistamiento = fv.id_hoja_alistamiento "+
"inner join java.adm_agencia as ao on ao.id_agencia = ha.id_agencia_origen  "+
"inner join java.adm_agencia as ad on ad.id_agencia = ha.id_agencia_destino  "+
"where fv.nombre_dia = '"+dia+"' and  ao.id_agencia = "+idAgencia+" and ha.id_ciudad = "+idCiudad+"");
	
		return q.getResultList();
	}*/
	
	@SuppressWarnings("unchecked") // metodo del delegado
	public List<OpeFrecuenciaVisita> buscaPlanificada(int idCiudad, String dia, int idAgencia) {
		
		List <OpeFrecuenciaVisita> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select fv.id_frecuencia_visita, fv.nombre_dia, fv.hora_desde, fv.hora_hasta,  ao.nombre as origen, ad.nombre as destino, ao.id_agencia as idAgenciaOrigen, ad.id_agencia as idAgenciaDestino, co.id_cliente    "+
"FROM java.ope_frecuencia_visita as fv "+
"inner join java.ope_hoja_alistamiento as ha on ha.id_hoja_alistamiento = fv.id_hoja_alistamiento "+
"inner join java.adm_agencia as ao on ao.id_agencia = ha.id_agencia_origen  "+
"inner join java.adm_agencia as ad on ad.id_agencia = ha.id_agencia_destino  "+
"inner join java.adm_cliente as co on co.id_cliente = ha.id_cliente_origen "+
"where fv.nombre_dia = '"+dia+"' and  ao.id_agencia = "+idAgencia+" and ha.id_ciudad = "+idCiudad+"");
	
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeHojaAlistamiento e = new OpeHojaAlistamiento();
			
			OpeFrecuenciaVisita f = new OpeFrecuenciaVisita();
			f.setIdFrecuenciaVisita(Integer.parseInt(row[0].toString()));
			f.setNombreDia(row[1].toString());
			f.setHoraDesde(row[2].toString());
			f.setHoraHasta(row[3].toString());
			
			AdmAgencia ao = new AdmAgencia ();
			ao.setNombre(row[4].toString());
			ao.setIdAgencia(Integer.parseInt(row[6].toString()));
			
			AdmAgencia ad = new AdmAgencia ();
			ad.setNombre(row[5].toString());
			ad.setIdAgencia(Integer.parseInt(row[7].toString()));
			
			AdmCliente co = new AdmCliente();
			co.setIdCliente(Integer.parseInt(row[8].toString()));
			
			
			e.setIdClienteOrigen(co);
			
			
			e.setIdAgenciaOrigen(ao);
			e.setIdAgenciaDestino(ad);
			
			f.setIdHojaAlistamiento(e);
			
			


			
			lista.add(f);
			
		}
		return lista;
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public  List<Object> buscaHojasSA( int ciudad, int idCircuito, String dia) { 
		
		
	
		Query q = em.createNativeQuery("select fv.id_frecuencia_visita, fv.nombre_dia, fv.hora_desde, fv.hora_hasta,  ao.nombre as origen, ad.nombre as destino "+
"FROM java.ope_frecuencia_visita as fv "+
"inner join java.ope_hoja_alistamiento as ha on ha.id_hoja_alistamiento = fv.id_hoja_alistamiento "+
"inner join java.adm_agencia as ao on ao.id_agencia = ha.id_agencia_origen  "+
"inner join java.adm_agencia as ad on ad.id_agencia = ha.id_agencia_destino  "+
"where fv.nombre_dia = '"+dia+"' and  fv.id_circuito ="+idCircuito+" and ha.id_ciudad = "+ciudad+""); 
		
		return q.getResultList();
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Object[]> buscaHojasCiurcuito( int idCircuito, String dia, int ciudad) { 
	
		Query q = em.createNativeQuery("select fv.hora_desde, fv.hora_hasta, ha.id_cliente_origen as clienteoriegen,  ha.id_agencia_origen as agenciaorigen, "+
"ha.id_cliente_destino as clientedestino, ha.id_agencia_destino as destino "+
"FROM java.ope_frecuencia_visita as fv "+
"inner join java.ope_hoja_alistamiento as ha on ha.id_hoja_alistamiento = fv.id_hoja_alistamiento "+
"where fv.nombre_dia = '"+dia+"' and  fv.id_circuito ="+idCircuito+" and ha.id_ciudad = "+ciudad+""); 
		return q.getResultList();
	}
	
	
//	@SuppressWarnings("unchecked")
//	public List<OpeHojaAlistamiento> buscaHojaCliente(int idCliente, int ciudad) { /////
//		
//		
//		Query q = em.createQuery("SELECT a FROM OpeHojaAlistamiento a where a.idClienteOrigen = "+idCliente+"  and  a.idAgenciaOrigen in (select b.idAgencia from AdmAgencia b where b.idCiudadDc = "+ciudad+")");
//		
//		 
//		return q.getResultList();
//		
//		
//	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<OpeHojaAlistamiento> buscaHojaCliente(int idCliente, int ciudad) { /////
		
		List <OpeHojaAlistamiento> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select ha.id_hoja_alistamiento, co.razon_social as clienteorigen, ao.nombre as agenciaorigen, "+ 
"cd.razon_social as clientedestino, ad.nombre as agenciadestino "+  
"FROM java.ope_hoja_alistamiento as ha "+
"inner join java.adm_cliente as co on co.id_cliente = ha.id_cliente_origen "+
"inner join java.adm_cliente as cd on cd.id_cliente = ha.id_cliente_destino "+
"inner join java.adm_agencia as ao on ao.id_agencia = ha.id_agencia_origen "+
"inner join java.adm_agencia as ad on ad.id_agencia = ha.id_agencia_destino "+
"where ha.id_cliente_origen = "+idCliente+"  and  ha.id_agencia_origen in  "+
"(select id_agencia from java.adm_agencia  where id_ciudad = "+ciudad+" )");
		
		 
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeHojaAlistamiento e = new OpeHojaAlistamiento();
			e.setIdHojaAlistamiento(Integer.parseInt(row[0].toString()));
			
			AdmCliente c1 = new AdmCliente ();
			c1.setRazonSocial(row[1].toString());
			
			AdmAgencia a1 = new AdmAgencia(); 
			a1.setNombre(row[2].toString());
			
			AdmCliente c2 = new AdmCliente ();
			c2.setRazonSocial(row[3].toString());
			
			AdmAgencia a2 = new AdmAgencia(); 
			a2.setNombre(row[4].toString());
			
			e.setIdClienteOrigen(c1);
			e.setIdAgenciaOrigen(a1);
			e.setIdClienteDestino(c2);
			e.setIdAgenciaDestino(a2);

			
			lista.add(e);
			
		}
		return lista;
		
		
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeHojaAlistamiento ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeHojaAlistamiento findOne(Integer idhojaAlistamiento){
		Query q = em.createQuery("select a from OpeHojaAlistamiento a where a.idHojaAlistamiento = " + idhojaAlistamiento);
		return (OpeHojaAlistamiento) q.getSingleResult();
	}

	



}


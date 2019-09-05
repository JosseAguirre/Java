package com.scl.cpc.servicio;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmAgencia;
import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.cpc.modelo.*;
import com.scl.operacion.modelo.OpeTransaccion;
@Stateless
public class ServicioDistribucionCaja {

	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcDistribucionCaja distribucionCaja) {
		em.persist(distribucionCaja);
	}
	
	public void delete(CpcDistribucionCaja distribucionCaja) {
		em.remove(em.merge(distribucionCaja));
	}
	
	public void update(CpcDistribucionCaja distribucionCaja){
	
		em.merge(distribucionCaja);
		
		
	}
	
	public void actualizaInicioCaja(Date fecha, String remesa){
		em.createNativeQuery("update java.cpc_distribucion_caja set  fecha_inicio_caja = '"+fecha+"' "+
"where id_transaccion = (select dc.id_transaccion from java.cpc_distribucion_caja as dc "+
"inner join java.ope_transaccion as tg on tg.id_transaccion = dc.id_transaccion "+
"where tg.numero_recibo = '"+remesa+"')").executeUpdate();
		
		
	}
	
	public void actualizaCuadre(Date fecha, String remesa){
		em.createNativeQuery("update java.cpc_distribucion_caja  set estado= 2, id_empleado = 1, fecha_fin_caja = '"+fecha+"' "+
"where id_transaccion = (select dc.id_transaccion from java.cpc_distribucion_caja as dc "+
"inner join java.ope_transaccion as tg on tg.id_transaccion = dc.id_transaccion "+
"where tg.numero_recibo = '"+remesa+"')").executeUpdate();
		
		
	}
	
	public void quitarAsignacion(CpcDistribucionCaja distribucionCaja){
		 em.createNativeQuery("delete from java.cpc_distribucion_caja where id_distribucion_caja in ("+distribucionCaja.getIdDistribucionCaja()+") ").executeUpdate();
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	 public List<CpcDistribucionCaja> buscaTrAsignadaCajero (AdmUsuario us){ //busca las transaciones asignadas al cajero para proceso, segun el cajero logueado
		List <CpcDistribucionCaja> lista = new ArrayList<>();
		
		Query q = em.createNativeQuery("select dc.id_distribucion_caja, tr.numero_recibo, ag.nombre, tr.id_transaccion, tr.total_efectivo, tr.total_cheque, tr.total_transaccion, tr.total_paquetes FROM java.cpc_distribucion_caja as dc "+
"inner join java.ope_transaccion as tr on tr.id_transaccion = dc.id_transaccion "+
"inner join java.adm_agencia as ag on ag.id_agencia = tr.id_agencia_origen "+
"inner join java.adm_empleado as em on em.id_empleado = dc.id_empleado "+
"inner join java.adm_usuario as us on us.id_empleado = em.id_empleado "+
"where dc.estado = 0 and us.id_usuario = "+us.getIdUsuario()+" ");
		
		 List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				CpcDistribucionCaja dc = new CpcDistribucionCaja ();
				dc.setIdDistribucionCaja(Integer.parseInt(row[0].toString()));
				
				OpeTransaccion tr = new OpeTransaccion();
				tr.setNumeroRecibo(row[1].toString());
				
				
				AdmAgencia ag = new  AdmAgencia();
				ag.setNombre(row[2].toString());
				tr.setIdAgenciaOrigen(ag);
				
				tr.setIdTransaccion(Integer.parseInt(row[3].toString()));
				tr.setTotalEfectivo(Float.parseFloat(row[4].toString()));
				tr.setTotalCheque(Float.parseFloat(row[5].toString()));
				tr.setTotalTransaccion(Float.parseFloat(row[6].toString()));
				tr.setTotalPaquetes(Integer.parseInt(row[7].toString()));
				
				dc.setIdTransaccion(tr);
				
				
				
				lista.add(dc);
				
				
				
			}
		
		
		
		
		
		return lista;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	 public List<CpcDistribucionCaja> buscaTrSiAsignadas (Date fecha) throws ParseException{ // Metodo para buscar la  transaccion si asignadas a cajeros del cpc
		
		List <CpcDistribucionCaja> lista = new ArrayList<>();
		
		 Query q = em.createNativeQuery("select * from java.sp_cargar_asignacion_cajeros ('"+fecha+"') ");
		 
		 
		 List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				
			CpcDistribucionCaja dc = new CpcDistribucionCaja ();
			dc.setIdDistribucionCaja(Integer.parseInt(row[0].toString()));
			
			AdmAgencia ag = new AdmAgencia();
			ag.setNombre(row[1].toString());
			
			
			OpeTransaccion tr = new OpeTransaccion ();
			tr.setIdAgenciaOrigen(ag);
			tr.setNumeroRecibo(row[2].toString());
			tr.setTotalEfectivo(Float.parseFloat(row[3].toString()));
			tr.setTotalCheque(Float.parseFloat(row[4].toString()));
			tr.setTotalTransaccion(Float.parseFloat(row[5].toString()));
			//tr.setIdTransaccion(Integer.parseInt(row[9].toString()));
			
			dc.setIdTransaccion(tr);
			
			AdmEmpleado em = new AdmEmpleado();
			
			em.setNombres(row[6].toString());
			em.setApellidos(row[7].toString());
			dc.setIdEmpleado(em);
			
			//em.setIdEmpleado(Integer.parseInt(row[8].toString()));
			
			
				
			
			if(row[10]== null){
				
				dc.setFechaInicioCaja(null);
			}else {
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse((row[10].toString()));
		    Timestamp fechaInicio = new java.sql.Timestamp(parsedDate.getTime());
			//Timestamp fechaInicio=(Timestamp) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse((row[10].toString()));
			
			
			dc.setFechaInicioCaja(fechaInicio);
			
			}
			
			
if(row[11]== null){
				
				dc.setFechaFinCaja(null);
			}else {
			
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate2 = dateFormat2.parse((row[11].toString()));
		    Timestamp fechaFin = new java.sql.Timestamp(parsedDate2.getTime());
			
			//Timestamp fechaFin=(Timestamp) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse((row[11].toString()));
			
			dc.setFechaFinCaja(fechaFin);
			dc.setEstado(Integer.parseInt(row[12].toString()));
			
			}
			
			
			
			lista.add(dc);
			}

		return lista;
		
		 
		 
	 }
	

	
	@SuppressWarnings("unchecked")
	public List<CpcDistribucionCaja> findAll() {
		Query q = em.createQuery("select a from CpcDistribucionCaja a ");
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcDistribucionCaja ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public CpcDistribucionCaja findOne(Integer iddistribucionCaja){
		Query q = em.createQuery("select a from CpcDistribucionCaja a where a.idTipoTarifa = " + iddistribucionCaja);
		return (CpcDistribucionCaja) q.getSingleResult();
	}

	


	
}

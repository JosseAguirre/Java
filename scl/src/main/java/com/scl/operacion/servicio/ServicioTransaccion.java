package com.scl.operacion.servicio;



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
import com.scl.administracion.modelo.AdmCliente;
import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.administracion.modelo.AdmUsuario;
import com.scl.cpc.modelo.CpcDistribucionCaja;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioTransaccion {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeTransaccion transaccion) {
		em.persist(transaccion);
	}
	
	public void delete(OpeTransaccion transaccion) {
		em.remove(em.merge(transaccion));
	}
	
	public void update(OpeTransaccion transaccion){
	
		em.merge(transaccion);
		
		
	}
	@SuppressWarnings("unchecked")
	 public List<OpeTransaccion> buscaReciboParaAnular (){ // Metodo para buscar la  transaccion validas para ser anuladas
		List <OpeTransaccion> lista = new ArrayList<>();
		
		 Query q = em.createNativeQuery("select id_transaccion, numero_recibo where java.ope_transaccion where id_transaccion = 5 ");
		 
		 List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				OpeTransaccion tr = new OpeTransaccion ();
			tr.setIdTransaccion(Integer.parseInt(row[0].toString()));
			tr.setNumeroRecibo(row[1].toString());
			
			lista.add(tr);
			}

		return lista;
		
		 
		 
	 }
	
	@SuppressWarnings("unchecked")
	 public List<OpeTransaccion> buscaTrNoAsignadas (Date fecha){ // Metodo para buscar la  transaccion no asiganadas a ningun cajero del cpc
		List <OpeTransaccion> lista = new ArrayList<>();
		
		 Query q = em.createNativeQuery("select * from java.sp_cagar_transacciones_no_asiganadas('"+fecha+"') ");
		 
		 List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				OpeTransaccion tr = new OpeTransaccion ();
			tr.setIdTransaccion(Integer.parseInt(row[0].toString()));
			tr.setNumeroRecibo(row[1].toString());
			
			AdmAgencia ag = new AdmAgencia();
			ag.setNombre(row[2].toString());
			
			tr.setIdAgenciaOrigen(ag);
			
			tr.setTotalEfectivo(Float.parseFloat(row[3].toString()));
			tr.setTotalCheque(Float.parseFloat(row[4].toString()));
			tr.setTotalTransaccion(Float.parseFloat(row[5].toString()));
			
			
			lista.add(tr);
			}

		return lista;
		
		 
		 
	 }
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTransaccion> noValidadasI(int idCiuad, Date fecha) { // Busca fundas que hayan ingresado a la boveda de tulas en transito y no hayan sido validadas
		String c = "::date";
		List <OpeDetalleTransaccion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select * from java.sp_cargar_detalle_validacion_ingreso ("+idCiuad+",'"+fecha+"')");
		 
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){

			OpeDetalleTransaccion dt = new OpeDetalleTransaccion();
			dt.setIdDetalleTransaccion(Integer.parseInt(row[0].toString()));
			dt.setSello(row[2].toString());
			dt.setTula(row[3].toString());
			dt.setEfectivo(Float.parseFloat(row[4].toString()));
			dt.setCheque(Float.parseFloat(row[5].toString()));
			
			OpeTransaccion t = new OpeTransaccion();
			t.setNumeroRecibo(row[1].toString());
			
			
			dt.setIdTransaccion(t);
			

			lista.add(dt);
			
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeDetalleTransaccion> noValidadasS(int idCiuad, Date fecha) {
		String c = "::date";
		List <OpeDetalleTransaccion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select * from java.sp_cargar_detalle_validacion_salida ("+idCiuad+",'"+fecha+"')");
		 
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){

			OpeDetalleTransaccion dt = new OpeDetalleTransaccion();
			dt.setIdDetalleTransaccion(Integer.parseInt(row[0].toString()));
			dt.setSello(row[2].toString());
			dt.setTula(row[3].toString());
			dt.setEfectivo(Float.parseFloat(row[4].toString()));
			dt.setCheque(Float.parseFloat(row[5].toString()));
			
			OpeTransaccion t = new OpeTransaccion();
			t.setNumeroRecibo(row[1].toString());
			
			
			dt.setIdTransaccion(t);
			

			lista.add(dt);
			
		}
		return lista;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<OpeTransaccion> findAll() {
		Query q = em.createQuery("select a from OpeTransaccion a");
		return q.getResultList();
	}
	

	/*@SuppressWarnings("unchecked")
	public  List<OpeTransaccion> buscaTransaccionEnDP(int idCiudad) {
		Query q = em.createQuery("select t from OpeTransaccion t  join fetch t.idAgenciaOrigen ao where ao.idCiudadDc = "+idCiudad+" "+
"and t.estadoTransaccion = 12 and t.idTransaccion in (select max(t2.idTransaccion) "+
"from  OpeTransaccion t2 where t.numeroRecibo = t2.numeroRecibo group by t2.numeroRecibo)");
		return q.getResultList();
	}*/
	
	@SuppressWarnings("unchecked")
	public List<OpeTransaccion> buscaTransaccionEnDP(int idCiudad, String idEstado) throws ParseException { /////
		
		List <OpeTransaccion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("Select  "+
"t.id_transaccion, "+
"t.id_rutero, "+
"t.id_agencia_origen, "+
"t.id_agencia_destino, "+
"t.fecha_inicio,"+
"t.fecha_fin,"+
"t.total_efectivo,"+
"t.total_cheque,"+
"t.total_transaccion,"+
"t.total_paquetes,"+
"t.id_cliente ,"+
"t.fecha_sincronizado,"+
"t.fecha_descargado,"+
"t.descargado,"+
"t.estado_transaccion,"+
"t.id_ubicacion_actual,"+
"t.numero_recibo,"+
"t.operacion_cerrada,"+
"t.sincronizado,"+
"t.id_responsable_recepcion,"+
"t.id_responsable_envio,"+
"t.id_vehiculo,"+
"t.id_tipo_tarifa,"+
"t.id_tipo_moneda,"+
"t.generado_por,"+
"t.observacion,"+
"t.fecha_operacion,"+
"ao.nombre as origen,"+
"ad.nombre as destino "+
"from	java.ope_transaccion as  t "+ 
"inner join java.adm_agencia ao on ao.id_agencia = t.id_agencia_origen "+
"inner join java.adm_agencia ad on ad.id_agencia = t.id_agencia_origen "+
"and t.estado_transaccion in ("+idEstado+") "+
"and ao.id_ciudad_dc = "+idCiudad+"  "+
"and t.id_transaccion in (Select max(tr.id_transaccion) from java.ope_transaccion tr   "+
"where tr.numero_recibo = t.numero_recibo group by tr.numero_recibo)	");
		
		 
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeTransaccion t = new OpeTransaccion();
			t.setIdTransaccion(Integer.parseInt(row[0].toString()));
			
			OpeRutero ru = new OpeRutero(); 
			//ru.setIdRutero(Integer.parseInt(row[1].toString()));
			t.setIdRutero(Integer.parseInt(row[1].toString()));
			
			AdmAgencia ao = new  AdmAgencia ();
			ao.setIdAgencia(Integer.parseInt(row[2].toString()));
			ao.setNombre((row[27].toString()));
			t.setIdAgenciaOrigen(ao);
			
			AdmAgencia ad = new  AdmAgencia ();
			ad.setIdAgencia(Integer.parseInt(row[3].toString()));
			ad.setNombre((row[28].toString()));
			t.setIdAgenciaDestino(ad);
			
			
			
			
			
			t.setFechaInicio(Timestamp.valueOf(row[4].toString()));
			t.setFechaFin(Timestamp.valueOf(row[5].toString()));
			
			t.setTotalEfectivo(Float.parseFloat(row[6].toString()));
			t.setTotalCheque(Float.parseFloat(row[7].toString()));
			t.setTotalTransaccion(Float.parseFloat(row[8].toString()));
			
			t.setTotalPaquetes(Integer.parseInt(row[9].toString()));
			
			AdmCliente cl = new  AdmCliente ();
			cl.setIdCliente(Integer.parseInt(row[10].toString()));
			t.setIdCliente(cl);
			
			t.setFechaSincronizado(Timestamp.valueOf(row[11].toString()));
			t.setFechaDescargado(Timestamp.valueOf(row[12].toString()));
			t.setDescargado(Integer.parseInt(row[13].toString()));
			t.setEstadoTransaccion(Integer.parseInt(row[14].toString()));
			t.setIdUbicacionActual(Integer.parseInt(row[15].toString()));
			t.setNumeroRecibo(row[16].toString());
			t.setOperacionCerrada(Integer.parseInt(row[17].toString()));
			t.setSincronizado(Integer.parseInt(row[18].toString()));
			t.setIdResponsableRecepcion(Integer.parseInt(row[19].toString()));
			t.setIdResponsableEnvio(Integer.parseInt(row[20].toString()));
			
			OpeVehiculo ve = new  OpeVehiculo();
			ve.setIdVehiculo(Integer.parseInt(row[21].toString()));
			t.setIdVehiculo(ve);
			
			
			OpeTipoTarifa tt = new  OpeTipoTarifa ();
			tt.setIdTipoTarifa(Integer.parseInt(row[22].toString()));
			t.setIdTipoTarifa(tt);
			
			t.setIdTipoMoneda(Integer.parseInt(row[23].toString()));
			t.setGeneradoPor(row[24].toString());
			t.setObservacion(row[25].toString());
			
			Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(row[26].toString());  
			t.setFechaOperacion(date1);
			
		
			
			
			lista.add(t);
			
		}
		return lista;
		
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public  List<OpeTransaccion> buscaTransaccion(String desde, String hasta, AdmUsuario us ) throws ParseException {
		List <OpeTransaccion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select tr.id_transaccion, "+ 
"ao.nombre as origen, "+
"ad.nombre as destino, "+ 
"tr.fecha_operacion,  "+
"tr.numero_recibo,  "+
"tr.total_efectivo,  "+
"tr.total_cheque,  "+
"tr.total_transaccion,  "+
"tr.total_paquetes  "+
"from java.ope_transaccion as tr "+
"inner join java.adm_agencia as ao on ao.id_agencia = tr.id_agencia_origen "+
"inner join java.adm_agencia as ad on ad.id_agencia = tr.id_agencia_destino "+
"inner join java.adm_usuario_agencia as ua on ua.id_agencia = ao.id_agencia  "+
"where tr.fecha_inicio >= '"+desde+"' and tr.fecha_inicio <= '"+hasta+"' "+
"and ua.id_usuario = "+us.getIdUsuario()+" and tr.estado_transaccion = 10");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
					
			OpeTransaccion t = new OpeTransaccion();
			t.setIdTransaccion(Integer.parseInt(row[0].toString()));
			
			AdmAgencia ao = new AdmAgencia();
			ao.setNombre(row[1].toString());
			
			AdmAgencia ad = new AdmAgencia();
			ad.setNombre(row[2].toString());
			
			t.setIdAgenciaOrigen(ao);
			t.setIdAgenciaDestino(ad);
			
			Date date=new SimpleDateFormat("yyyy-MM-dd").parse(row[3].toString());
			t.setFechaOperacion(date);
			
			t.setNumeroRecibo(row[4].toString());
			t.setTotalEfectivo(Float.parseFloat(row[5].toString()));
			t.setTotalCheque(Float.parseFloat(row[6].toString()));
			t.setTotalTransaccion(Float.parseFloat(row[7].toString()));
			t.setTotalPaquetes(Integer.parseInt(row[8].toString()));
			
			lista.add(t);
			
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public  List<OpeTransaccion> buscaTransaccionNoIdRutero( Date fecha) {
		List <OpeTransaccion> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT id_transaccion, id_agencia_origen FROM java.ope_transaccion where estado_transaccion = 10 and id_rutero isnull and fecha_operacion = '"+fecha+"'");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
					
			OpeTransaccion t = new OpeTransaccion();
			t.setIdTransaccion(Integer.parseInt(row[0].toString()));		
			AdmAgencia a = new AdmAgencia();
			a.setIdAgencia(Integer.parseInt(row[1].toString()));	
			t.setIdAgenciaOrigen(a);
			lista.add(t);
			
		}
		return lista;
	
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createNativeQuery("select max(id_transaccion) from java.ope_transaccion ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public void actualizaNumeroRemesa(int n){
		em.createNativeQuery("update java.ope_parametro_general  set valor = "+n+"  where id_parametro_general = 2 ").executeUpdate();
		
		
		
	}
	
	public void actualizaIdRutero( int idTransaccion, int idRutero){
		em.createNativeQuery("update java.ope_transaccion  set id_rutero = "+idRutero+"  where id_transaccion = "+idTransaccion+" ").executeUpdate();
		
		
		
	}
	
	
	
	public OpeParametroGeneral obtenerNumeroRemesa() {
		OpeParametroGeneral lista = new OpeParametroGeneral();
		Query q = em.createNativeQuery("select  valor, prefijo from java.ope_parametro_general where id_parametro_general = 2 ");
	
		
		OpeParametroGeneral e = new OpeParametroGeneral();
			@SuppressWarnings("unchecked")
			List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				
				
				
				
				
				e.setValor(row[0].toString());
				e.setPrefijo(row[1].toString());
				
			
				
				
			}
			return e;
			
			
			
		}
		

	
	public OpeTransaccion findOne(Integer idTransaccion){
		Query q = em.createQuery("select a from OpeTransaccion a where a.idTransaccion = " + idTransaccion);
		return (OpeTransaccion) q.getSingleResult();
	}

	
	



}


package com.scl.operacion.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.*;
import com.scl.administracion.modelo.AdmUsuarioCiudad;
import com.scl.operacion.modelo.*;

@Stateless
public class ServicioReporte {
	@PersistenceContext
	private EntityManager em;
	
	

	
	@SuppressWarnings("unchecked")
	public List<Object> buscaTransaccionOD (Date fecha) {
		Query q = em.createNativeQuery("select ao.nombre as origen, ad.nombre as destino, tr.numero_recibo, tr.total_efectivo, tr.total_cheque, "+ 
"tr.total_transaccion, tr.total_paquetes  FROM java.ope_transaccion as tr "+
"inner join java.adm_agencia as ao on ao.id_agencia = tr.id_agencia_origen "+
"inner join java.adm_agencia as ad on ad.id_agencia = tr.id_agencia_destino "+
"where tr.fecha_operacion = '"+fecha+"' and tr.estado_transaccion = 11");
		return q.getResultList();
	}
	
	
	
	
	



}


package com.scl.cpc.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.AdmEmpleado;
import com.scl.cpc.modelo.CpcBanco;
import com.scl.cpc.modelo.CpcDetalleDistribucion;
import com.scl.cpc.modelo.CpcPapeleta;
import com.scl.operacion.modelo.OpeDetalleTransaccion;
import com.scl.cpc.modelo.CpcPapeleta;

@Stateless
public class ServicioPapeleta {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcPapeleta papeleta) {
		em.persist(papeleta);
	}
	
	public void delete(CpcPapeleta papeleta) {
		em.remove(em.merge(papeleta));
	}
	
	public void update(CpcPapeleta papeleta){
	
		em.merge(papeleta);
		
		
	}
	
	public void eliminarPapeleta(int idpapeleta) {
		em.createNativeQuery("Delete from java.cpc_papeleta where id_papeleta ="+idpapeleta+" ").executeUpdate();
	}
	
	

	
	@SuppressWarnings("unchecked")
	public List<CpcPapeleta> findAll() {
		Query q = em.createQuery("select a from CpcPapeleta a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcPapeleta> buscaPapeletas( String remesa) {
		List <CpcPapeleta> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select pa.id_papeleta, ba.nombre as banco "+
", pa.numero_cuenta as cuenta "+
", pa.numero_papeleta as papeleta "+
", pa.valor_depositado as valorpapeleta "+
", dt.sello as sello "+
" from java.ope_transaccion as tr "+
"inner join java.ope_detalle_transaccion as dt on dt.id_transaccion = tr.id_transaccion "+
"inner join java.cpc_detalle_distribucion as dd on dd.id_detalle_transaccion = dt.id_detalle_transaccion "+
"inner join java.cpc_papeleta as pa on pa.id_detalle_distribucion = dd.id_detalle_distribucion "+
"inner join java.cpc_banco as ba  on  ba.id_banco = pa.id_banco "+
"where  tr.numero_recibo = '"+remesa+"'");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcPapeleta c = new CpcPapeleta();
			c.setIdPapeleta(Integer.parseInt(row[0].toString()));
			
			CpcBanco b = new CpcBanco();
			b.setNombre(row[1].toString());
			c.setId_banco(b);
			
			c.setNumeroCuenta(row[2].toString());
			
			c.setNumeroPapeleta(row[3].toString());
			c.setValorDepositado(Float.parseFloat(row[4].toString()));
			
			OpeDetalleTransaccion s = new OpeDetalleTransaccion();
			s.setSello(row[5].toString());
			
			CpcDetalleDistribucion dd =  new CpcDetalleDistribucion();
			dd.setIdDetalleTransaccion(s);
			
			c.setIdDetalleDistribucion(dd);
			
			
			
			
			
			
			lista.add(c);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcPapeleta ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public Float findOne(String remesa){
		Query q = em.createNativeQuery("select case when  sum(case when id_detalle_distribucion in ( select  dd.id_detalle_distribucion  from "+  
"java.cpc_detalle_distribucion  as dd "+
"inner join java.ope_detalle_transaccion as dtg on dtg.id_detalle_transaccion = dd. id_detalle_transaccion  "+
"inner join java.ope_transaccion  as tg on tg.id_transaccion = dtg.id_transaccion  "+
"where tg.numero_recibo = '"+remesa+"') then valor_depositado else 0 end) is null then 0.00  else  "+
"sum(case when id_detalle_distribucion in ( select  dd.id_detalle_distribucion  from   "+
"java.cpc_detalle_distribucion  as dd  "+
"inner join java.ope_detalle_transaccion as dtg on dtg.id_detalle_transaccion = dd. id_detalle_transaccion  "+
"inner join java.ope_transaccion  as tg on tg.id_transaccion = dtg.id_transaccion  "+
"where tg.numero_recibo = '"+remesa+"') then valor_depositado else 0 end) end as suma from java.cpc_papeleta ");
		return (Float) q.getSingleResult();
	}


}

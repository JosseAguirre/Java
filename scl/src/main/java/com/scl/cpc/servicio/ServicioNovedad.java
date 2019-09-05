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
import com.scl.cpc.modelo.CpcNovedad;
import com.scl.cpc.modelo.CpcPapeleta;
import com.scl.cpc.modelo.CpcTipoNovedad;
import com.scl.operacion.modelo.OpeDetalleTransaccion;

@Stateless
public class ServicioNovedad {

	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcNovedad novedad) {
		em.persist(novedad);
	}
	
	public void delete(CpcNovedad novedad) {
		em.remove(em.merge(novedad));
	}
	
	public void update(CpcNovedad novedad){
	
		em.merge(novedad);
		
		
	}
	
	public void eliminarNovedad(int idNovedad) {
		em.createNativeQuery("Delete from java.cpc_novedad where id_novedad ="+idNovedad+" ").executeUpdate();
	}
	
	

	
	@SuppressWarnings("unchecked")
	public List<CpcNovedad> findAll() {
		Query q = em.createQuery("select a from CpcNovedad a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcNovedad> buscaNovedad( String remesa) {
		List <CpcNovedad> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select nv.id_novedad, dt.sello as sello, tn.nombre as tipo,  nv.valor as valor, pa.numero_papeleta as papeleta from  "+
"java.ope_transaccion as tr "+
"inner join java.ope_detalle_transaccion as dt on dt.id_transaccion = tr.id_transaccion "+
"inner join java.cpc_detalle_distribucion as dd on dd.id_detalle_transaccion = dt.id_detalle_transaccion "+
"inner join java.cpc_novedad as nv on nv.id_detalle_distribucion = dd.id_detalle_distribucion "+
"inner join java.cpc_tipo_novedad as tn on tn.id_tipo_novedad = nv.id_tipo_novedad "+
"inner join java.cpc_papeleta as pa on pa.id_papeleta = nv.id_papeleta "+
"where tr.numero_recibo = '"+remesa+"'");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcNovedad nov = new CpcNovedad();
			nov.setIdNovedad(Integer.parseInt(row[0].toString()));
			
			OpeDetalleTransaccion dt = new OpeDetalleTransaccion ();
			dt.setSello(row[1].toString());
			
			CpcDetalleDistribucion dd = new CpcDetalleDistribucion();
			dd.setIdDetalleTransaccion(dt);
			
			nov.setIdDetalleDistribucion(dd);
			
			
			CpcTipoNovedad tn = new CpcTipoNovedad();
			tn.setNombre(row[2].toString());
			nov.setIdTipoNovedad(tn);
			
			nov.setValor(Float.parseFloat(row[3].toString()));
			
			CpcPapeleta pa = new CpcPapeleta();
			pa.setNumeroPapeleta(row[4].toString());
			nov.setIdPapeleta(pa);
			
			
			
			
			lista.add(nov);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcNovedad ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public Float findOne (String remesa){
		Query q = em.createNativeQuery("select case when sum( case when id_tipo_novedad = 3 then valor else 0 end) -  "+
"sum( case when id_tipo_novedad = 2 then valor else 0 end) -sum( case when id_tipo_novedad = 1 then valor else 0 end)  is null then 0.00 "+
"else sum( case when id_tipo_novedad = 3 then valor else 0 end) - sum( case when id_tipo_novedad = 2 then valor else 0 end) -sum( case when id_tipo_novedad = 1 then valor else 0 end) end "+
 " as diferencia from java.cpc_novedad where id_detalle_distribucion IN ( "+
"(select  dd.id_detalle_distribucion  from  java.cpc_detalle_distribucion  as dd "+
"inner join java.ope_detalle_transaccion as dtg on dtg.id_detalle_transaccion = dd. id_detalle_transaccion "+
"inner join java.ope_transaccion  as tg on tg.id_transaccion = dtg.id_transaccion "+
"where tg.numero_recibo = '"+remesa+"'))");
		return (Float) q.getSingleResult();
	}


}

package com.scl.cpc.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.scl.cpc.modelo.CpcTipoNovedad;


@Stateless
public class ServicioTipoNovedad {
	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcTipoNovedad CpcTipoNovedad) {
		em.persist(CpcTipoNovedad);
	}
	
	public void delete(CpcTipoNovedad CpcTipoNovedad) {
		em.remove(em.merge(CpcTipoNovedad));
	}
	
	public void update(CpcTipoNovedad CpcTipoNovedad){
	
		em.merge(CpcTipoNovedad);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<CpcTipoNovedad> findAll() {
		Query q = em.createQuery("select a from CpcTipoNovedad a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcTipoNovedad> buscaTipoNovedad( ) {
		List <CpcTipoNovedad> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_tipo_novedad, nombre from java.cpc_tipo_novedad");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcTipoNovedad nov = new CpcTipoNovedad();
			nov.setIdTipoNovedad(Integer.parseInt(row[0].toString()));
			nov.setNombre(row[1].toString());
			
			lista.add(nov);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcTipoNovedad ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public CpcTipoNovedad findOne(Integer idCpcTipoNovedad){
		Query q = em.createQuery("select a from CpcTipoNovedad a where a.idTipoTarifa = " + idCpcTipoNovedad);
		return (CpcTipoNovedad) q.getSingleResult();
	}


}

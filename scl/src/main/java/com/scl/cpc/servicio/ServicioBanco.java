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

@Stateless
public class ServicioBanco {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(CpcBanco banco) {
		em.persist(banco);
	}
	
	public void delete(CpcBanco banco) {
		em.remove(em.merge(banco));
	}
	
	public void update(CpcBanco banco){
	
		em.merge(banco);
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public List<CpcBanco> findAll() {
		Query q = em.createQuery("select a from CpcBanco a ");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CpcBanco> buscaBancos() {
		List <CpcBanco> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_banco, nombre from  java.cpc_banco ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			CpcBanco c = new CpcBanco();
			c.setIdBanco(Integer.parseInt(row[0].toString()));
			c.setNombre(row[1].toString());
			
			
			
			lista.add(c);
			
		}
		return lista;
	}
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from CpcBanco ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public CpcBanco findOne(Integer idbanco){
		Query q = em.createQuery("select a from CpcBanco a where a.idBanco = " + idbanco);
		return (CpcBanco) q.getSingleResult();
	}

	

}

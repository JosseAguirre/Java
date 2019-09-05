package com.scl.operacion.servicio;


	
	import java.util.List;
	import javax.ejb.Stateless;
	import javax.persistence.EntityManager;
	import javax.persistence.PersistenceContext;
	import javax.persistence.Query;

import com.scl.operacion.modelo.OpeDestinoProvisional;

	@Stateless
	public class ServicioDestinoProvisional {
		@PersistenceContext
		private EntityManager em;
		
		public void create(OpeDestinoProvisional destinoProvicional) {
			em.persist(destinoProvicional);
		}
		
		public void delete(OpeDestinoProvisional destinoProvicional) {
			em.remove(em.merge(destinoProvicional));
		}
		
		public void update(OpeDestinoProvisional destinoProvicional){
		
			em.merge(destinoProvicional);
			
			
		}
		
		@SuppressWarnings("unchecked")
		public List<OpeDestinoProvisional> findAll() {
			Query q = em.createQuery("select a from OpeDestinoProvisional a");
			return q.getResultList();
		}
		
		public Integer getPK() {
			Integer codigo = 0;
			Query q = em.createQuery("select max(id) from OpeDestinoProvisional ");
			codigo = (Integer) q.getSingleResult();
			if(codigo == null){
				codigo = 1;
			}else{
				codigo++;
			}
			return codigo;
		}
		
		public OpeDestinoProvisional findOne(Integer idDestinoProvisional){
			Query q = em.createQuery("select a from OpeDestinoProviional a where a.idDestinoProvisional = " + idDestinoProvisional);
			return (OpeDestinoProvisional) q.getSingleResult();
		}

		

	

}

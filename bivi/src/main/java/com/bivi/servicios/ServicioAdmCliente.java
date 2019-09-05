package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.AdmCliente;

@Stateless
public class ServicioAdmCliente {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmCliente admcliente) {
		em.persist(admcliente);
	}
	
	public void delete (AdmCliente admcliente) {
		em.remove(em.merge(admcliente));
	}
	
	public void update (AdmCliente admcliente) {
		em.merge(admcliente);
	}

	@SuppressWarnings("unchecked")
	public List<AdmCliente> findAll() { // Busca todo de la tabla AdmCliente
		Query q = em.createQuery("select b from AdmCliente b   ");
		return q.getResultList();
	}
	
	
	
	
	
	//Busca el todo de la tabla AdmCliente donde el id del cliente padre sea nulo para asi encontrar los clientes padre
	@SuppressWarnings("unchecked")
	public List<AdmCliente> buscaClientePadre(){   
		Query q = em.createQuery("select b from AdmCliente b where b.idClientePadre = null");
		return q.getResultList();
	}
	
	// segunda forma usando lista de objetos genericas
	@SuppressWarnings("unchecked")
	public List<AdmCliente> buscaClientePadre2(){ 
		List <AdmCliente> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_cliente, razon_social from bivi.adm_cliente  where id_cliente_padre isnull");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmCliente e = new AdmCliente();
			e.setIdCliente(Integer.parseInt(row[0].toString()));
			e.setRazonSocial(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
	}
	
	
	
	//busca clientes hijos
	@SuppressWarnings("unchecked")
	public List<AdmCliente> buscaCliente(int idCliente){   
		Query q = em.createQuery("select b from AdmCliente b where b.idClientePadre in ("+idCliente+")");
		return q.getResultList();
	}
	
	
	
	
	
	
	
        
     
	@SuppressWarnings("unchecked")
	public List<AdmCliente> findPadreTodoLista(){   
		Query q = em.createQuery("select b from AdmCliente b where b.idClientePadre = 0");
		return q.getResultList();
	}
        
        @SuppressWarnings("unchecked")
	public List<AdmCliente> findcli() { // Busca todo de la tabla AdmCliente
		Query q = em.createQuery("select b from AdmCliente b where b.sysdelete = false and b.idClientePadre = null");
		return q.getResultList();
	}
	
	public Integer findPadreTodo(){
		Query q = em.createQuery("select b from AdmCliente b where b.idClientePadre = 0");
		return (Integer) q.getSingleResult();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla AdmCliente y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmCliente ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from AdmCliente");
		return (Integer) q.getSingleResult();
	}
	
	//Selecciona todo de la tabla AdmCliente donde el id de la primary key sea igual a la del id seleccionado
	public AdmCliente findOne(Integer codigoAdmCliente){
		Query q = em.createQuery("select b from AdmCliente b where b.idCliente = " + codigoAdmCliente);
		return (AdmCliente) q.getSingleResult();
	}
	
	

	public AdmCliente findIdCliente(Integer idAgencia){
		Query q = em.createQuery("select f.idcliente from Admagencia f where f.idagencia ="+ idAgencia);
		return (AdmCliente)  q.getSingleResult();
	}
	
	
	

	@SuppressWarnings("unchecked")
	public List<AdmCliente> findCliente(Integer idCiudad) {
		return em.createQuery("select c from AdmCliente c where c.idcliente in (select a from Admagencia a where a.idciudaddc = "+idCiudad+")")
				.getResultList();
		
		
	}
	
}

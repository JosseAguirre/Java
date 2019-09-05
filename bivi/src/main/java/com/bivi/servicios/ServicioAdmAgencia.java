package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.AdmAgencia;
import com.bivi.modelos.AdmUsuario;



@Stateless
public class ServicioAdmAgencia {
	@PersistenceContext
	private EntityManager em;

	public void create(AdmAgencia admagencia) {
		em.persist(admagencia);
	}

	public void delete(AdmAgencia admagencia) {
		em.remove(em.merge(admagencia));
	}

	public void update(AdmAgencia admagencia) {
		em.merge(admagencia);
	}

	
	@SuppressWarnings("unchecked")
	public List<AdmAgencia> findAll() { // Busca todo de la tabla AdmAgencia
		Query q = em.createQuery("select b from AdmAgencia b order by b.idAgencia ASC");
		return q.getResultList();
	}
	
	public Integer getPK() { //Busca la primary key mas alta de la tabla AdmAgencia y se le suma 1
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmAgencia ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{ 
			codigo++;
		}
		return codigo;
	}
	
	public Integer findPk(){
		Query q = em.createQuery("select max(id) from AdmAgencia");
		return (Integer) q.getSingleResult();
	}
	
	public AdmAgencia findOne(Integer idAgencia){
		Query q = em.createQuery("select a from AdmAgencia a where a.idAgencia = " + idAgencia);
		return (AdmAgencia) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked") //Busca las agencias asignadas al usuario, metodo usado para los delegados de las agencias
	public List<AdmAgencia> buscaAgenciaCiudad(int idCiudad, AdmUsuario usuario) {
		Query q = em.createQuery("select f from AdmAgencia f  where f.idCiudad = " +idCiudad+" and f.idAgencia in (select a.idAgencia from AdmUsuarioAgencia a where a.idUsuario = "+usuario.getIdUsuario()+")");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked") // busca las agencias segun los clientes, metodo asignado para todos los de operaciones
	public List<AdmAgencia> buscaAgenciaCiudad(int idCiudad, int idCliente) {
		
		List <AdmAgencia> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT  id_agencia, nombre FROM bivi.adm_agencia a where id_ciudad = "+idCiudad+" and  id_cliente in "+
				"(select id_cliente from bivi.adm_cliente  where id_cliente = "+idCliente+")");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmAgencia e = new AdmAgencia();
			e.setIdAgencia(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
		}
		return lista;
	}
	
}

package com.bivi.servicios;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioAdmRol {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmRol AdmRol) {
		em.persist(AdmRol);
	}
	
	public void delete(AdmRol AdmRol) {
		em.remove(em.merge(AdmRol));
	}
	
	public void update(AdmRol AdmRol){
	
		em.merge(AdmRol);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmRol> findAll() { // Busca todo de la tabla AdmRol
		Query q = em.createQuery("select b from AdmRol b   ");
		return q.getResultList();
	}
	
	
	/*
	@SuppressWarnings("unchecked")
	public List<AdmRol> findAll() {
		List <AdmRol> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_rol, nombre, descripcion from bivi.adm_rol  ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmRol e = new AdmRol();
			e.setIdRol(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			e.setDescripcion(row[2].toString());
			
			lista.add(e);
			
		}
		return lista;
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<AdmRol> rolesDisponibles(AdmUsuario us) {
		Query q = em.createQuery("select a from AdmRol a where a.idrol not in (select c.idrol from AdmRolusuario c where c.idusuario = "+us.getIdUsuario()+")");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmRol> rolesAsignados(AdmUsuario us) {
		Query q = em.createNativeQuery("select ru.id_rol_usuario, r.nombre from bivi.adm_rol_usuario as ru "+
"inner join java.adm_rol as r on r.id_rol = ru.id_rol "+
"where ru.id_usuario = "+us.getIdUsuario());
		
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmRol ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmRol findOne(Integer codigoAdmRol){
		Query q = em.createQuery("select c from AdmRol c where c.idempleado = " + codigoAdmRol);
		return (AdmRol) q.getSingleResult();
	}


	
	


}

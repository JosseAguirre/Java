package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;
import com.scl.operacion.modelo.OpeCircuito;

@Stateless
public class ServicioUsuarioCiudad {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmUsuarioCiudad usuarioCiudad) {
		em.persist(usuarioCiudad);
	}
	
	public void delete(AdmUsuarioCiudad usuarioCiudad) {
		em.remove(em.merge(usuarioCiudad));
	}
	
	public void update(AdmUsuarioCiudad usuarioCiudad){
		em.merge(usuarioCiudad);	
	}
	
	
	
	public void eliminarUsuarioCiudad(int idUsuarioCiudad) {
	 em.createNativeQuery("delete from java.adm_usuario_ciudad where id_usuario_ciudad = "+idUsuarioCiudad+" ").executeUpdate();
		
	}

	
	@SuppressWarnings("unchecked")
	public List<AdmUsuarioCiudad> findAll() {
		Query q = em.createQuery("select c from AdmUsuarioCiudad c ");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmUsuarioCiudad> buscaCiudadesAsiganadas(AdmUsuario us) {
		List <AdmUsuarioCiudad> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select uc.id_usuario_ciudad, dc.nombre, dc.id_detalle_catalogo  from java.adm_usuario_ciudad as uc "+
"inner join java.adm_usuario as u on u.id_usuario = uc.id_usuario "+
"inner join java.adm_detalle_catalogo as dc on dc.id_detalle_catalogo = uc.id_ciudad_dc "+
"where u.id_usuario = "+us.getIdUsuario()+" ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmUsuarioCiudad e = new AdmUsuarioCiudad();
			e.setIdUsuarioCiudad(Integer.parseInt(row[0].toString()));
			
			AdmDetalleCatalogo d =  new AdmDetalleCatalogo();
			d.setNombre(row[1].toString());
			d.setIdDetalleCatalogo(Integer.parseInt(row[2].toString()));
			
			e.setIdCiudadDc(d);
			
			lista.add(e);
			
		}
		return lista;
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmUsuarioCiudad ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public Integer getIdRolUsuario(AdmRol rol, AdmUsuario us) {
		Integer codigo = 0;
		Query q = em.createQuery("select c.idrolusuario from AdmUsuarioCiudad c where c.idrol = "+rol.getIdRol()+" and c.idusuario ="+us.getIdUsuario());
		codigo = (Integer) q.getSingleResult();
		
		return codigo;
	}
	
	public AdmUsuarioCiudad findOne(Integer codigorolmmenu){
		Query q = em.createQuery("select c from AdmUsuarioCiudad c where c.idrolmenu = " + codigorolmmenu);
		return (AdmUsuarioCiudad) q.getSingleResult();
	}
	
	

}

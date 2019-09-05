package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.scl.administracion.modelo.*;
import com.scl.operacion.modelo.OpeFrecuenciaVisita;



@Stateless
public class ServicioAdmRolMenu {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmRolMenu admrolmenu) {
		em.persist(admrolmenu);
	}
	
	public void delete(AdmRolMenu admrolmenu) {
		em.remove(em.merge(admrolmenu));
	}
	
	public void update(AdmRolMenu admrolmenu){
	
		em.merge(admrolmenu);
		
		
	}
	

	public void quitarMenuAsignado(AdmRol us, AdmMenu me) {
		
	 em.createNativeQuery("delete from java.adm_rol_menu  where id_menu = "+me.getIdMenu()+" and  id_rol = "+us.getIdRol()+"").executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmRolMenu> menuesAsignados(AdmRol us) {
		System.out.println("El rol traido es  "+us.getIdRol());
		Query q = em.createQuery("select c from AdmRolMenu c where c.idrol = "+us.getIdRol());
		return q.getResultList();
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<AdmRolMenu> findAll() {
		/*CriteriaBuilder cb = em.getCriteriaBuilder();

		 CriteriaQuery<AdmRolMenu> q = cb.createQuery(AdmRolMenu.class);
		  Root<AdmRolMenu> c = q.from(AdmRolMenu.class);
		  q.select(c.get("admmenu")).distinct(true);
	  

	    return q.getResultList();
		
		Query q = em.createQuery("select distinct c.admMenu.idMenu from AdmRolMenu c where c.admRol in (select a.admRol from AdmrolUsuario a where a.admUsuario = 1)  "); 
		return  q.getResultList();
		
		//Query q = em.createQuery("select distinct c.admmenu.idmenu from AdmRolMenu c  where c.admrol in (select a.admrol from Admrolusuario a where a.admusuario = "+idusuario+") group by c.admmenu.idmenu ");
		
	}*/
	
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmRolMenu ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
        ///modiificar
        public Integer getIdRolmenu(AdmMenu menu, AdmRol rol) {
		Integer codigo = 0;
		Query q = em.createQuery("select c.idrolmenu from AdmRolMenu c where c.idmenu = "+menu.getIdMenu()+" and c.idrol ="+rol.getIdRol());
		codigo = (Integer) q.getSingleResult();
		
		return codigo;
	}
	
	public AdmRolMenu findOne(Integer codigorolmmenu){
		Query q = em.createQuery("select c from AdmRolMenu c where c.idrolmenu = " + codigorolmmenu);
		return (AdmRolMenu) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmRolMenu> menuAsignados(AdmRol idRol) {
		List <AdmRolMenu> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select rm.id_rol_menu  as idrolm, m.nombre as rolnombre , rm.id_menu from java.adm_rol_menu as rm "+
"inner join java.adm_menu as m on m.id_menu = rm.id_menu where rm.id_rol = "+idRol.getIdRol());
	
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmRolMenu rm = new AdmRolMenu();
			rm.setIdRolMenu(Integer.parseInt(row[0].toString()));
			
			
		
			
			
			AdmMenu m = new AdmMenu();
			m.setNombre(row[1].toString());
			m.setIdMenu(Integer.parseInt(row[2].toString()));
			
			rm.setIdMenu(m);
			
		
			lista.add(rm);
			
		}
		return lista;
	}
	
	
	public List<AdmMenu> pornombresemejante(String nombre) {
		return em.createQuery("select c from Admmenu c where c.nombres like :nombres", AdmMenu.class)
				.setParameter("nombres", "%" + nombre + "%")
				.getResultList();
	}

	
	


}

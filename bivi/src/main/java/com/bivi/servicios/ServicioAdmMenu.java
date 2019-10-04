package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioAdmMenu {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmMenu admmenu) {
		em.persist(admmenu);
	}
	
	public void delete(AdmMenu admmenu) {
		em.remove(em.merge(admmenu));
	}
	
	public void update(AdmMenu admmenu){
	
		em.merge(admmenu);
		
		
	}
        
        @SuppressWarnings("unchecked")
	public List<AdmMenu> menuAsignados(AdmRol rol) {
		Query q = em.createQuery("select c.idmenu from Admrolmenu c where c.idrol = "+rol.getIdRol());
		return q.getResultList();
	}
        
        @SuppressWarnings("unchecked")
	public List<AdmMenu> menuDisponibles(AdmRol rol) {
		Query q = em.createQuery("select a from AdmMenu a where a.idmenu not in (select c.idmenu from Admrolmenu c where c.idrol = "+rol.getIdRol()+")");
		return q.getResultList();
	}
        
    @SuppressWarnings("unchecked")
	public List<AdmMenu> findAllx() {
    	List <AdmMenu> lista = new ArrayList<>();
    	
		Query q = em.createNativeQuery("select id_menu, nombre, id_padre from java.adm_menu  ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmMenu e = new AdmMenu();
			e.setIdMenu(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			if(row[2].toString()==null){
				e.setIdPadre(null);
			}else{
				e.setIdPadre(Integer.parseInt(row[2].toString()));
			}
			
			lista.add(e);
			
		}
		return lista;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmMenu> findAll() {
		Query q = em.createQuery("select c from AdmMenu c order by c.idMenu ASC");
		return q.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<AdmMenu> findAll2(int s) {
		Query q = em.createQuery("select c from AdmMenu c where c.idPadre = "+s+"");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmMenu> buscaMenu() { // Busca los menues segun el rol del usuario logueado
		List <AdmMenu> lista = new ArrayList<>();
		
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		Query q = em.createNativeQuery("select distinct rm.id_menu as rm, me.id_menu, me.id_padre, me.nombre, me.url_pagina, me.icono, me.orden, me.icono2 from bivi.adm_rol_menu as rm  "+ 
				"inner join bivi.adm_menu as me on me.id_menu = rm.id_menu "+
				"inner join bivi.adm_rol_usuario as ru on ru.id_rol = rm.id_rol "+ 
				"where ru.id_usuario = "+us.getIdUsuario()+" order by me.orden asc");
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmMenu c = new AdmMenu();
			c.setIdMenu(Integer.parseInt(row[1].toString()));
			
			if(row[2]== null){
				c.setIdPadre(null);	
			} else {
				c.setIdPadre(Integer.parseInt(row[2].toString()));
			}
			
			c.setNombre((row[3].toString()));
			
			if(row[4] == null){
				c.setUrlPagina(null);	
			}else {
				c.setUrlPagina((row[4].toString()));
			}
			if(row[5] == null){
				c.setIcono(null);	
			}else{
				c.setIcono((row[5].toString()));
			}
			
			if(row[6] == null){
				c.setOrden(null);
			}else{
			c.setOrden(Integer.parseInt(row[6].toString()));
			}
			
			if(row[7] == null){
				c.setIcono2(null);	
			}else{
			c.setIcono2(row[7].toString());
			}
			
			
			lista.add(c);
			
		}
		return lista;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmMenu> findAll2() {
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		Query q = em.createQuery("select distinct c.admmenu from Admrolmenu c where c.admrol in (select a.admrol from Admrolusuario a where a.admusuario = "+us.getIdUsuario()+") ");
		
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmMenu ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmMenu findOne(Integer codigoadmmenu){
		Query q = em.createQuery("select c from AdmMenu c where c.idempleado = " + codigoadmmenu);
		return (AdmMenu) q.getSingleResult();
	}
	
	
	public List<AdmMenu> pornombresemejante(String nombre) {
		return em.createQuery("select c from AdmMenu c where c.nombres like :xxxxxxx", AdmMenu.class)
				.setParameter("xxxxxxx", "%" + nombre + "%")
				.getResultList();
	}

	
	

}
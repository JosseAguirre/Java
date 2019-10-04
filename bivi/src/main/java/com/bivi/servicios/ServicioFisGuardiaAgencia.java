package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bivi.modelos.*;


@Stateless
public class ServicioFisGuardiaAgencia {
	@PersistenceContext
	private EntityManager em;
	
	public void create(FisGuardiaAgencia guardiaAgencia) {
		em.persist(guardiaAgencia);
	}
	
	public void delete(FisGuardiaAgencia guardiaAgencia) {
		em.remove(em.merge(guardiaAgencia));
	}
	
	public void update(FisGuardiaAgencia guardiaAgencia){
		em.merge(guardiaAgencia);	
	}
	
	
	private int idPruestoConsulta;
	
	@SuppressWarnings("unchecked")
	public List<FisGuardiaAgencia> findAll() {
		Query q = em.createQuery("select b from FisGuardiaAgencia b order by b.idGuardiaAgencia ASC");
		return q.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<FisGuardiaAgencia> buscaAsigancion() {
		Query q = em.createQuery("select a from FisGuardiaAgencia a");	
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from FisGuardiaAgencia ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public FisGuardiaAgencia findOne(Integer codigoFisGuardiaAgencia){
		Query q = em.createQuery("select c from FisGuardiaAgencia c where c.idGuardiaAgencia = " + codigoFisGuardiaAgencia);
		return (FisGuardiaAgencia) q.getSingleResult();
	}
	
	
	@SuppressWarnings("unchecked")  
	public List<FisGuardiaAgencia> buscaPuestoUsuario(AdmUsuario u) {
		
		List <FisGuardiaAgencia> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select pu.id_puesto, pu.nombre_puesto from bivi.fis_guardia_agencia as ga "+
				"inner join bivi.adm_puestos as pu on pu.id_puesto = ga.id_puesto "+
				"inner join bivi.adm_usuario as us on us.id_usuario = ga.id_usuario "+
				"where ga.id_usuario = "+u.getIdUsuario()+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmPuesto p = new AdmPuesto();
			p.setIdPuesto(Integer.parseInt(row[0].toString()));
			p.setNombrePuesto(row[1].toString());
			
			
			FisGuardiaAgencia ga = new FisGuardiaAgencia();		
			ga.setIdPuesto(p);
			
			idPruestoConsulta = p.getIdPuesto();
			
			lista.add(ga);
			
		}
		return lista;	
		
	}
	
	@SuppressWarnings("unchecked")  
	public List<FisGuardiaAgencia> buscaGuardiasPuesto(Integer idPuesto) {
		
		List <FisGuardiaAgencia> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select us.id_usuario, us.usuario from bivi.fis_guardia_agencia as ga "+
				"inner join bivi.adm_puestos as p on p.id_puesto = ga.id_puesto "+
				"inner join bivi.adm_usuario as us on us.id_usuario = ga.id_usuario "+
				"where p.id_puesto = "+idPuesto+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmUsuario us = new AdmUsuario();
			us.setIdUsuario(Integer.parseInt(row[0].toString()));
			us.setUsuario(row[1].toString());
			
			
			FisGuardiaAgencia ga = new FisGuardiaAgencia();
			ga.setIdUsuario(us);
			
			
			lista.add(ga);
			
		}
		return lista;	
		
	}

	public int getIdPruestoConsulta() {
		return idPruestoConsulta;
	}

	public void setIdPruestoConsulta(int idPruestoConsulta) {
		this.idPruestoConsulta = idPruestoConsulta;
	}

}

package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;
import com.scl.operacion.modelo.OpeDetalleTransaccion;
import com.scl.operacion.modelo.OpeTransaccion;

@Stateless
public class ServicioAdmUsuario {
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmUsuario admusuario) {
		em.persist(admusuario);
	}
	
	public void delete(AdmUsuario admusuario) {
		em.remove(em.merge(admusuario));
	}
	
	public void update(AdmUsuario admusuario){
	
		em.merge(admusuario);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmUsuario> findAll() {
		List <AdmUsuario> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT id_usuario, usuario,password, id_estado_dc, id_empleado FROM java.adm_usuario");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			
			
			AdmUsuario us = new AdmUsuario();
			us.setIdUsuario(Integer.parseInt(row[0].toString()));
			us.setUsuario(row[1].toString());
			us.setPassword(row[2].toString());
			
			AdmDetalleCatalogo dc = new AdmDetalleCatalogo();
			dc.setIdDetalleCatalogo(Integer.parseInt(row[3].toString()));
			
			AdmEmpleado em = new AdmEmpleado();
			em.setIdEmpleado(Integer.parseInt(row[4].toString()));

			us.setIdEstadoDc(dc);
			us.setIdEmpleado(em);
			
			lista.add(us);
			
		}
		return lista;
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmUsuario ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public AdmUsuario findOne(Integer codigoadmusuario){
		Query q = em.createQuery("select a from AdmUsuario a where a.idusuario = " + codigoadmusuario);
		return (AdmUsuario) q.getSingleResult();
	}

	

	

}

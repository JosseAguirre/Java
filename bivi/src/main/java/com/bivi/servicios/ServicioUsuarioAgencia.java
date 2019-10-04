package com.bivi.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.bivi.modelos.*;

@Stateless
public class ServicioUsuarioAgencia {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(AdmUsuarioAgencia usuarioAgencia) {
		em.persist(usuarioAgencia);
	}
	
	public void delete(AdmUsuarioAgencia usuarioAgencia) {
		em.remove(em.merge(usuarioAgencia));
	}
	
	public void update(AdmUsuarioAgencia usuarioAgencia){
		em.merge(usuarioAgencia);	
	}
	
	
	public void eliminaAgenciaAsiganda(int idUsuarioAgencia){
		em.createNativeQuery("delete from bivi.adm_usuario_agencia where id_usuario_agencia = "+idUsuarioAgencia+"").executeUpdate();
	}
	
	@SuppressWarnings("unchecked") // busca las agencias segun los clientes,ciudad y usuario  metodo usado para buscar las agenciasasignads  a los usuario 
	public List<AdmUsuarioAgencia> buscaAgenciaCiudadCliente(AdmUsuario us) {
		
		List <AdmUsuarioAgencia> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select ua.id_usuario_agencia, ag.nombre, cl.razon_social from bivi.adm_usuario_agencia as ua "+
				"inner join bivi.adm_agencia as ag on ag.id_agencia = ua.id_agencia "+
				"inner join bivi.adm_usuario as us on us.id_usuario = ua.id_usuario "+
				"inner join bivi.adm_cliente as cl on cl.id_cliente = ag.id_cliente "+
				"where us.id_usuario = "+us.getIdUsuario()+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmUsuarioAgencia e = new AdmUsuarioAgencia();
			e.setIdUsuarioAgencia(Integer.parseInt(row[0].toString()));
			
			AdmAgencia a = new AdmAgencia();
			a.setNombre(row[1].toString());
			
			AdmCliente c = new AdmCliente();
			c.setRazonSocial(row[2].toString());
			
			a.setIdCliente(c);
			
			
			e.setIdAgencia(a);
			
			
			lista.add(e);
			
		}
		return lista;	
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmUsuarioAgencia> findAll() {
		Query q = em.createQuery("select c from AdmUsuarioAgencia c ");
		return q.getResultList();
	}
	
	
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmUsuarioAgencia ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	
	public AdmUsuarioAgencia findOne(Integer codigorolmmenu){
		Query q = em.createQuery("select c from AdmUsuarioAgencia c where c.idrolmenu = " + codigorolmmenu);
		return (AdmUsuarioAgencia) q.getSingleResult();
	}

}

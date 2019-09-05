package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;


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

	// GENERO UNA LISTA PARA OBTENER TOSOD LOS VALORES DE LA TABLA AGENCIA QUE
	// NO SE ENCUENTREN COMO ELIMINADO (NADA SE ELIMINA SOLO CAMBIA COMO
	// SYSDELETE)
	
	
	@SuppressWarnings("unchecked") //Busca las agencias asignadas al usuario, metodo usado para los delegados de las agencias
	public List<AdmAgencia> buscaAgenciaCiudad(int idCiudad, AdmUsuario usuario) {
		Query q = em.createQuery("select f from AdmAgencia f  where  f.idEstadoDc = 91  and f.idCiudadDc = " +idCiudad+" and f.idAgencia in (select a.idAgencia from AdmUsuarioAgencia a where a.idUsuario = "+usuario.getIdUsuario()+")");
		return q.getResultList();
		
		
	}

	@SuppressWarnings("unchecked") // busca las agencias segun los clientes, metodo asignado para todos los de operaciones
	public List<AdmAgencia> buscaAgenciaCiudad(int idCiudad, int idCliente) {
		
		List <AdmAgencia> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT  id_agencia, nombre FROM java.adm_agencia a where id_ciudad_dc = "+idCiudad+" and id_agencia_padre isnull  and  id_cliente in "+
"(select id_cliente from java.adm_cliente  where id_cliente_padre = "+
"(select id_cliente from java.adm_cliente c where id_cliente = "+idCliente+"))");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmAgencia e = new AdmAgencia();
			e.setIdAgencia(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
		
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmAgencia> buscaAgenciaCiudad2(int idCiudad, int idCliente) {
		Query q = em.createQuery("SELECT a FROM AdmAgencia a where a.idCiudadDc = "+idCiudad+" and a.idAgenciaPadre = null  and  a.idCliente in (select b.idCliente from AdmCliente b where b.idClientePadre = (select c.idCliente from AdmCliente c where c.idCliente = "+idCliente+"))");
		return q.getResultList();
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmAgencia> buscaAgencia(int idCliente) {
		Query q = em.createQuery("select f from AdmAgencia f  where f.idCliente in ("+idCliente+") and  f.idEstadoDc = 91");
		return q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmAgencia> buscaAgenciasPadre(int idCiudad) {
		//Query q = em.createNativeQuery("SELECT a.id_agencia, a.nombre FROM java.adm_agencia a where a.id_ciudad_dc = "+idCiudad+"", AdmAgencia.class);
		//return  q.getResultList();
		 
		
		Query q = em.createQuery("select g from AdmAgencia g  where g.idAgenciaPadre = null and g.idCiudadDc = "+idCiudad+" ");
		return q.getResultList();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<AdmAgencia> findAgenciaCiudad(Integer idCiudad) {
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		System.out.println("user es "+us.getUsuario());
		Query q = em.createQuery("select f.idAgencia from AdmEmpleadoAgencia f where f.idempleado = (select a.idEmpleado from AdmUsuario a where a.idUsuario = "+us.getIdUsuario()+") and f.idAgencia.idCiudaddc ="+idCiudad);
		return  q.getResultList();
	}

	public Integer findPk() {

		Query q = em.createQuery("select max(id) from AdmAgencia");
		return (Integer) q.getSingleResult();

	}

	// GENER UNA BUSQUEDA PARA OBTENER EL ID DE MAYOR VALOR
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmAgencia ");
		codigo = (Integer) q.getSingleResult();
		if (codigo == null) {
			codigo = 1;
			// SI EL CODIGO ES MAYOR A NADA SE LE SUMA 1 Y ME DEVUELVE EL NUEVO
			// CODIGO
		} else {
			codigo++;
		}
		return codigo;
	}

	// GENERO UNA BUSQUEDA PARA ENCONTAR EL ID DE LA AGENCIA
	public AdmAgencia findOne(Integer codigoadmagencia) {
		Query q = em.createQuery("select f from AdmAgencia f where f.idAgencia = " + codigoadmagencia);
		return (AdmAgencia) q.getSingleResult();
	}

	// GENERO UNA BUSQUEDA POR MEDIO DE UNA LISTA PARA FILTRAR UNA BUSQUEDA.
	public List<AdmAgencia> pornombresemejante(String nombre) {
		return em.createQuery("select f from AdmAgencia f where c.nombres like :nombres", AdmAgencia.class)
				.setParameter("nombres", "%" + nombre + "%").getResultList();
	}

	// GENERO UNA BUSQUEDA PARA ENCONTRAR TODOS LOS IDAGENCIAPADRE QUE TENGAN UN
	// VALOR 0 (PRA SABER QUE SON AGENCIAS SIN PADRE)
	

	// Metodo para buscar todos los agencias cuyo idagencia sea 0 es decir la
	// aencia padre por defecto
	public Integer findPadreagencia() {
		Query q = em.createQuery("select idAgencia from AdmAgencia g where g.idAgencia = 0");
		return (Integer) q.getSingleResult();
	}
}

package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.scl.administracion.modelo.*;
import com.scl.operacion.modelo.OpeVehiculo;

@Stateless
public class ServicioAdmEmpleado {
	@PersistenceContext
	private EntityManager em;

	public void create(AdmEmpleado admempleado) {
		em.persist(admempleado);
	}

	public void delete(AdmEmpleado admempleado) {
		em.remove(em.merge(admempleado));
	}

	public void update(AdmEmpleado admempleado) {

		em.merge(admempleado);

	}
	// busca todo los cajeros asignados a caja
		@SuppressWarnings("unchecked")
		public List<AdmEmpleado> buscaCajerosAsignados(Date fecha) { //lista de empleados para la creacion de usuarios
			List <AdmEmpleado> lista = new ArrayList<>();
			Query q = em.createNativeQuery("select em.id_empleado, em.nombres, em.apellidos FROM java.cpc_caja as ca "+ 
"inner join java.adm_empleado as em  on em.id_empleado = ca.id_empleado where ca.fecha = '"+fecha+"' ");
			List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				
				AdmEmpleado em = new AdmEmpleado();
				em.setIdEmpleado(Integer.parseInt(row[0].toString()));
				em.setNombres(row[1].toString());
				em.setApellidos(row[2].toString());
				
				
				lista.add(em);
				
			}
			return lista;
		}
	
	// busca todo los cajeros
	@SuppressWarnings("unchecked")
	public List<AdmEmpleado> buscaCajeros() { //lista de empleados para la creacion de usuarios
		List <AdmEmpleado> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select em.id_empleado, em.nombres, em.apellidos FROM java.adm_rol_usuario as ru  "+
"inner join java.adm_usuario as us on us.id_usuario = ru.id_usuario "+
"inner join java.adm_empleado as em  on em.id_empleado = us.id_empleado ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmEmpleado em = new AdmEmpleado();
			em.setIdEmpleado(Integer.parseInt(row[0].toString()));
			em.setNombres(row[1].toString());
			em.setApellidos(row[2].toString());
			
			
			lista.add(em);
			
		}
		return lista;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<AdmUsuario> buscaUsuariosValidadores() { //lista de empleados para la creacion de usuarios
		List <AdmUsuario> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select us.id_usuario, us.usuario FROM java.adm_usuario as us  "+ 
"inner join java.adm_rol_usuario as ru on ru.id_usuario = us.id_usuario  "+
"where ru.id_rol in (7,8)");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmUsuario em = new AdmUsuario();
			em.setIdUsuario(Integer.parseInt(row[0].toString()));
			em.setUsuario(row[1].toString());
		
			
		
			lista.add(em);
			
		}
		return lista;
	}
	
	
	

	// trae todos los empleados 
	@SuppressWarnings("unchecked")
	public List<AdmEmpleado> findAll() { //lista de empleados para la creacion de usuarios
		List <AdmEmpleado> lista = new ArrayList<>();
		Query q = em.createNativeQuery("SELECT id_empleado, nombres, apellidos, id_estado_dc FROM java.adm_empleado ");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmEmpleado em = new AdmEmpleado();
			em.setIdEmpleado(Integer.parseInt(row[0].toString()));
			em.setNombres(row[1].toString());
			em.setApellidos(row[2].toString());
			
			AdmDetalleCatalogo estado = new  AdmDetalleCatalogo();
			estado.setIdDetalleCatalogo(Integer.parseInt(row[3].toString()));
			
			em.setIdEstadoDc(estado);
			lista.add(em);
			
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmEmpleado> buscaTripulantePrueba(int idCiudad, String fecha) {
		List <AdmEmpleado> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select e.id_empleado, e.nombres, e.apellidos from java.adm_rol_usuario as ru "+
"inner join java.adm_usuario as u on u.id_usuario = ru.id_usuario "+
"inner join java.adm_empleado as e on e.id_empleado = u.id_empleado "+
"where ru.id_rol = 2  "+
"and ru.id_usuario not in ( select q.id_usuario from java.adm_usuario q where q.id_empleado in "+ 
"(select c.id_empleado from java.ope_detalle_tripulacion c where c.id_tripulacion in "+
"(select m.id_tripulacion from java.ope_Tripulacion m  where m.fecha_operacion ='"+fecha+"'))) and e.id_ciudad_dc = "+idCiudad+"");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmEmpleado v = new AdmEmpleado();
			v.setIdEmpleado(Integer.parseInt(row[0].toString()));
			v.setNombres(row[1].toString());
			v.setApellidos(row[2].toString());
			lista.add(v);
			
		}
		return lista;
	}
	
	

	// busca la pk mayor y le suma uno
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmEmpleado  ");
		codigo = (Integer) q.getSingleResult();
		if (codigo == null) {
			codigo = 1;
		} else {
			codigo++;
		}
		return codigo;
	}
	// Busca el id seleccionado
	public AdmEmpleado findOne(Integer bovbovedacliente) {
		Query q = em.createQuery("select f from AdmEmpleado f where f.idEmpleado = " + bovbovedacliente);
		return (AdmEmpleado) q.getSingleResult();
	}
	// busca el id del empleado seleccionado
	public AdmEmpleado findempleado() {
		
		AdmUsuario us = (AdmUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuario");
		Query q = em.createQuery("select c from AdmEmpleado c where c.idEmpleado = (select a from Admusuario a where idUsuario = "+us.getIdUsuario()+")");
		return (AdmEmpleado) q.getSingleResult();
	}

	// usado para buscar el empleado por el nombre semejante
	public List<AdmEmpleado> pornombresemejante(String nombre) {
		return em.createQuery("select c from AdmEmpleado c where c.nombres like :nombres", AdmEmpleado.class)
				.setParameter("nombres", "%" + nombre + "%").getResultList();
	}

	// selecciona todos los empleados cuyo cargo sea cajero principal o cajero
	@SuppressWarnings("unchecked")
	public List<AdmEmpleado> empleadocambioturno() {
		Query q = em.createQuery("select c from AdmEmpleado c where c.idCargoDc = 113 or c.idCargoDc = 114");
		return q.getResultList();
	}
	
}

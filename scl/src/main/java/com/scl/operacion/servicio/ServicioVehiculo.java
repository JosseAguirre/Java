package com.scl.operacion.servicio;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.operacion.modelo.*;

@Stateless
public class ServicioVehiculo {
	@PersistenceContext
	private EntityManager em;
	
	public void create(OpeVehiculo vehiculo) {
		em.persist(vehiculo);
	}
	
	public void delete(OpeVehiculo vehiculo) {
		em.remove(em.merge(vehiculo));
	}
	
	public void update(OpeVehiculo vehiculo){
	
		em.merge(vehiculo);
		
		
	}
	
	@SuppressWarnings("unchecked")
	 public List<OpeVehiculo> buscarVehiculosCiudad (int idCiudad){ // Metodo para buscar la  transaccion validas para ser anuladas
		List <OpeVehiculo> lista = new ArrayList<>();
		
		 Query q = em.createNativeQuery("select ve.id_vehiculo, ve.descripcion, ve.placa, ca.nombre from java.ope_vehiculo as ve "+ 
"inner join java.ope_categoria_vehiculo as ca on ca.id_categoria_vehiculo = ve.id_categoria_vehiculo "+
"where id_ciudad_dc = "+idCiudad+" ");
		 
		 List<Object[]> rows =  q.getResultList();
			for(Object[] row : rows){
				OpeVehiculo ve = new OpeVehiculo ();
			ve.setIdVehiculo(Integer.parseInt(row[0].toString()));
			ve.setDescripcion(row[1].toString());
			ve.setPlaca(row[2].toString());
			
			OpeCategoriaVehiculo ca = new OpeCategoriaVehiculo ();
			ca.setNombre(row[3].toString());
			
			ve.setIdCategoriaVehiculo(ca);
			
			lista.add(ve);
			}

		return lista;
		
		 
		 
	 }
	
	@SuppressWarnings("unchecked")
	public List<OpeVehiculo> findAll() {
		Query q = em.createQuery("select a from OpeVehiculo a");
		return q.getResultList();
	}
	
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from OpeVehiculo ");
		codigo = (Integer) q.getSingleResult();
		if(codigo == null){
			codigo = 1;
		}else{
			codigo++;
		}
		return codigo;
	}
	
	public OpeVehiculo findOne(Integer idVehiculo){
		Query q = em.createQuery("select a from OpeVehiculo a where a.idVehiculo = " + idVehiculo);
		return (OpeVehiculo) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<OpeVehiculo> buscaVehiculo(int idCiudad, String fecha) {
		Query q = em.createQuery("select a from OpeVehiculo a JOIN FETCH a.idCiudadDc where a.idCiudadDc = "+idCiudad+" and a.idVehiculo not in (select c.idVehiculo from OpeTripulacion c where c.fechaOperacion ='"+fecha+"')");
		return q.getResultList(); 
		
		
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	public List<OpeVehiculo> buscaVehiculoPrueba(int idCiudad, String fecha) {
		List <OpeVehiculo> lista = new ArrayList<>();
		Query q = em.createNativeQuery("select id_vehiculo, descripcion from java.ope_vehiculo   where id_ciudad_dc = "+idCiudad+" and id_Vehiculo not in " +
				"(select id_Vehiculo from java.Ope_Tripulacion  where fecha_Operacion ='"+fecha+"')");
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			OpeVehiculo v = new OpeVehiculo();
			v.setIdVehiculo(Integer.parseInt(row[0].toString()));
			v.setDescripcion(row[1].toString());
			lista.add(v);
			
		}
		return lista;
		
		
	}

	



}


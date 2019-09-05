package com.scl.administracion.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.scl.administracion.modelo.*;

@Stateless
public class ServicioAdmDetalleCatalogo {
	@PersistenceContext
	private EntityManager em;

	public void create(AdmDetalleCatalogo detallecatalogo) {
		em.persist(detallecatalogo);
	}

	public void delete(AdmDetalleCatalogo detallecatalogo) {
		em.remove(em.merge(detallecatalogo));
	}

	public void update(AdmDetalleCatalogo detallecatalogo) {

		em.merge(detallecatalogo);

	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> ciudadesTodas() { //busca todas las ciudadades con query sqlnativo
		List <AdmDetalleCatalogo> lista = new ArrayList<>();
		
		Query q = em.createNativeQuery("select id_detalle_catalogo, nombre from  java.adm_detalle_catalogo  where id_catalogo = 3");
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmDetalleCatalogo e = new AdmDetalleCatalogo();
			e.setIdDetalleCatalogo(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
	
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> ciudadesAsignadas(AdmUsuario us) {
		List <AdmDetalleCatalogo> lista = new ArrayList<>();
		
		Query q = em.createNativeQuery("select dc.id_detalle_catalogo, dc.nombre from java.adm_usuario_ciudad as uc "+
"inner join java.adm_detalle_catalogo as dc on dc.id_detalle_catalogo = uc.id_ciudad_dc where uc.id_Usuario = "+us.getIdUsuario()+"");
		
		List<Object[]> rows =  q.getResultList();
		for(Object[] row : rows){
			
			AdmDetalleCatalogo e = new AdmDetalleCatalogo();
			e.setIdDetalleCatalogo(Integer.parseInt(row[0].toString()));
			e.setNombre(row[1].toString());
			
			lista.add(e);
			
		}
		return lista;
	
		
	}
	
	
			
			
	//OJO NO SE PARA ESTE METODO
	// Metodo para obtener el Nivel de Servicio de la tabla Admdetallecatálogo
		@SuppressWarnings("unchecked")
		public List<AdmDetalleCatalogo> nivelTransaccion() {
			Query q = em.createQuery("SELECT c FROM AdmDetalleCatalogo c where idCatalogo = 28");
			return q.getResultList();
		}
		
		
	
	// Metodo para obtener el Tipo de Cliente de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> tiposcliente() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where idCatalogo = 16 ");
		return q.getResultList();
	}

	// Metodo para obtener la Prioridad de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> prioridad() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where idCatalogo = 15 ");
		return q.getResultList();
	}

	// Metodo para obtener las Actividades de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> actividades() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 14 ");
		return q.getResultList();
	}
	//Metodo para obtener las ciudades de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> ciudades() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 3 ");
		return q.getResultList();
	}
	//Metodo para obtener el genero de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> sexos() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 6 ");
		return q.getResultList();
	}
	
	
	//no se para es te emetodo
	//Metodo para obtener os estados de la tabla Admdetallecatálogo --cod 10  esatdoa para cliente/agencias
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> estados() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 10 ");
		return q.getResultList();
	}
	
	
	//Metodo para obtener los estados de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> estadosusuario() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 11 ");
		return q.getResultList();
	}
	
	
	//Metodo para obtener los estados de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> estadosEmpleados() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 12 ");
		return q.getResultList();
	}
	
	//ojo  no sepa que este metodo
	//Metodo para obtener los estados de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> estadoagencia() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 10 ");
		return q.getResultList();
	}
	
	
	
	
	//ojo no separa que es este metodo
	//Metodo para obtener los estados de la transaccion
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> estadotransf() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 26 ");
		return q.getResultList();
	}
	
	
	//Metodo para obtener los cargos de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> cargos() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 4 ");
		return q.getResultList();
	}
	//Metodo para obtener la clase de empleado de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> clasesempleado() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 9 ");
		return q.getResultList();
	}
	//Metodo para obtener los departamentos de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> departamentos() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 5 ");
		return q.getResultList();
	}
	//Metodo para obtener las lineas de negocio de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> lineasnegocio() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 7 ");
		return q.getResultList();
	}
	//Metodo para obtener el tipo empleado de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> tiposempleado() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c where c.idCatalogo = 8 ");
		return q.getResultList();
	}
	//Metodo para obtener toda la información de la tabla Admdetallecatálogo
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> findAll() {
		Query q = em.createQuery("select c from AdmDetalleCatalogo c");
		return q.getResultList();
	}
	//metodo para encontar la id mas alta y sumarle uno//usado en el metodo guardar
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmDetalleCatalogo ");
		codigo = (Integer) q.getSingleResult();
		if (codigo == null) {
			codigo = 1;
		} else {
			codigo++;
		}
		return codigo;
	}
	
	//np se para q sirve esto
//Metodo para obtener el iddetallecatalogo//
	public AdmDetalleCatalogo findOne(Integer iddetallecatalogo) {
		Query q = em.createQuery("select g from AdmDetalleCatalogo g where g.idDetalleCatalogo = "+ iddetallecatalogo);
		return (AdmDetalleCatalogo) q.getSingleResult();
	}
	
	
	// Metodo para que me busque todos los iddetallecatalogopadre que tengan el valor de 0
	//usado para llenar un select one menu de detallecatálogopadre
	@SuppressWarnings("unchecked")
	public List<AdmDetalleCatalogo> detallecatalogopadre() {
		Query q = em.createQuery("select g from AdmDetalleCatalogo g  where g.idDetalleCatalogoPadre = 0");
		return q.getResultList();
	}
	// Metodo para buscar todos los detallecatalogos cuyo iddetallecatalogo sea 0 es decir el
	// catalogo padre por defecto
	public Integer findPadreDetalleCatalogo() {
		Query q = em.createQuery("select idDetalleCatalogo from AdmDetalleCatalogo g where g.idDetalleCatalogo = 0");
		return (Integer) q.getSingleResult();
	}
}

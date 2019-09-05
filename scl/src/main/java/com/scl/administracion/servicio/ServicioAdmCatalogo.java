package com.scl.administracion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.scl.administracion.modelo.*;

@Stateless
public class ServicioAdmCatalogo {
	@PersistenceContext
	private EntityManager em;

	public void create(AdmCatalogo admcatalogo) {
		em.persist(admcatalogo);
	}

	public void delete(AdmCatalogo admcatalogo) {
		em.remove(em.merge(admcatalogo));
	}

	public void update(AdmCatalogo admcatalogo) {

		em.merge(admcatalogo);

	}

	// metodo para buscar toda lainformación de la base de datos de la tabla
	// AdmCatalogo
	@SuppressWarnings("unchecked")
	public List<AdmCatalogo> findAll() {
		Query q = em.createQuery("select a from AdmCatalogo a");
		return q.getResultList();
	}

	// Metodo para seleccionar el id mas alto y sumarle uno si es mayor q cero o
	// manejarlo como cero//se usa en el metodo guardar
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmCatalogo ");
		codigo = (Integer) q.getSingleResult();
		if (codigo == null) {
			codigo = 1;
		} else {
			codigo++;
		}
		return codigo;
	}

	// Metodo para que me retorne el id del catalogo//usado en el metodo
	// actualizar por medio de la busqueda en combos
	public AdmCatalogo findOne(Integer codigocatalogo) {
		Query q = em.createQuery("select a from AdmCatalogo a where a.idCatalogo = " + codigocatalogo);
		return (AdmCatalogo) q.getSingleResult();
	}

	public List<AdmDetalleCatalogo> estadosusuario() {
		return null;
	}

	// Metodo para que me busque todos los idcatalogopadreque tengan el valor de 0
	//usado para llenar un select one menu de catálogopadre
	@SuppressWarnings("unchecked")
	public List<AdmCatalogo> catalogopadre() {
		Query q = em.createQuery("select g from AdmCatalogo g  where g.idPadreCatalogo = 0");
		return q.getResultList();
	}

	// Metodo para buscar todos los catalogos cuyo idcatalogo sea 0 es decir el
	// catalogo padre por defecto
	public Integer findPadreCatalogo() {
		Query q = em.createQuery("select idCatalogo from AdmCatalogo c where c.idCatalogo = null");
		return (Integer) q.getSingleResult();
	}

}

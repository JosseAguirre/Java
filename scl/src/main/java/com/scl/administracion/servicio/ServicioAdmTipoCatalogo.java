
package com.scl.administracion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scl.administracion.modelo.*;

@Stateless
public class ServicioAdmTipoCatalogo {
	@PersistenceContext
	private EntityManager em;

	public void create(AdmTipoCatalogo admtipocatalogo) {
		em.persist(admtipocatalogo);
	}

	public void delete(AdmTipoCatalogo admtipocatalogo) {
		em.remove(em.merge(admtipocatalogo));
	}

	public void update(AdmTipoCatalogo admtipocatalogo) {

		em.merge(admtipocatalogo);

	}

	// Metodo paraobener toda la información de a tabla Admtipocatálogo
	@SuppressWarnings("unchecked")
	public List<AdmTipoCatalogo> findAll() {
		Query q = em.createQuery("select a from AdmTipoCatalogo a");
		return q.getResultList();
	}

	// Metodo para obtener la id mas alta y se suma unoen caso de ser mayor a
	// uno ysi no existe se crea como uno//usado para el metodo guardar
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmTipoCatalogo ");
		codigo = (Integer) q.getSingleResult();
		if (codigo == null) {
			codigo = 1;
		} else {
			codigo++;
		}
		return codigo;
	}

	// Metodo para btener el idtipocatálogo seleccionado//usado para el metodo
	// editar
	public AdmTipoCatalogo findOne(Integer codigotipocatalogo) {
		Query q = em.createQuery("select a from AdmTipoCatalogo a where a.idTipoCatalogo = " + codigotipocatalogo);
		return (AdmTipoCatalogo) q.getSingleResult();
	}
}

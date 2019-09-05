package com.scl.administracion.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.scl.administracion.modelo.*;

@Stateless
public class ServicioAdmSucursal {

	@PersistenceContext
	private EntityManager em;

	public void create(AdmSucursal admsucursal) {
		em.persist(admsucursal);
	}

	public void delete(AdmSucursal admsucursal) {
		em.remove(em.merge(admsucursal));
	}

	public void update(AdmSucursal admsucursal) {

		em.merge(admsucursal);
	}

	// busca el id sucursal donde sea igual al id sucursal
	@SuppressWarnings("unchecked")
	public List<AdmSucursal> rolesAsignados(AdmSucursal us) {
		Query q = em.createQuery("select c from AdmSucursal c where c.idsucursal = " + us.getIdSucursal());
		return q.getResultList();
	}

	// Selecciona todo de la tabla AdmSucursal
	@SuppressWarnings("unchecked")
	public List<AdmSucursal> findAll() {
		Query q = em.createQuery("select c from AdmSucursal c ");
		return q.getResultList();
	}

	// Selecciona el pk mas alto y le suma uno
	public Integer getPK() {
		Integer codigo = 0;
		Query q = em.createQuery("select max(id) from AdmSucursal ");
		codigo = (Integer) q.getSingleResult();
		if (codigo == null) {
			codigo = 1;
		} else {
			codigo++;
		}
		return codigo;
	}

	// Busca el rol que pertenece al codigo sucursal  seleccionado
	/*public AdmSucursal findOne(Integer codigosucursal) {
		Query q = em.createQuery("select c from AdmSucursal c where c.idrolmenu = " + codigosucursal);
		return (AdmSucursal) q.getSingleResult();
	}*/
	public AdmSucursal findOne(Integer codigosucursal) {
		Query q = em.createQuery("select c from AdmSucursal c where c.idsucursal = " + codigosucursal);
		return (AdmSucursal) q.getSingleResult();
	}
}

package com.awesomeware.shipping.environment.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.awesomeware.shipping.environment.model.AreaType;
import com.awesomeware.shipping.environment.persistence.AreaTypeJPA;

@Stateless
public class AreaTypeDAO implements AreaTypeDAOLocal {
	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(String type) {
		AreaTypeJPA jpa = new AreaTypeJPA();
		jpa.setName(type);
		em.persist(jpa);
	}

	@Override
	public void delete(String type) {
		AreaTypeJPA jpa = em.find(AreaTypeJPA.class, type);
		if (jpa != null) {
			em.remove(jpa);
		}
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.dao.AreaTypeDAOLocal#count(com.awesomeware.shipping.environment.model.AreaType)
	 */
	@Override
	public long count(AreaType type) {
		TypedQuery<Long> query = em.createNamedQuery("count-area-type", Long.class);
		query.setParameter("type", em.find(AreaTypeJPA.class, type.toString()));
		return query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.dao.AreaTypeDAOLocal#getAll()
	 */
	@Override
	public List<AreaType> getAll() {
		TypedQuery<AreaTypeJPA> query = em.createNamedQuery("all-area-types", AreaTypeJPA.class);
		List<AreaType> results = new ArrayList<>();
		for (AreaTypeJPA jpa : query.getResultList()) {
			results.add(new AreaType(jpa.getName()));
		}
		return results;
	}
}

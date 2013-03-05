package com.port7.environment.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.persistence.PortJPA;

@Stateless
public class PortDAO implements PortDAOLocal {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<PortJPA> searchByNameOrAlias(String term) {
		final TypedQuery<PortJPA> query = em.createNamedQuery("port-search-by-name", PortJPA.class);
		query.setParameter("term", term + "%");
		return query.getResultList();
	}
}

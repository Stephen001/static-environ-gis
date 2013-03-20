package com.port7.environment.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.model.Area;
import com.port7.environment.persistence.AreaAliasJPA;
import com.port7.environment.persistence.AreaJPA;
import com.port7.environment.persistence.AreaTypeJPA;

@Stateless
public class AreaDAO implements AreaDAOLocal {
	@PersistenceContext
	private EntityManager em;

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.AreaDAOLocal#addAlias(java.lang.String, com.port7.environment.persistence.AreaJPA)
	 */
	@Override
	public void addAlias(String alias, AreaJPA area) {
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("existing-alias-for-area", AreaAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("area", area);
		try {
			query.getSingleResult();
		} catch (NoResultException e) {
			AreaAliasJPA aliasJPA = new AreaAliasJPA();
			aliasJPA.setArea(area);
			aliasJPA.setName(alias);
			em.persist(aliasJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.AreaDAOLocal#removeAlias(java.lang.String, com.port7.environment.persistence.AreaJPA)
	 */
	@Override
	public void removeAlias(String alias, AreaJPA area) {
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("existing-alias-for-area", AreaAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("area", area);
		try {
			AreaAliasJPA result = query.getSingleResult();
			em.remove(result);
		} catch (NoResultException e) {}
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.dao.AreaDAOLocal#updateMetadata(com.port7.environment.model.Area)
	 */
	@Override
	public void updateMetadata(String oldName, Area area) {
		TypedQuery<AreaJPA> query = em.createNamedQuery("area-by-name", AreaJPA.class);
		query.setParameter("englishName", oldName);
		AreaJPA c;
		boolean needsPersist = false;
		try {
			c = query.getSingleResult();
		} catch (NoResultException e) {
			c = new AreaJPA();
			needsPersist = true;
		}
		c.setLandMassShape(area.getGeometry());
		c.setEnglishName(area.getEnglishName());
		c.setType(em.find(AreaTypeJPA.class, area.getType().toString()));
		if (needsPersist) {
			em.persist(c);
		}
	}

	@Override
	public void delete(AreaJPA byName) {
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("aliases-from-area", AreaAliasJPA.class);
		query.setParameter("area", byName);
		for (AreaAliasJPA alias : query.getResultList()) {
			em.remove(alias);
		}
		em.remove(byName);
	}

	@Override
	public List<AreaJPA> searchByNameOrAlias(String term) {
		final TypedQuery<AreaJPA> query = em.createNamedQuery("area-search-by-name", AreaJPA.class);
		query.setParameter("term", term + "%");
		return query.getResultList();
	}

	@Override
	public List<String> getAliases(AreaJPA area) {
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("aliases-from-area", AreaAliasJPA.class);
		query.setParameter("area", area);
		List<String> results = new ArrayList<>();
		for (AreaAliasJPA alias : query.getResultList()) {
			results.add(alias.getName());
		}
		return results;
	}
}

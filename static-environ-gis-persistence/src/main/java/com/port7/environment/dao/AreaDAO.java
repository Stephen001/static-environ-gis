package com.port7.environment.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.model.Area;
import com.port7.environment.model.event.AreaDataChangeEvent;
import com.port7.environment.model.event.DataChangeType;
import com.port7.environment.persistence.AreaAliasJPA;
import com.port7.environment.persistence.AreaJPA;
import com.port7.environment.persistence.AreaTypeJPA;

@Stateless
public class AreaDAO implements AreaDAOLocal {
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	Event<AreaDataChangeEvent> event;

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.AreaDAOLocal#addAlias(java.lang.String, com.port7.environment.persistence.AreaJPA)
	 */
	@Override
	public boolean addAlias(String alias, AreaJPA area) {
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("existing-alias-for-area", AreaAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("area", area);
		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {
			AreaAliasJPA aliasJPA = new AreaAliasJPA();
			aliasJPA.setArea(area);
			aliasJPA.setName(alias);
			em.persist(aliasJPA);
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.AreaDAOLocal#removeAlias(java.lang.String, com.port7.environment.persistence.AreaJPA)
	 */
	@Override
	public boolean removeAlias(String alias, AreaJPA area) {
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("existing-alias-for-area", AreaAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("area", area);
		try {
			AreaAliasJPA result = query.getSingleResult();
			em.remove(result);
			return true;
		} catch (NoResultException e) {
			return false;
		}
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
			event.fire(new AreaDataChangeEvent(oldName, area, DataChangeType.ADD_ENTITY));
		} else {
			TypedQuery<AreaAliasJPA> aliasQuery = em.createNamedQuery("aliases-from-area", AreaAliasJPA.class);
			aliasQuery.setParameter("area", c);
			for (AreaAliasJPA alias : aliasQuery.getResultList()) {
				event.fire(new AreaDataChangeEvent(alias.getName(), area, DataChangeType.UPDATE_ENTITY));
			}
			if (area.getEnglishName().equals(oldName)) {
				event.fire(new AreaDataChangeEvent(area.getEnglishName(), area, DataChangeType.UPDATE_ENTITY));
			} else {
				event.fire(new AreaDataChangeEvent(area.getEnglishName(), area, DataChangeType.ADD_ENTITY));
				event.fire(new AreaDataChangeEvent(oldName, area, DataChangeType.REMOVE_ENTITY));
			}
		}
	}

	@Override
	public List<String> delete(AreaJPA byName) {
		List<String> removedAliases = new ArrayList<>();
		TypedQuery<AreaAliasJPA> query = em.createNamedQuery("aliases-from-area", AreaAliasJPA.class);
		query.setParameter("area", byName);
		for (AreaAliasJPA alias : query.getResultList()) {
			removedAliases.add(alias.getName());
			em.remove(alias);
		}
		em.remove(byName);
		return removedAliases;
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

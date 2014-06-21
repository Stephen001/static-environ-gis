package com.awesomeware.shipping.environment.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.awesomeware.shipping.environment.model.Port;
import com.awesomeware.shipping.environment.model.event.DataChangeType;
import com.awesomeware.shipping.environment.model.event.PortDataChangeEvent;
import com.awesomeware.shipping.environment.persistence.PortAliasJPA;
import com.awesomeware.shipping.environment.persistence.PortJPA;

@Stateless
public class PortDAO implements PortDAOLocal {
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	Event<PortDataChangeEvent> event;
	
	@Override
	public List<PortJPA> searchByNameOrAlias(String term) {
		final TypedQuery<PortJPA> query = em.createNamedQuery("port-search-by-name", PortJPA.class);
		query.setParameter("term", term + "%");
		return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.dao.PortDAOLocal#addAlias(java.lang.String, com.awesomeware.shipping.environment.persistence.PortJPA)
	 */
	@Override
	public boolean addAlias(String alias, PortJPA port) {
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("existing-alias-for-port", PortAliasJPA.class);
		query.setParameter("name", alias.toUpperCase(Locale.ENGLISH));
		query.setParameter("port", port);
		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {
			PortAliasJPA aliasJPA = new PortAliasJPA();
			aliasJPA.setPort(port);
			aliasJPA.setName(alias);
			em.persist(aliasJPA);
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.dao.PortDAOLocal#removeAlias(java.lang.String, com.awesomeware.shipping.environment.persistence.PortJPA)
	 */
	@Override
	public boolean removeAlias(String alias, PortJPA port) {
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("existing-alias-for-port", PortAliasJPA.class);
		query.setParameter("name", alias.toUpperCase(Locale.ENGLISH));
		query.setParameter("port", port);
		try {
			PortAliasJPA result = query.getSingleResult();
			em.remove(result);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.dao.PortDAOLocal#updateMetadata(com.awesomeware.shipping.environment.model.Port)
	 */
	@Override
	public void updateMetadata(String oldName, Port port) {
		TypedQuery<PortJPA> query = em.createNamedQuery("port-by-name", PortJPA.class);
		query.setParameter("englishName", oldName.toUpperCase(Locale.ENGLISH));
		PortJPA c;
		boolean needsPersist = false;
		try {
			c = query.getSingleResult();
		} catch (NoResultException e) {
			c = new PortJPA();
			needsPersist = true;
		}
		c.setLocation(port.getGeometry());
		c.setEnglishName(port.getEnglishName());
		if (needsPersist) {
			em.persist(c);
			event.fire(new PortDataChangeEvent(oldName, port, DataChangeType.ADD_ENTITY));
		} else {
			TypedQuery<PortAliasJPA> aliasQuery = em.createNamedQuery("aliases-from-port", PortAliasJPA.class);
			aliasQuery.setParameter("port", c);
			for (PortAliasJPA alias : aliasQuery.getResultList()) {
				event.fire(new PortDataChangeEvent(alias.getName(), port, DataChangeType.UPDATE_ENTITY));
			}
			if (port.getEnglishName().equals(oldName)) {
				event.fire(new PortDataChangeEvent(port.getEnglishName(), port, DataChangeType.UPDATE_ENTITY));
			} else {
				event.fire(new PortDataChangeEvent(port.getEnglishName(), port, DataChangeType.ADD_ENTITY));
				event.fire(new PortDataChangeEvent(oldName, port, DataChangeType.REMOVE_ENTITY));
			}
		}
	}

	@Override
	public List<String> delete(PortJPA byName) {
		List<String> removedAliases = new ArrayList<>();
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("aliases-from-port", PortAliasJPA.class);
		query.setParameter("port", byName);
		try {
			for (PortAliasJPA alias : query.getResultList()) {
				em.remove(alias);
				removedAliases.add(alias.getName());
			}
		} catch (NoResultException e) {}
		em.remove(byName);
		return removedAliases;
	}

	@Override
	public List<String> getAliases(PortJPA port) {
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("aliases-from-port", PortAliasJPA.class);
		query.setParameter("port", port);
		List<String> results = new ArrayList<>();
		for (PortAliasJPA alias : query.getResultList()) {
			results.add(alias.getName());
		}
		return results;
	}
}

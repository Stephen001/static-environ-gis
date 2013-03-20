package com.port7.environment.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.model.Port;
import com.port7.environment.persistence.PortAliasJPA;
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
	
	/* (non-Javadoc)
	 * @see com.port7.environment.dao.PortDAOLocal#addAlias(java.lang.String, com.port7.environment.persistence.PortJPA)
	 */
	@Override
	public void addAlias(String alias, PortJPA port) {
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("existing-alias-for-port", PortAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("port", port);
		PortAliasJPA result = query.getSingleResult();		
		if (result == null) {
			PortAliasJPA aliasJPA = new PortAliasJPA();
			aliasJPA.setPort(port);
			aliasJPA.setName(alias);
			em.persist(aliasJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.PortDAOLocal#removeAlias(java.lang.String, com.port7.environment.persistence.PortJPA)
	 */
	@Override
	public void removeAlias(String alias, PortJPA port) {
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("existing-alias-for-port", PortAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("port", port);
		PortAliasJPA result = query.getSingleResult();
		if (result != null) {
			em.remove(result);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.dao.PortDAOLocal#updateMetadata(com.port7.environment.model.Port)
	 */
	@Override
	public void updateMetadata(String oldName, Port port) {
		TypedQuery<PortJPA> query = em.createNamedQuery("port-by-name", PortJPA.class);
		query.setParameter("englishName", oldName);
		PortJPA c;
		boolean needsPersist = false;
		try {
			c = query.getSingleResult();
		} catch (NoResultException e) {
			c = new PortJPA();
			needsPersist = true;
		}
		c.setLocation(port.getLocation());
		c.setEnglishName(port.getEnglishName());
		if (needsPersist) {
			em.persist(c);
		}
	}

	@Override
	public void delete(PortJPA byName) {
		TypedQuery<PortAliasJPA> query = em.createNamedQuery("aliases-from-port", PortAliasJPA.class);
		query.setParameter("port", byName);
		for (PortAliasJPA alias : query.getResultList()) {
			em.remove(alias);
		}
		em.remove(byName);
	}
}

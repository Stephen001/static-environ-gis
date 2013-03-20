package com.port7.environment.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
		AreaAliasJPA result = query.getSingleResult();		
		if (result == null) {
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
		AreaAliasJPA result = query.getSingleResult();
		if (result != null) {
			em.remove(result);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.dao.AreaDAOLocal#updateMetadata(com.port7.environment.model.Area)
	 */
	@Override
	public void updateMetadata(String oldName, Area area) {
		TypedQuery<AreaJPA> query = em.createNamedQuery("area-by-name", AreaJPA.class);
		query.setParameter("englishName", oldName);
		AreaJPA c = query.getSingleResult();
		c.setLandMassShape(area.getGeometry());
		c.setEnglishName(area.getEnglishName());
		c.setType(em.find(AreaTypeJPA.class, area.getType()));
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
}

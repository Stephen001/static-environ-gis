package com.port7.environment.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.model.Country;
import com.port7.environment.persistence.CountryAliasJPA;
import com.port7.environment.persistence.CountryJPA;

@Stateless
public class CountryDAO implements CountryDAOLocal {
	@PersistenceContext
	private EntityManager em;

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.CountryDAOLocal#addAlias(java.lang.String, com.port7.environment.persistence.CountryJPA)
	 */
	@Override
	public void addAlias(String alias, CountryJPA country) {
		TypedQuery<CountryAliasJPA> query = em.createNamedQuery("existing-alias-for-country", CountryAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("country", country);
		CountryAliasJPA result = query.getSingleResult();		
		if (result == null) {
			CountryAliasJPA aliasJPA = new CountryAliasJPA();
			aliasJPA.setCountry(country);
			aliasJPA.setName(alias);
			em.persist(aliasJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.dao.CountryDAOLocal#removeAlias(java.lang.String, com.port7.environment.persistence.CountryJPA)
	 */
	@Override
	public void removeAlias(String alias, CountryJPA country) {
		TypedQuery<CountryAliasJPA> query = em.createNamedQuery("existing-alias-for-country", CountryAliasJPA.class);
		query.setParameter("name", alias);
		query.setParameter("country", country);
		CountryAliasJPA result = query.getSingleResult();
		if (result != null) {
			em.remove(result);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.dao.CountryDAOLocal#updateMetadata(com.port7.environment.model.Country)
	 */
	@Override
	public void updateMetadata(String oldName, Country country) {
		TypedQuery<CountryJPA> query = em.createNamedQuery("country-by-name", CountryJPA.class);
		query.setParameter("englishName", oldName);
		CountryJPA c = query.getSingleResult();
		c.setLandMassShape(country.getLandMassShape());
		c.setThreeCountryCode(country.getThreeCharCountryCode());
		c.setTwoCountryCode(country.getTwoCharCountryCode());
		c.setEnglishName(country.getEnglishName());
	}

	@Override
	public void delete(CountryJPA byName) {
		TypedQuery<CountryAliasJPA> query = em.createNamedQuery("aliases-from-country", CountryAliasJPA.class);
		query.setParameter("country", byName);
		for (CountryAliasJPA alias : query.getResultList()) {
			em.remove(alias);
		}
		em.remove(byName);
	}
}

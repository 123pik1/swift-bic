package pik.DB;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import pik.DB.Entities.BankBranch;
import pik.DB.Entities.Country;

public class Queries {
	private EntityManager entityManager;
	Queries(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public BankBranch getBranchBySwiftCode(String swiftCode)
	{
		TypedQuery<BankBranch> query = entityManager.createQuery("SELECT b FROM BankBranch b WHERE b.swiftCode = :swiftCode", BankBranch.class);
		query.setParameter("swiftCode", swiftCode);
		BankBranch branch = query.getSingleResult();
		System.out.println("gets branch by BIC");
		return branch;
	}

	public List<BankBranch> getSubBranches(String globalCode)
	{
		TypedQuery<BankBranch> query = entityManager.createQuery("SELECT b FROM BankBranch b WHERE SUBSTRING(b.swiftCode, 1, 8) = :globalCode", BankBranch.class);
		query.setParameter("globalCode", globalCode);
		List<BankBranch> res = query.getResultList();
		System.out.println("gets subBranches");
		return res;
	}

	public List<BankBranch> getBranchesFromCountry(Country country)
	{
		TypedQuery<BankBranch> query = entityManager.createQuery("SELECT b FROM BankBranch b WHERE b.country.ISO2 = :ISO2", BankBranch.class);
		query.setParameter("ISO2", country.getISO2());
		List<BankBranch> res = query.getResultList();
		System.out.println("gets from country");
		return res;
	}
	public Country getCountryByISO2 (String ISO2Code)
	{
		TypedQuery<Country> query = entityManager.createQuery("SELECT c from Country c where c.ISO2 = :ISO2", Country.class);
		query.setParameter("ISO2", ISO2Code);
		return query.getSingleResult();
	}
}

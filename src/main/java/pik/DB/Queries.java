package pik.DB;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import pik.DB.Entities.BankBranch;

public class Queries {
	private EntityManager entityManager;
	Queries(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public BankBranch getBranchBySwiftCode(String swiftCode)
	{
		TypedQuery<BankBranch> query = entityManager.createQuery("SELECT b FROM Branch b WHERE b.swiftCode = :swiftCode", BankBranch.class);
		query.setParameter("swiftCode", swiftCode);
		BankBranch branch = query.getSingleResult();
		System.out.println("gets branch by BIC");
		return branch;
	}

	
}

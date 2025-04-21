package pik.DB;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.cfg.Configuration;

import lombok.Getter;
import pik.DB.Entities.Storable;

public class Handler {

	@Getter
	private EntityManagerFactory entityManagerFactory;

	@Getter
	private EntityManager entityManager;

	public Handler()
	{
		start();
		System.out.println("blub");
	}
	public void  start()
	{
		try
		{
			entityManagerFactory = Persistence.createEntityManagerFactory("swift-bic");
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Throwable ex)
		{
			throw new ExceptionInInitializerError();
		}
	}





	public void addObjects(List<Storable> entities)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		try 
		{
			transaction.begin();
			for (Storable storable : entities) {
				entityManager.persist(storable);
			}
			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void addObject(Storable entity)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		}
	}


	public void close()
	{
		entityManager.close();
		entityManagerFactory.close();
	}
}

package pik.DB;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.cfg.Configuration;

import lombok.Getter;
import pik.DB.Entities.BankBranch;
import pik.DB.Entities.Storable;
import pik.Exceptions.ObjectAlreadyInDB;

public class Handler {

	@Getter
	private EntityManagerFactory entityManagerFactory;

	@Getter
	private EntityManager entityManager;

	@Getter
	private org.h2.tools.Server webServer;

	public Queries queries;

	public Handler()
	{
		start();
	}
	public void  start()
	{
		try{
            webServer = org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        }catch(Exception e){
            e.printStackTrace();
        }
		try
		{
			entityManagerFactory = Persistence.createEntityManagerFactory("swift-bic");
			entityManager = entityManagerFactory.createEntityManager();
			queries = new Queries(entityManager);
		} catch (Throwable ex)
		{
			
			System.err.println("issue with initialization " + ex.getMessage());
			throw new ExceptionInInitializerError();
		}
	}





	public void addObjects(Set<Storable> entities)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		try 
		{
			transaction.begin();
			for (Storable storable : entities) {
				System.out.println(storable.toString());
				entityManager.persist(storable);
			}
			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction.isActive()) {
				transaction.rollback();
			}
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
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new ObjectAlreadyInDB();
		}
	}


	public void close()
	{
		webServer.stop();
		entityManager.close();
		entityManagerFactory.close();
	}

	public void deleteObject(Storable entity)
	{
		System.out.println("in deletion");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
}

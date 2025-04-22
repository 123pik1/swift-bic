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
	}
	public void  start()
	{
		try{
            org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        }catch(Exception e){
            e.printStackTrace();
        }
		try
		{
			entityManagerFactory = Persistence.createEntityManagerFactory("swift-bic");
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Throwable ex)
		{
			System.err.println("wth");
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
				System.out.println(storable.toString());
				entityManager.persist(storable);
			}
			transaction.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addObject(Storable entity)
	{
		System.out.println("adding object");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void close()
	{
		entityManager.close();
		entityManagerFactory.close();
	}
}

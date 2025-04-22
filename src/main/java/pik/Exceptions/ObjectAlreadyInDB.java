package pik.Exceptions;

public class ObjectAlreadyInDB extends RuntimeException{
	public ObjectAlreadyInDB()
	{
		System.err.println("Object is already in database");
	}
}

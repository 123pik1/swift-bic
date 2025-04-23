package pik.Exceptions;

public class ObjectAlreadyInDBException extends RuntimeException{
	public ObjectAlreadyInDBException()
	{
		System.err.println("Object is already in database");
	}
}

package pik.Exceptions;

public class NotDeletedObjectException extends RuntimeException{
	public NotDeletedObjectException()
	{
		System.out.println("You can't delete object from database");
	}
}

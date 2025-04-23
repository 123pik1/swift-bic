package pik.Exceptions;

public class UnrecognizedCommandException extends RuntimeException{
	public UnrecognizedCommandException()
	{
		System.out.println("Unrecognized command");
	}
}

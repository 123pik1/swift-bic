package pik.Exceptions;

public class NoSwiftCodeInDBException extends RuntimeException{
	public NoSwiftCodeInDBException()
	{
		System.err.println("SWIFT code has not been found in database");
	}
}

package pik.Exceptions;

public class NoCountryInDbException extends RuntimeException{
	public NoCountryInDbException()
	{
		System.out.println("No country found in database");
	}
}

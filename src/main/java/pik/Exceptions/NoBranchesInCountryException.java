package pik.Exceptions;

public class NoBranchesInCountryException extends RuntimeException{
	public NoBranchesInCountryException()
	{
		System.out.println("There are no branches in this country");
	}
}

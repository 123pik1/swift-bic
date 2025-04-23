package pik.DB_Parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pik.DB.Entities.BankBranch;
import pik.DB.Entities.Country;

public abstract class Parser {
	protected String filePath;
	
	public Parser(String filePath)
	{
		this.filePath = filePath;
	}

	public Set<BankBranch> parseToBankBranch()
	{
		Set<BankBranch> result = new HashSet<BankBranch>();
		List<String[]> fromFile = parseFile(); //gets values from file
		for (String[] record : fromFile)
		{
			result.add(new BankBranch(record[4], record[3], new Country(record[0], record[6]),record[1])); //creates branch
		}
		
		return result;
	}

	public Set<Country> parseToCountries()
	{
		Set<Country> result = new HashSet<Country>();
		List<String[]> fromFile = parseFile();
		for (String[] record : fromFile)
		{
			Country country = new Country(record[0], record[6]); //create country from ISO2 code and country name
			if (!result.contains(country)) {
				result.add(country);
			}
		}
		return result;
	}


	protected abstract List<String[]> parseFile();
}

package pik.DB_Parser;

import java.util.ArrayList;
import java.util.List;

import pik.DB.Entities.BankBranch;

public abstract class Parser {
	protected String filePath;
	
	public Parser(String filePath)
	{
		this.filePath = filePath;
	}

	public List<BankBranch> parseToBankBranch()
	{
		System.out.println(filePath);
		List<BankBranch> result = new ArrayList<BankBranch>();
		List<String[]> fromFile = parseFile(); //gets values from file
		for (String[] record : fromFile)
		{
			result.add(new BankBranch(record));
		}
		
		return result;
	}


	protected abstract List<String[]> parseFile();
}

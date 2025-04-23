package pik.DB_Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TsvParser extends Parser{


	public TsvParser (String filePath)
	{
		super(filePath);
	}

	protected List<String[]> parseFile()
	{
		List<String[]> records = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
		{
			String line;
			line = reader.readLine();
			while ((line = reader.readLine())!=null)
			{
				String[] values = line.split("	"); //splits by tabulators (.tsv file)
				records.add(values);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return records;
	}



}

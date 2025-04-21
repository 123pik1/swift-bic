package pik.DB_Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvParser extends Parser{


	public CsvParser (String filePath)
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
			System.out.println(line);
			while ((line = reader.readLine())!=null)
			{
				String[] values = line.split(",");
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

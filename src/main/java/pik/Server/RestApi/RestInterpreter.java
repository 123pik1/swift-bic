package pik.Server.RestApi;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pik.DB.Entities.BankBranch;
import pik.DB.Entities.Country;
import pik.Exceptions.NoBranchesInCountryException;
import pik.Exceptions.NoCountryInDbException;
import pik.Exceptions.NoSwiftCodeInDBException;
import pik.Exceptions.NotDeletedObjectException;
import pik.Exceptions.ObjectAlreadyInDBException;
import pik.Server.Server;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@SpringBootApplication
@RestController
@RequestMapping("/v1/swift-codes")
public class RestInterpreter {
	

	//  ==== Spring sphere ====
	@GetMapping("/{swiftCode}")
	public ResponseEntity<String> getBySwiftCode(@PathVariable String swiftCode) {
		try{
			BankBranch branch = Server.getDbHandler().queries.getBranchBySwiftCode(swiftCode);
			return new ResponseEntity<>((branch.toJsonString(false)), HttpStatus.OK);
		}
		catch (NoSwiftCodeInDBException e)
		{
			return new ResponseEntity<>("There was no swiftCode found in database", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>("There was a problem with request", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/country/{ISO2Code}")
	public ResponseEntity<String> getByIso2Code(@PathVariable String ISO2Code) {
		try{
			String response = "{";
		Country country = Server.getDbHandler().queries.getCountryByISO2(ISO2Code);
		List<BankBranch> branches = Server.getDbHandler().queries.getBranchesFromCountry(country);
		response += "\"countryISO2\": \""+country.getISO2()+"\"";
		response += "\"countryName\": \""+country.getName()+"\"";
		response += "\"swiftCodes\": [";
		boolean firstIter = true;
		for (BankBranch bankBranch : branches) {
			if (!firstIter)
			{
				response+=",";
				firstIter=!firstIter;
			}
			response += bankBranch.toJsonString(true, 0);
		}
		response+="]}";
		return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch (NoCountryInDbException e)
		{
			return new ResponseEntity<>("No country found in database by this ISO2 code", HttpStatus.BAD_REQUEST);
		}
		catch (NoBranchesInCountryException e)
		{
			return new ResponseEntity<>("No branches found in this country", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>("There was a problem with request", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> insertBranch(@RequestBody String entity) {
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(entity);

			String address = root.path("address").asText().toUpperCase();
            String bankName = root.path("bankName").asText().toUpperCase();
            String countryISO2 = root.path("countryISO2").asText().toUpperCase();
            String countryName = root.path("countryName").asText().toUpperCase();
            boolean isHeadquarter = root.path("isHeadquarter").asBoolean(); //BankBranch constructor checks it
            String swiftCode = root.path("swiftCode").asText().toUpperCase();
			Country country = new Country(countryISO2, countryName);
			BankBranch branch = new BankBranch(address, bankName, country , swiftCode);
			System.out.println("after processing");
			try 
			{
				Server.getDbHandler().queries.getCountryByISO2(countryISO2); //check if there is country in DB
			}
			catch (NoCountryInDbException e)
			{
				System.out.println("country not found");
				Server.getDbHandler().addObject(country);
			}
			System.out.println("after adding country");
			System.out.println("Received BankBranch: " + branch);
			Server.getDbHandler().addObject(branch);
			return new ResponseEntity<>("{ \"message\":\"BankBranch received and processed\"}", HttpStatus.OK);
		}
		catch (ObjectAlreadyInDBException e)
		{
            return new ResponseEntity<>("{ \"message\":\"Object is already in database\"}", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			System.err.println("Error processing JSON: " + e.getMessage());
            return new ResponseEntity<>("{ \"message\":\"Error processing JSON\"}", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{swiftCode}")
	public ResponseEntity<String> deleteBranch(@PathVariable String swiftCode)
	{
		try {
			BankBranch branch = Server.getDbHandler().queries.getBranchBySwiftCode(swiftCode);
			Server.getDbHandler().deleteObject(branch);
			return new ResponseEntity<>("{ \"message\":\"Branch deleted\"}", HttpStatus.OK);
		} catch (NoSwiftCodeInDBException e) {
			return new ResponseEntity<>("{ \"message\":\"There wasn't any branch with this swiftcode\"}", HttpStatus.BAD_REQUEST);
		}
		catch (NotDeletedObjectException e)
		{
			return new ResponseEntity<>("{ \"message\":\"You can't delete object from database\"}", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>("{ \"message\":\"There was a problem with request\"}", HttpStatus.BAD_REQUEST);
		}
	}
}

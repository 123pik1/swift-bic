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
import pik.Exceptions.NoSwiftCodeInDBException;
import pik.Server.Server;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@SpringBootApplication
@RestController
@RequestMapping("/v1/swift-codes")
public class RestInterpreter {
	

	//  ==== Spring sphere ====
	@GetMapping("/{swiftCode}")
	public String getBySwiftCode(@PathVariable String swiftCode) {
		BankBranch branch = Server.getDbHandler().queries.getBranchBySwiftCode(swiftCode);
		return branch.toJsonString(false);
	}

	@GetMapping("/country/{ISO2Code}")
	public String getByIso2Code(@PathVariable String ISO2Code) {
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
		response+="]\n}";
		return response;
	}

	@PostMapping("/")
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
			if (Server.getDbHandler().queries.getCountryByISO2(countryISO2)==null)
			{
				System.out.println("country not found");
				Server.getDbHandler().addObject(country);
			}
			System.out.println("after adding country");
			System.out.println("Received BankBranch: " + branch);
			Server.getDbHandler().addObject(branch);
			return new ResponseEntity<>("BankBranch received and processed", HttpStatus.OK);
		}
		catch (Exception e)
		{
			System.err.println("Error processing JSON: " + e.getMessage());
            return new ResponseEntity<>("Error processing JSON", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{swiftCode}")
	public ResponseEntity<String> deleteBranch(@PathVariable String swiftCode)
	{
		try {
			BankBranch branch = Server.getDbHandler().queries.getBranchBySwiftCode(swiftCode);
			Server.getDbHandler().deleteObject(branch);
			return new ResponseEntity<>("Branch deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Problem with deleting branch", HttpStatus.BAD_REQUEST);
		}
	}
}

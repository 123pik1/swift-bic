package pik.Server.RestApi;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pik.DB.Entities.BankBranch;
import pik.DB.Entities.Country;
import pik.Server.Server;

@SpringBootApplication
@RestController
@RequestMapping("/v1/swift-codes")
public class RestInterpreter {
	

	//  ==== Spring sphere ====
	@GetMapping("/{swiftCode}")
	public String getBySwiftCode(@PathVariable String swiftCode) {
		return Server.getDbHandler().queries.getBranchBySwiftCode(swiftCode).toJsonString(false);
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
}

package pik.Server.RestApi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public String getByIso2Code(@RequestParam String ISO2Code) {
		return new String();
	}
}

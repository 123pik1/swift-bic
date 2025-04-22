package pik.Server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import pik.DB.Handler;
import pik.DB.Entities.Storable;
import pik.DB_Parser.CsvParser;
import pik.DB_Parser.TsvParser;
import pik.Listener.CommandListener;
import pik.Listener.Commanded;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/v1/swift-codes")
public class Server implements Commanded{


	@Setter
    private boolean running = true;


	private Handler dbHandler;

	public Server() {}

	public void startWithParser()
	{
		TsvParser parser = new TsvParser("src/main/resources/Interns_2025_SWIFT_CODES - Sheet1.tsv");
        dbHandler = new Handler();
        dbHandler.start();
		parser.parseToCountries();
		dbHandler.addObjects((Set<Storable>)(Set<?>)parser.parseToCountries());
        dbHandler.addObjects((Set<Storable>)(Set<?>)parser.parseToBankBranch()); //to Storable for adding objects in database
        System.out.println("inserted");
		start();
	}

	public void start()
	{
        System.out.println("in start");
		CommandListener commandListener = new CommandListener(this);
        Thread commandListenerThread = new Thread(commandListener);
        System.out.println("creation of new thread");
        commandListenerThread.setDaemon(true);
        commandListenerThread.start();
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finishing");
        commandListenerThread.interrupt();
        dbHandler.close();
	}
	
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startWithParser();
	}

	public void serveCommand(String command)
	{}



	//  ==== Spring sphere ====
	@GetMapping("/{swiftCode}")
	public String getBySwiftCode(@RequestParam String swiftCode) {
		return dbHandler.queries.getBranchBySwiftCode(swiftCode).toJsonString(false);
	}

	@GetMapping("/country/{ISO2Code}")
	public String getByIso2Code(@RequestParam String ISO2Code) {
		return new String();
	}
	
}

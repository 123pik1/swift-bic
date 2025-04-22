package pik.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import pik.DB.Handler;
import pik.DB.Entities.Storable;

import pik.DB_Parser.TsvParser;
import pik.Listener.CommandListener;
import pik.Listener.Commanded;
import pik.Server.RestApi.RestInterpreter;

import java.util.Set;



public class Server implements Commanded{


	@Setter
    private boolean running = true;

	@Getter
	private static Handler dbHandler;

	private RestInterpreter interpreter;


	private ApplicationContext appContext;

	public Server() {}

	public void startWithParser(String[] args)
	{
		TsvParser parser = new TsvParser("src/main/resources/Interns_2025_SWIFT_CODES - Sheet1.tsv");
        dbHandler = new Handler();
        dbHandler.start();
		parser.parseToCountries();
		dbHandler.addObjects((Set<Storable>)(Set<?>)parser.parseToCountries());
        dbHandler.addObjects((Set<Storable>)(Set<?>)parser.parseToBankBranch()); //to Storable for adding objects in database
        System.out.println("inserted");
		start(args);
	}

	public void start(String[] args)
	{
        System.out.println("in start");
		CommandListener commandListener = new CommandListener(this);
        Thread commandListenerThread = new Thread(commandListener);
        System.out.println("creation of new thread");
        commandListenerThread.setDaemon(true);
        commandListenerThread.start();
		appContext = SpringApplication.run(RestInterpreter.class, args);
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        shutdown();
	}
	
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startWithParser(args);
	}

	public void serveCommand(String command)
	{}


	private void shutdown()
	{
		System.out.println("finishing");
		int exitCode = SpringApplication.exit(appContext, ()->0);
        dbHandler.close();
		System.exit(exitCode);
	}
	
	
}

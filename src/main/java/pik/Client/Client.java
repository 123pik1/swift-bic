package pik.Client;

import lombok.Setter;
import pik.Exceptions.UnrecognizedCommandException;
import pik.Listener.Commanded;

public class Client implements Commanded {

	@Setter
	private boolean running;

	private String address;

	public Client(String address)
	{
		this.running = true;
		this.address = address;
	}

	@Override
	public void serveCommand(String command) {
		String[] splittedCommand = command.split(" ");
		switch (splittedCommand[0]) {
			case "1":
				
				break;
		
			default:
				throw new UnrecognizedCommandException();
		}
	}
	
}

package pik.Client;

import lombok.Setter;
import pik.Listener.Commanded;

public class Client implements Commanded {

	@Setter
	private boolean running;

	

	@Override
	public void serveCommand(String command) {
		switch (command) {
			case "1":
				
				break;
		
			default:
				break;
		}
	}
	
}

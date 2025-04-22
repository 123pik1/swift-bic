package pik.Client;

import lombok.Setter;
import pik.Listener.Commanded;

public class Client implements Commanded {

	@Setter
	private boolean running;

	

	@Override
	public void serveCommand(String command) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'serveCommand'");
	}
	
}

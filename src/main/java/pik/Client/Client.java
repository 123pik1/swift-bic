package pik.Client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;

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
				sendGet_getBranchBySwiftCode(splittedCommand[1]);
				break;
		
			default:
				throw new UnrecognizedCommandException();
		}
	}
	

	private void sendGet_getBranchBySwiftCode(String swiftCode)
	{
		//TODO split it to more functions - connection itself can be in another function
		try {
			String actualAddress = this.address+"/"+swiftCode;
			URL url = new URL(actualAddress);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

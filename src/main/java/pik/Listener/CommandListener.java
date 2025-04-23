package pik.Listener;

import java.util.Scanner;


public class CommandListener implements Runnable{
	
	Commanded commanded;

	public CommandListener(Commanded commanded)
	{
		this.commanded = commanded;
	}

	/*
	Command structure:
	<command> <arguments>
	*/

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("commandListener started");
		try {
			while (true)
			{
				if (System.in.available() > 0 && scanner.hasNextLine())
				{
					String command = scanner.nextLine();
					System.out.println("Command received: " + command);

					if (command.equalsIgnoreCase("exit"))
					{
						System.out.println("Exiting the program...");
						break;
					}
					else
						commanded.serveCommand(command);
				}
				Thread.sleep(100);
			}
			scanner.close();
			commanded.setRunning(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

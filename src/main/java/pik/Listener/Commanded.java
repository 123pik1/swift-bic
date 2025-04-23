package pik.Listener;


public interface Commanded {

	public void setRunning(boolean setter); //commanded object must have attribute running
	public void serveCommand(String command); //function to serve commands in commanded object
}

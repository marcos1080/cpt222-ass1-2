package au.edu.rmit.cpt222.model.comms;

/**
 * Static class used to facilitate changing network settings.
 * 
 * @author Mark Stuart
 */
public class Config {
	private static String HOST_IP = "127.0.0.1";
	private static int HOST_PORT = 6666;
	
	public static void setNetworkSettings(String address, String port) {
		HOST_IP = address;
		HOST_PORT = Integer.parseInt(port);
	}
	
	public static String getHostAddress() {
		return HOST_IP;
	}
	
	public static int getHostPort() {
		return HOST_PORT;
	}
}

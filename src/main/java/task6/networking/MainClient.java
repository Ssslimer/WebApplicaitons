package networking;

public class MainClient
{
	public static void main(String[] args)
	{
		String ip = "127.0.0.1";
		int port = 4444;	
		Client client = new Client();
        client.connectToServer(ip, port);
		
        client.start();        
	}	
}
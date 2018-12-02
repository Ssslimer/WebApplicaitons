package networking;

public class MainClient
{
	public static void main(String[] args)
	{
        if(args.length != 1)
        {
            System.err.println("Usage: java -jar N3_Client.jar <port number>");
            return;
        }
		
		String ip = "127.0.0.1";
		int port = 4444;	
		Client client = new Client();
        client.connectToServer(ip, port);
		
        client.loop();        
	}	
}
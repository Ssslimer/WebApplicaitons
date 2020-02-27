package pai_o1.mains;

import java.io.IOException;

import pai_o1.model.ConnectionClient;
import pai_o1.ui.ConsoleUI;
import pai_o1.ui.SimulatedClientUI;
import pai_o1.ui.UserInterface;

public class MainClient
{
	public static UserInterface ui;
	
	public static void main(String[] args)
	{    
        if(args.length != 3)
        {
            System.err.println("Usage: java -jar N3_Client.jar <port number> <client name> <mode: normal or simulated>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String clientName = args[1];
        
        ConnectionClient client = new ConnectionClient(clientName, false);
        
        if("simulated".equals(args[2])) ui = new SimulatedClientUI(client, clientName);
        else 							ui = new ConsoleUI(client, clientName);
        
		try
		{
			String[] freeTerminsData =  {"cmd.exe", "/c", "start", (clientName + ": Messages from Server"), "java", "-jar", "O1_Callback.jar", "4444", clientName};

			ProcessBuilder builder = new ProcessBuilder(freeTerminsData);
			builder.start();			
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        
        client.connectToServer(port);
		
        ui.loop();
    }
}

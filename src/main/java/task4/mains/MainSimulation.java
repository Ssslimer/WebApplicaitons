package pai_o1.mains;

import java.io.IOException;

public class MainSimulation
{
	public static void main(String[] args)
	{    
		int numberOfClients = Integer.parseInt(args[0]);
		
		try
		{
			String[] serverData =  {"cmd.exe", "/c", "start", "Time Bank Server", "java", "-jar", "O1_Server.jar", "4444"};
			String[] clientData =  {"cmd.exe", "/c", "start", " ", "java", "-jar", "O1_Client.jar", "4444", " ", "simulated"};
			
			for(int i = 0; i < serverData.length; i++) System.out.print(serverData[i]+" ");
			ProcessBuilder builder = new ProcessBuilder(serverData);
			builder.start();
			
			Thread.sleep(5000);
			
			String[] names = {"Janusz", "Grażyna", "Steve", "Bob", "Patrick", "Helena", "Bogdan", "Hans", "Gertruda", "Ivan"};
			
			for(int i = 0; i < numberOfClients; i++)
			{
				clientData[3] = (names[i] + ": Simulated Input");
				clientData[8] = names[i];
				builder = new ProcessBuilder(clientData);
				builder.start();
			}
		}
		catch(IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
    }
}

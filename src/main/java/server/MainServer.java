package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainServer
{
	public static void main(String[] args)
	{
		int port = 4444;
		
		Server server = new Server(port);
		server.start();
          	
        consoleInput();
	}
	
	private static void consoleInput()
	{
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            String line = null;
			while((line = br.readLine()) != null && line.equalsIgnoreCase("stop"))
			{
				System.exit(0);
			}
		}
        catch(IOException e)
        {
			e.printStackTrace();
		}
	}
}

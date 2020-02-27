package pai_o1.mains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pai_o1.model.TimeBank;

public class MainServer
{
	public static void main(String[] args)
	{   
        if(args.length != 1)
        {
            System.err.println("Usage: java -jar N3_Server.jar <port number>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Thread server = new Thread(() -> new TimeBank(port)); 
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

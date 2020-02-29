package task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class N3_Server
{
	public static void main(String[] args)
	{      
        if(args.length != 1)
        {
            System.err.println("Usage: java -jar N3_Server.jar <port number>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Thread connecionsMenneger = new Thread(() ->
        {
        	try(ServerSocket echoServer = new ServerSocket(port))
        	{
        		System.out.println("Server setup on port: " + echoServer.getLocalPort());
		        	
        		while(true)
        		{
        			Thread t = new ServerConnectionThread(echoServer.accept());
        			t.start();
        		}      
        	}
        	catch(IOException e)
        	{
        		System.out.println(e);
        	}
        }); 
        connecionsMenneger.start();
             
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            String line = null;
			while((line = br.readLine()) != null )
			{
				if(line.equalsIgnoreCase("stop")) System.exit(0);
			}
		}
        catch(IOException e)
        {
			e.printStackTrace();
		}
	}
}

package task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Task3_Client
{
	public static void main(String[] args)
	{      
        if(args.length != 1)
        {
            System.err.println("Usage: java -jar N3_Client.jar <port number>");
            return;
        }

        int port = Integer.parseInt(args[0]);
	
    	try(Socket clientSocket = new Socket("127.0.0.1", port))
    	{
    		System.out.println("Client setup on port: " + clientSocket.getPort());
    		
    		try(BufferedReader streamFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    		    BufferedWriter streamToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    	    	BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)))
    	    {
    	    	String s = null;
    	    	while((s = consoleReader.readLine()) != null && !s.equalsIgnoreCase("exit"))
    	    	{
    	    		long timeBefore = System.nanoTime();
    	    		System.out.print("client sent "+s);
    	    		streamToServer.write(s);     	    		
        	    	String line = streamFromServer.readLine();
    	    		long timeAfter = System.nanoTime();
    	    		
    	    		System.out.println("Echo from server: " + line);
    	    		System.out.println("PING: " + ((timeAfter-timeBefore)/1000000.0) + '\n');
    	    	}
    	    }
    		catch(IOException e)
    		{
    			System.out.println(e.getMessage());
    		}
    	}
    	catch(IOException e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
}

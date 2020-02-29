package task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerConnectionThread extends Thread
{	
	private Socket clientSocket;
	
	public ServerConnectionThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run()
	{
		try(BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream os = new PrintStream(clientSocket.getOutputStream()))
        {
			System.out.print("server before while");
    	    while(true)
    	    {
    	    	String line = is.readLine();
	    		System.out.print("client got "+line);
    	    	if(line == null) continue;

    	    	System.out.println("Server receives data: " + line);
    	    	os.println(line); 
    	    }
        }
		catch(IOException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				clientSocket.close();
			}
			catch(IOException e){}
		}
    }
}

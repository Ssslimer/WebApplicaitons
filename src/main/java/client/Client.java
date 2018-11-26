package client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
	private Socket clientSocket;
	private ObjectOutputStream streamToServer;
	private ObjectInputStream streamFromServer;

	public Client(String ip, int port)
	{
		try
    	{	
			clientSocket = new Socket(ip, port);
			System.out.println("Connected to server IP: " + ip + " Port: " + clientSocket.getPort());
	
    		streamToServer = new ObjectOutputStream(clientSocket.getOutputStream());
	    	streamFromServer = new ObjectInputStream(clientSocket.getInputStream());					
    	}
    	catch(IOException e)
    	{  		
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    		try{clientSocket.close();
			}catch(IOException e1){}
    	}
	}
}

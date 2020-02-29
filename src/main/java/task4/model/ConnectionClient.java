package task4.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import task4.model.Message.MessageContent;

public class ConnectionClient
{
	private final String clientName;
	private final boolean isCallback;
	
	private Socket clientSocket;
	private ObjectOutputStream streamToServer;
	private ObjectInputStream streamFromServer;

	public ConnectionClient(String clientName, boolean isCallback)
	{
		this.clientName = clientName;
		this.isCallback = isCallback;
	}
	
	public void connectToServer(int port)
	{
		try
    	{	
			clientSocket = new Socket("127.0.0.1", port);
			System.out.println("Connected to server on port: " + clientSocket.getPort());
	
    		streamToServer = new ObjectOutputStream(clientSocket.getOutputStream());
	    	streamFromServer = new ObjectInputStream(clientSocket.getInputStream());
	    	
	    	List<String> data = new ArrayList<>(1);
	    	data.add(isCallback ? "true" : "false");
	    	sendToServer(new Message(MessageContent.CONNECTION_INIT, clientName, data));						
    	}
    	catch(IOException e)
    	{  		
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    		try{clientSocket.close();
			}catch(IOException e1){}
    	}
	}
	
	public void sendToServer(Message message) throws IOException
	{
		streamToServer.writeObject(message);
	}
	
	public Object getDataFromServer() throws ClassNotFoundException, IOException
	{
		return streamFromServer.readObject();	
	}
	
	public String getClientName()
	{
		return clientName;
	}
}

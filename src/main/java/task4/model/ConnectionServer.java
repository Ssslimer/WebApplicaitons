package pai_o1.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import pai_o1.model.Message.MessageContent;
import pai_o1.model.TimeBank.Result;

public class ConnectionServer extends Thread
{	
	private Socket clientSocket;
	private TimeBank timeBank;
	private ObjectInputStream streamFromClient;
	private ObjectOutputStream streamToClient;
	
	private String clientName;
	
	public ConnectionServer(Socket clientSocket, TimeBank timeBank)
	{
		this.clientSocket = clientSocket;
		this.timeBank = timeBank;
	}
	
	@Override
	public void run()
	{		
	    try
	    {
	    	initConection();
	    	
	    	while(true)
	    	{
	    		Message fromClient = (Message) streamFromClient.readObject();
	
	    		System.out.println("Message from: " + fromClient.getSender() +" "+ fromClient.toString());
	    		Message toClient = handleMessage(fromClient);
	    		if(toClient == null) continue;

	    		streamToClient.writeObject(toClient);
	    	}
	    }
	    catch(SocketException e)
	    {
	    	System.out.println(e.getMessage());
	    	System.out.println("Lost connection with client");
	    	return;
	    }
	    catch(EOFException e)
	    {	 
	    	try {wait(100);}
	    	catch(InterruptedException e1) {}
	    }
	    catch(ClassNotFoundException | IOException e)
	    {
			e.printStackTrace();
			return;
		}
    }
	
	private void initConection() throws IOException
	{
		streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());
		streamFromClient = new ObjectInputStream(clientSocket.getInputStream());
	}
	
	private Message handleMessage(Message message)
	{
		MessageContent activity = message.getActivity();
		clientName = message.getSender();
		List<String> data = (List<String>) message.getData();
		
		switch(activity)
		{			
			case ADD_RESERVATION:				
				int reservationTime = Integer.parseInt(data.get(0));
				boolean isLazy = data.get(1).equalsIgnoreCase("lazy") ? true : false;
				
				if(reservationTime > 18 || reservationTime < 10) return new Message(MessageContent.STRING, "Wrong time format");
				
				Result result = timeBank.addReservation(clientName, reservationTime, isLazy);
				
				switch(result)
				{
					case LOCKED:
						return new Message(MessageContent.STRING, "Cannot make reservation right now. Please try later.");
					case SUCCESS:
						return new Message(MessageContent.STRING, "Your reservation has been accepted");
					case TAKEN:
						return new Message(MessageContent.STRING, "This termin is not available");
					default:
						return new Message(MessageContent.STRING, "Your reservation has been accepted");
				}

			case CANCEL_RESERVATION:				
				reservationTime = Integer.parseInt(data.get(0));
				isLazy = data.get(1).equalsIgnoreCase("lazy") ? true : false;
				
				result = timeBank.cancelReservation(clientName, reservationTime, isLazy);
				
				switch(result)
				{
					case LOCKED:
						return new Message(MessageContent.STRING, "Cannot make reservation right now. Please try later.");
					case SUCCESS:
						return new Message(MessageContent.STRING, "Reservation cancelled");
					case NOT_OWNER:
						return new Message(MessageContent.STRING, "You didn't have a reservation for this hour");
					default:
						return new Message(MessageContent.STRING, "Your reservation has been accepted");
				}
				
			case GET_FREE_RESERVATIONS:	
				return new Message(MessageContent.GET_FREE_RESERVATIONS, timeBank.getFreeReservations());
				
			case CONNECTION_INIT:
				boolean isCallback = data.get(0).equals("true") ? true : false;
				timeBank.addClient(clientName, this, isCallback);
				
				if(isCallback)  setName("Connection from: " + clientName);
				else			setName("Callback Connection to: " + clientName);
					
				return null;

			default:
				return new Message(MessageContent.STRING, "Unknown command");
		}
	}
	
	public void sendFreeTermins()
	{
		Message message = new Message(MessageContent.GET_FREE_RESERVATIONS, timeBank.getFreeReservations());
		
		if(streamToClient == null) return;
		
		try {streamToClient.writeObject(message);}
		catch(IOException e) {e.printStackTrace();}
	}
	
	public void terminate()
	{
		Message message = new Message(MessageContent.TERMINATE, Collections.emptyList());
		
		if(streamToClient == null) return;
		
		try {streamToClient.writeObject(message);}
		catch(IOException e) {e.printStackTrace();}
	}
	
	public String getClientName()
	{
		return clientName;
	}
}

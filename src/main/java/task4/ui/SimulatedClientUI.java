package task4.ui;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import task4.model.ConnectionClient;
import task4.model.Message;
import task4.model.Message.MessageContent;

public class SimulatedClientUI implements UserInterface
{
	private final ConnectionClient client;	
	private final Random random = new Random();
	private final String clientName;

	public SimulatedClientUI(ConnectionClient client, String clientName)
	{
		this.client = client;
		this.clientName = clientName;
	}
	
	public void loop()
	{		
		try
	    {
			while(true)
			{
				Message message = prepareMessage();
	    		client.sendToServer(message);
	    		
	    		Message dataFromServer = (Message) client.getDataFromServer();
		    	handleCallback(dataFromServer);

	    		try {Thread.sleep(1000);}
				catch(InterruptedException e) {e.printStackTrace();}
			}	
	    }	
		catch(SocketException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Lost connection with server");
			return;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	void handleCallback(Message serverCallback)
	{		
		switch(serverCallback.getActivity())
		{
			case STRING: 	System.out.println(serverCallback.getData() + "\n"); break;		
			case TERMINATE: System.exit(0);				
			default: 		System.out.println("Server have send unknown message");
		}
	}
	
	private Message prepareMessage()
	{
		MessageContent content = random.nextBoolean() ? MessageContent.ADD_RESERVATION : MessageContent.CANCEL_RESERVATION;
		
		List<String> data = new ArrayList<>(2);
		//data.add(Integer.toString((random.nextInt(8) + 10)));
		data.add(Integer.toString(15));
		data.add(random.nextInt(10) < 2 ? "lazy" : "normal");
		
		System.out.println(content + " " + data);
		
		return new Message(content, clientName, data);
	}
}

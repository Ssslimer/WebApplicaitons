package pai_o1.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import pai_o1.model.ConnectionClient;
import pai_o1.model.Message;
import pai_o1.model.Message.MessageContent;

public class ConsoleUI implements UserInterface
{
	private BufferedReader consoleReader;
	private final ConnectionClient client;
	private final String clientName;
	
	public ConsoleUI(ConnectionClient client, String clientName)
	{
		this.client = client;
		this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
		this.clientName = clientName;
	}
	
	public void loop()
	{		
		try
	    {		
	        showMainMenu();
	        
			while(true)
			{
				String input = getUserInput();
				
				if(input.equalsIgnoreCase("exit")) return;
				
				Message message = prepareMessage(input);
				if(message == null) continue;
				
	    		client.sendToServer(message);
	    		
		    	Message dataFromServer = (Message) client.getDataFromServer();
		    	handleCallback(dataFromServer);

	    		try {Thread.sleep(1000);}
				catch(InterruptedException e) {e.printStackTrace();}
			}
	    }		
		catch(SocketException e)
		{
			System.out.println("Lost connection with server");
			System.out.println(e.getMessage());
			return;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch(ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
	}

	private void handleCallback(Message serverCallback)
	{
		switch(serverCallback.getActivity())
		{
			case STRING:
				System.out.println(serverCallback.getData());
			break;
				
			case GET_FREE_RESERVATIONS:
				System.out.println(serverCallback.getData());
			break;
				
			case TERMINATE:
				System.exit(0);
			
			default:
				System.out.println("Server have send unknown message");
		}
	}
	
	/** We wait for user to enter sth in the console */
	private String getUserInput() throws IOException
	{
		String s = null;
    	while((s = consoleReader.readLine()) == null) {}
    	
    	return s;
	}
	
	private Message prepareMessage(String input)
	{
		String[] line = input.split(" ");
		List<String> data = Arrays.asList(Arrays.copyOfRange(line, 1, line.length));
		
		MessageContent act = null;		
		switch(Integer.parseInt(line[0]))
		{
			case 1:
				if(data.size() != 0)
				{
					System.out.println("Wrong command for getting free termins");
					return null;
				}
				act = MessageContent.GET_FREE_RESERVATIONS;
			break;		
			case 2:
				if(data == null || data.size() != 2) 
				{
					System.out.println("Wrong command for add reservation");
					return null;
				}
				act = MessageContent.ADD_RESERVATION;		
				break;
			case 3:
				if(data == null || data.size() != 2)
				{
					System.out.println("Wrong command for reservation cancel");
					return null;
				}
				act = MessageContent.CANCEL_RESERVATION;
				break;
		}
		
		return new Message(act, client.getClientName(), data);
	}
	
	private void showMainMenu()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Show free termins: 1\n");
		builder.append("Add a new reservation: 2 <hour> <lazy or normal>\n");
		builder.append("Cancle your reservation: 3 <hour> <lazy or normal>\n");
		builder.append("Close the application: exit");
		System.out.println(builder.toString());
	}
}

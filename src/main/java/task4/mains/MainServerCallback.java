package pai_o1.mains;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import pai_o1.model.ConnectionClient;
import pai_o1.model.Message;

public class MainServerCallback
{
	public static void main(String[] args)
	{
		int port = Integer.parseInt(args[0]);
		String clientName = args[1];

		ConnectionClient clientConnection = new ConnectionClient(clientName, true);
		clientConnection.connectToServer(port);		
	
		loop(clientConnection);
	}

	private static void loop(ConnectionClient clientConnection)
	{		
		try
    	{
			while(true)
			{
	    		Message serverCallback = (Message) clientConnection.getDataFromServer();
				handleCallback(serverCallback);	    		

	    		try {Thread.sleep(1000);}
				catch(InterruptedException e) {e.printStackTrace();}
			}
    	}
		catch(SocketException e)
		{
			System.exit(0);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Server has send unknown command");
			System.out.println(e.getMessage());
			return;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void handleCallback(Message serverCallback)
	{
		switch(serverCallback.getActivity())
		{
			case STRING:
				System.out.println(serverCallback.getData());
			break;
				
			case GET_FREE_RESERVATIONS:
				List<Integer> freeTermins = (List<Integer>) serverCallback.getData();
				System.out.println("Free termins:");
				
				for(int i = 0; i < freeTermins.size(); i++) System.out.print((10 + freeTermins.get(i)) + " ");
				System.out.println(' ');
			break;

			case TERMINATE:
				System.exit(0);
			
			default:
				System.out.println("Server have send unknown message");
		}
	}
}

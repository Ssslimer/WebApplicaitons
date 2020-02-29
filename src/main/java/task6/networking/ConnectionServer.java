package task6.networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import task6.equation.Equation;
import task6.optimization.DifferentialEvolution;
import task6.optimization.OptimizationResult;
import task6.optimization.SearchDomain;
import task6.optimization.SimulatedAnnealing;

class ConnectionServer extends Thread
{	
	private Socket clientSocket;
	private ObjectInputStream streamFromClient;
	private ObjectOutputStream streamToClient;

	public ConnectionServer(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run()
	{		
	    try
	    {
			streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());
			streamFromClient = new ObjectInputStream(clientSocket.getInputStream());
			
	    	Message fromClient = (Message) streamFromClient.readObject();	
	    	System.out.println("Message from: " + fromClient.toString());
	    		
	    	Message toClient = handleMessage(fromClient);
	    		
	    	streamToClient.writeObject(toClient);
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
	
	@SuppressWarnings("unchecked")
	private Message handleMessage(Message message)
	{
		MessageContent activity = message.getActivity();
		List<Object> data = (List<Object>) message.getData();
		
		switch(activity)
		{			
			case REQUEST:
				int optimizationMethod = (int) data.get(0);
				Equation eq = (Equation) data.get(1);
				
				if(optimizationMethod == 1)
				{
					int maxSteps = (int) data.get(2);
					SearchDomain range = (SearchDomain) data.get(3);
					
					OptimizationResult result = SimulatedAnnealing.findGlobalOptimum(eq, maxSteps, range);
					
					return new Message(MessageContent.RESULT, result);
				}
				else if(optimizationMethod == 2)
				{
					int maxSteps = (int) data.get(2);
					SearchDomain range = (SearchDomain) data.get(3);
					
					OptimizationResult result = DifferentialEvolution.findGlobalOptimum(eq, maxSteps, range);

					return new Message(MessageContent.RESULT, result);
				}
				else return new Message(MessageContent.STRING, "Unknown command");
							
			default:
				return new Message(MessageContent.STRING, "Unknown command");
		}
	}
}

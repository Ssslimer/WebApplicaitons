package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import equation.Equation;
import equation.EquationParser;
import optimization.OptimizationResult;
import optimization.SearchDomain;
import visualization.Visualization;
import visualization.VisualizationLauncher;

class Client
{
	private Socket clientSocket;
	private ObjectOutputStream streamToServer;
	private ObjectInputStream streamFromServer;
	private BufferedReader consoleReader;

	public void connectToServer(String ip, int port)
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
		
		consoleReader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void start()
	{		
		try
	    {		
			String input = getUserInput();			
			Message message = prepareMessage(input);
			if(message == null) return;
				
	    	sendToServer(message);
	    		
		    Message dataFromServer = (Message) getDataFromServer();
		    handleCallback(dataFromServer);
	    }
		catch(NumberFormatException e)
		{
			System.out.println("Wrong command");
			System.out.println(e.getMessage());
			start();
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
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private Message prepareMessage(String input) throws NumberFormatException
	{
		String[] line = input.split(" ");

		List<Object> data = new LinkedList<>();			
		EquationParser parser = new EquationParser();
		
		int optimizationMethod = Integer.parseInt(line[0]);
		switch(optimizationMethod)
		{
			case 1: // Simulated Annealing
				if(line.length < 3)
				{
					System.out.println("Wrong number of arguments");
					return null;
				}
				
				data.add(optimizationMethod);

				Equation eq = parser.parse(line[1]);
				data.add(eq);
				
				int maxSteps = Integer.parseInt(line[2]);
				data.add(maxSteps);
				
				String[] latterals = Arrays.copyOfRange(line, 3, 3+eq.getVariables().size()*2);
				SearchDomain searchDomain = stringsToSearchDomain(latterals);
				data.add(searchDomain);
				
			break;
			
			case 2: // Differential Evolution
				if(line.length < 3)
				{
					System.out.println("Wrong number of arguments");
					return null;
				}
				
				data.add(optimizationMethod);

				eq = parser.parse(line[1]);
				data.add(eq);
				
				maxSteps = Integer.parseInt(line[2]);
				data.add(maxSteps);
				
				latterals = Arrays.copyOfRange(line, 3, 3+eq.getVariables().size()*2);
				searchDomain = stringsToSearchDomain(latterals);
				data.add(searchDomain);
			break;
		}
		
		return new Message(MessageContent.REQUEST, data);
	}
	
	private SearchDomain stringsToSearchDomain(String[] lattelars) throws NumberFormatException
	{
		double[] r = new double[lattelars.length];
		for(int i = 0; i < lattelars.length; i++) r[i] = Double.parseDouble(lattelars[i]);
		
		return new SearchDomain(r);
	}
	
	private void handleCallback(Message serverCallback)
	{
		switch(serverCallback.getActivity())
		{
			case RESULT:
				OptimizationResult result = (OptimizationResult) serverCallback.getData();
				
				System.out.println(result);
				
				try
				{
					VisualizationLauncher.show(new Visualization(result));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
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
	
	public void sendToServer(Message message) throws IOException
	{
		streamToServer.writeObject(message);
	}
	
	public Object getDataFromServer() throws ClassNotFoundException, IOException
	{
		return streamFromServer.readObject();	
	}
}

package task4.model;

public class Client
{
	private final String name;
	
	private ConnectionServer fromClient;
	private ConnectionServer toClient;
	
	public Client(String name, ConnectionServer connection, boolean isCallback)
	{
		this.name = name;
		if(isCallback) 	this.toClient = connection;
		else 			this.fromClient = connection;
	}
	
	public void addConnectionToClient(ConnectionServer toClient)
	{
		this.toClient = toClient;
	}
	
	public void addConnectionFromClient(ConnectionServer fromClient)
	{
		this.fromClient = fromClient;	
	}
	
	public String getName()
	{
		return name;
	}

	public ConnectionServer getConnectionFromClient()
	{
		return fromClient;
	}
	
	public ConnectionServer getConnectionToClient()
	{
		return toClient;
	}
}

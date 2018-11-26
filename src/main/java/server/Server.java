package server;


import java.net.ServerSocket;

public class Server extends Thread
{
	private int port;
	
	public Server(int port)
	{
		this.port = port;
	}
	
	@Override
	public void run()
	{
		try(ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Server was set up. IP: " + serverSocket.getInetAddress() +" port: " + serverSocket.getLocalPort());
		
			while(true)
			{
				/*final ConnectionServer connectionToClient = new ConnectionServer(serverSocket.accept(), this);
				System.out.println("New connection is open");       			
				connectionToClient.start();

				Thread watcher = new Thread(() ->
				{
					try{connectionToClient.join();
					}catch(InterruptedException e){}

					String name = connectionToClient.getClientName();
					System.out.println("removing: " +name);
					Client client = clients.get(name);
					if(client == null) return;
					if(client.getConnectionFromClient().isAlive()) client.getConnectionFromClient().terminate();
					if(client.getConnectionToClient().isAlive()) client.getConnectionToClient().terminate();
					clients.remove(name);
        		});
				watcher.start();*/
        	}
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try {Thread.sleep(2000);}
			catch(InterruptedException e) {e.printStackTrace();}
        }
	}
}

package task4.model;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TimeBank
{
	public static Map<String, Client> clients = Collections.synchronizedMap(new HashMap<>());
	private static final int MAX_RESERVATIONS_PER_DAY = 8;
	
	
	private String[] reservations = new String[MAX_RESERVATIONS_PER_DAY];
	private boolean[] isLocked = new boolean[MAX_RESERVATIONS_PER_DAY];

	private synchronized void lock(int hour)
	{
		this.isLocked[hour] = true;
	}
	
	private synchronized void unlock(int hour)
	{
		this.isLocked[hour] = false;
	}
	
	private synchronized boolean isLocked(int hour)
	{
		return isLocked[hour];
	}

	public enum Result
	{
		LOCKED, SUCCESS, TAKEN, NOT_OWNER
	}
	
	public TimeBank(int port)
	{
		try(ServerSocket echoServer = new ServerSocket(port))
		{
			System.out.println("Server setup on port: " + echoServer.getLocalPort());
		
			while(true)
			{
				final ConnectionServer connectionToClient = new ConnectionServer(echoServer.accept(), this);
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
				watcher.start();
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
	
	public Result addReservation(String reservationOwner, int hour, boolean isLazy)
	{
		int time = hour - 10;
			
		if(isLocked(time))
		{
			System.out.println(reservationOwner +" couldn't make a reservation. The reception is busy");
			return Result.LOCKED;
		}
	
		lock(time);
		
		if(isLazy)
		{
			try{Thread.sleep(10000);
			}catch(InterruptedException e){}
		}
		
		if(reservations[time] != null)
		{
			System.out.println(reservationOwner +" couldn't make a reservation. Reservation on " + hour + " is already taken");
			unlock(time);
			return Result.TAKEN;
		}
			
		reservations[time] = reservationOwner;

		unlock(time);
		System.out.println(reservationOwner +" successfully made reservation on " + hour);
		sendFreeTermins();
		
		return Result.SUCCESS;
	}
	
	public Result cancelReservation(String client, int hour, boolean isLazy)
	{
		int time = hour - 10;
		
		if(isLocked(time))
		{
			System.out.println(client +" couldn't cancel a reservation. The reception is busy");
			return Result.LOCKED;
		}
		
		lock(time);
		
		if(isLazy)
		{
			try{Thread.sleep(10000);
			}catch(InterruptedException e){}
		}
		
		if(!client.equals(reservations[time]))
		{
			unlock(time);
			System.out.println(client +" couldn't cancel a reservation. Is not an owner of reservation on " + hour);
			return Result.NOT_OWNER;	
		}
		
		reservations[time] = null;

		unlock(time);
		
		System.out.println(client +" successfully canceled reservation on " + hour);
		sendFreeTermins();
		
		return Result.SUCCESS;
	}
	
	public List<Integer> getFreeReservations()
	{
		List<Integer> freeReservations = new ArrayList<>();
		
		synchronized(reservations)
		{		
			for(int i = 0; i < 8; i++)
			{
				if(reservations[i] == null) freeReservations.add(i);
			}
		}

		return freeReservations;
	}
	
	private void sendFreeTermins()
	{
		synchronized(clients)
		{
		      Iterator<Client> i = clients.values().iterator();
		      
		      while(i.hasNext())
		      {
		    	  Client client = i.next();
		    	  
		    	  ConnectionServer toClient = client.getConnectionToClient();
		    	  if(toClient == null) continue;
		    	  
		    	  toClient.sendFreeTermins();
		      }
		}
	}

	public void addClient(String clientName, ConnectionServer connection, boolean isCallback)
	{
		Client existingClient = clients.get(clientName);
		
		if(existingClient == null)
		{
			clients.put(clientName, new Client(clientName, connection, isCallback));
		}
		else
		{
			if(isCallback && existingClient.getConnectionToClient() == null)
			{
				existingClient.addConnectionToClient(connection);
			}
			else if(!isCallback && existingClient.getConnectionFromClient() == null)
			{
				existingClient.addConnectionFromClient(connection);
			}
		}	
	}
}

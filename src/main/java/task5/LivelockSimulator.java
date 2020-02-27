import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LivelockSimulator
{
	private enum PavementSide
	{
		UP, DOWN
	}
	
	public void startSimulation()
	{
		Pedestrian p1 = new Pedestrian("Alphonse", PavementSide.UP);
		Pedestrian p2 = new Pedestrian("Gaston", PavementSide.UP);

		ExecutorService service = Executors.newFixedThreadPool(2);
		try
        {
			Future<?> f1 = service.submit(() -> p1.go(p2));            
			Future<?> f2 = service.submit(() -> p2.go(p1));

            System.out.println(f1.get(10, TimeUnit.SECONDS));
            System.out.println(f2.get(10, TimeUnit.SECONDS));
        }
        catch(TimeoutException e)
        {
            p1.resign();
            p2.resign();
            System.out.println("Threads stoped. Pedestrians are livelocked");
            
        }
		catch(InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		finally
		{
			service.shutdownNow();
		}
	}

	private class Pedestrian
	{
		private final String name;
		private PavementSide side;
		private boolean isActiv = true;
		
		public Pedestrian(String name, PavementSide side)
		{
			this.name = name;
			this.side = side;
		}

		public void changeSide()
		{
			if(side == PavementSide.UP) side = PavementSide.DOWN;
			else side = PavementSide.UP;
			
			System.out.println(name + " has changed the side to " + side.name());
		}
		
		public void resign()
		{
			isActiv = false;
		}

		public void go(Pedestrian other)
		{
			while(isActiv)
			{
				if(side == other.side)
				{
					try {Thread.sleep(1000);
					}catch(InterruptedException e) {}
					
					changeSide();
		
					try {Thread.sleep(1000);
					}catch(InterruptedException e) {}
					
					continue;
				}
				
				System.out.println(name + " went through");
				return;
			}
			
			System.out.println(name +" has resigned");
		}
	}
}

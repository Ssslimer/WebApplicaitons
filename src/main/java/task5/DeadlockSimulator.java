import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sun.misc.Lock;

public class DeadlockSimulator
{
	private final String s1 = "String No. 1 for testing deadlock";
	private final String s2 = "String No. 2 for testing deadlock";
	
	private final Lock s1Lock = new Lock();
	private final Lock s2Lock = new Lock();
	
	public void startSimulation()
	{
        ExecutorService service = Executors.newFixedThreadPool(2);
        
        Future<String> f1=null, f2=null;
        try
        {
            f1 = service.submit(() ->
            {
            	s1Lock.lock();
            	
    			System.out.println("Thread No. 1 locked " + s1);
    			
    			try {Thread.sleep(1000);}
    			catch(InterruptedException e) {}
    				
    			s2Lock.lock();
    			System.out.println("Thread No. 1 locked " + s2);
    			
    			s1Lock.unlock();
    			s2Lock.unlock();
    			
                return "Thread No. 2 finished execution";
            });
            
            f2 = service.submit(() ->
            {
            	s2Lock.lock();
 
            	System.out.println("Thread No. 2 locked " + s2);
    				
    			try {Thread.sleep(1000);}
    			catch(InterruptedException e) {}

    			s1Lock.lock();
    			System.out.println("Thread No. 2 locked " + s1);
	
    			s1Lock.unlock();
    			s2Lock.unlock();

                return "Thread No. 2 finished execution";
            });

            System.out.println(f1.get(10, TimeUnit.SECONDS));
            System.out.println(f2.get(10, TimeUnit.SECONDS));
        }
        catch(TimeoutException e)
        {
            System.out.println("Threads stoped");
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            service.shutdownNow();
            
            try {Thread.sleep(2000);}
			catch(InterruptedException e) {e.printStackTrace();}
            
            /*System.out.println(f1.isCancelled());
            System.out.println(f1.isDone());
            System.out.println(f2.isCancelled());
            System.out.println(f2.isDone());*/
        }
	}
}

package task5;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StarvationSimulator
{
	private int[] tab = {1, 123, 56, 47, 123, 56, 658, 12, 76, 34, 253, 667, 115, 980};
	private Lock lock = new ReentrantLock();	
	
	private static Random random = new Random();
	
	private class MyTask implements Runnable, Comparable<MyTask>
	{
		private final int priority;
		
		public MyTask(int priority)
		{
			this.priority = priority;
		}
		
		@Override
		public void run()
		{
			long t1 = System.nanoTime();
			double b = random.nextDouble() * 3.14 * Math.sin(random.nextInt());
			long t2 = System.nanoTime();
			
			System.out.println("Priority: "+ priority + " Time: " + (t2-t1) + " Value: " + ((t2-t1) * b));
			
			try {Thread.sleep(100);
			}catch(InterruptedException e){}
		}

		@Override
		public int compareTo(MyTask o)
		{
			return o.priority - this.priority;
		}		
	}
	
	public void startSimulation()
	{
		Thread t1 = new Thread(new GreedyTask());
		Thread t2 = new Thread(new GreedyTask());
		Thread t3 = new Thread(new GreedyTask());
		Thread t4 = new Thread(new GreedyTask());
		Thread t5 = new Thread(new GreedyTask());
		Thread t6 = new Thread(new NormalTask());
		
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MAX_PRIORITY);
		t3.setPriority(Thread.MAX_PRIORITY);
		t4.setPriority(Thread.MAX_PRIORITY);
		t5.setPriority(Thread.MAX_PRIORITY);
		t6.setPriority(Thread.MIN_PRIORITY);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}

	private class GreedyTask implements Runnable
	{
		@Override
		public void run()
		{
			for(int i = 0; i < tab.length; i++)
			{
				while(!lock.tryLock()) {}

				System.out.println("Greedy: " + (tab[i] * random.nextDouble()));
				
				try {Thread.sleep(10);}
				catch(InterruptedException e) {}
				
				lock.unlock();
			}
			
			System.out.println("Greedy finished");
		}		
	}
	
	private class NormalTask implements Runnable
	{
		@Override
		public void run()
		{
			for(int i = 0; i < tab.length; i++)
			{
				while(!lock.tryLock()) {}
				
				System.out.println("Normal: " + (tab[i] * random.nextDouble()));
				
				try {Thread.sleep(10);}
				catch(InterruptedException e) {}
					
				lock.unlock();
			}
			
			System.out.println("Normal finished");
		}		
	}
}

package task5;

public class Task5
{
	public static void main(String[] args)
	{
		if(args.length != 1)
		{
			System.out.println("Wrong number of parameters. User O2.jar <number for mode 1, 2, 3>");
		}
		
		int mode = Integer.parseInt(args[0]);
		
		switch(mode)
		{
			case 1: simulateDeadlock(); return;
			case 2: simulateLivelock(); return;
			case 3: simulateStarvation(); return;
			default: System.out.println("Wrong mode, choose: 1, 2 or 3");
		}
		
	}

	private static void simulateDeadlock()
	{
		DeadlockSimulator sim = new DeadlockSimulator();
		sim.startSimulation();
	}
	
	private static void simulateLivelock()
	{
		LivelockSimulator sim = new LivelockSimulator();
		sim.startSimulation();
	}
	
	private static void simulateStarvation()
	{
		StarvationSimulator sim = new StarvationSimulator();
		sim.startSimulation();
	}
}

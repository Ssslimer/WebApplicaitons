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
		
		
		Startable simulation = selectSimulation(mode);
		if(simulation == null)
		{
			System.out.println("Wrong mode, choose: 1, 2 or 3");
			return;
		}

		simulation.start();
	}
	
	private static Startable selectSimulation(int mode)
	{
		switch(mode)
		{
			case 1:
				return new DeadlockSimulator();
			case 2:
				return new LivelockSimulator();
			case 3:
				return new StarvationSimulator();
			default:
				return null;
		}
	}
}

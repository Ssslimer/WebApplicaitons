package optimization;

import java.util.Random;

import equation.Equation;

public class SimulatedAnnealing
{
	private static Random random = new Random();

	public double[] findGlobalOptimum(Equation equation, Optimum optimum, int maxSteps, SearchDomain searchDomain)
	{		
		double[] currentVariables = new double[equation.getVariables().size()];
		for(int i = 0; i < currentVariables.length; i++)
		{
			double min = searchDomain.ranges[i][0];
			double max = searchDomain.ranges[i][1];
			currentVariables[i] = randomValueFromRange(min, max);
		}
		
		double currentValue = equation.compute(currentVariables);
		
        double[] bestVariables = new double[currentVariables.length];
        double bestValue = currentValue;
        
		for(int step = 0; step < maxSteps; step++)
		{
			for(int i = 0; i < currentVariables.length; i++)
			{
				double min = searchDomain.ranges[i][0];
				double max = searchDomain.ranges[i][1];
				currentVariables[i] = randomValueFromRange(min, max);
			}
			
			double newValue = equation.compute(currentVariables);
			
            if(acceptanceProbability(currentValue, newValue, step/maxSteps) > Math.random())
            {
            	currentValue = newValue;
            	
                if(optimum == Optimum.MINIMA && currentValue < bestValue ||
                   optimum == Optimum.MAXIMA && currentValue > bestValue)
                {
                    for(int i = 0; i < bestVariables.length; i++) bestVariables[i] = currentVariables[i];
                    bestValue = currentValue;
                }
            }
		}

		return bestVariables;
	}
	
    private double acceptanceProbability(double energy, double newEnergy, double temperature)
    {
        if(newEnergy < energy) return 1;
        
        return Math.exp(-(energy - newEnergy) / temperature);
    }
    
    private double randomValueFromRange(double min, double max)
    {
    	return min + (max - min) * random.nextDouble();
    }
}

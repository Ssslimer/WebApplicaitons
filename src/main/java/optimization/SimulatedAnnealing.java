package optimization;

import java.util.Random;

import equation.Equation;
import equation.OptimizationResult;
import equation.SearchDomain;

public class SimulatedAnnealing
{
	private static Random random = new Random();

	public OptimizationResult findGlobalOptimum(Equation equation, int maxSteps, SearchDomain searchDomain)
	{		
		final double changeThreshold = 0.001d;
				
		double[] currentVariables = generateRandomVariables(equation, searchDomain);
		double[] bestVariables = new double[currentVariables.length];
		
		double currentValue = equation.compute(currentVariables);
        double bestValue = currentValue;
        
		for(int step = 0; step < maxSteps; step++)
		{
			setRandomVariables(searchDomain, currentVariables);

			double newValue = equation.compute(currentVariables);
			
            if(acceptanceProbability(currentValue, newValue, step/maxSteps) > Math.random())
            {
            	currentValue = newValue;
            	
                if(currentValue < bestValue)
                {
                    for(int i = 0; i < bestVariables.length; i++) bestVariables[i] = currentVariables[i];
                    
                    if(bestValue - currentValue < changeThreshold)
                    {
                    	return new OptimizationResult(step, bestValue, bestVariables, null);
                    }
                    
                    bestValue = currentValue;                 
                }
            }
		}

		return new OptimizationResult(maxSteps, bestValue, bestVariables, null);
	}
	
	private double[] generateRandomVariables(Equation equation, SearchDomain searchDomain)
	{
		double[] currentVariables = new double[equation.getVariables().size()];
		for(int i = 0; i < currentVariables.length; i++)
		{
			double min = searchDomain.ranges[i][0];
			double max = searchDomain.ranges[i][1];
			currentVariables[i] = randomValueFromRange(min, max);
		}
		
		return currentVariables;
	}
	
	private void setRandomVariables(SearchDomain searchDomain, double[] currentVariables)
	{
		for(int i = 0; i < currentVariables.length; i++)
		{
			double min = searchDomain.ranges[i][0];
			double max = searchDomain.ranges[i][1];
			currentVariables[i] = randomValueFromRange(min, max);
		}
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

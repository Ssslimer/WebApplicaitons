package task6.optimization;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import task6.equation.Equation;

public class SimulatedAnnealing
{
	private static Random random = new Random();

	public static OptimizationResult findGlobalOptimum(Equation equation, int maxSteps, SearchDomain searchDomain)
	{		
		final double changeThreshold = 0.001d;
		final int maxUnsuccessfulShots = 500;
			
		double[] currentVariables = generateRandomVariables(equation, searchDomain);
		double[] bestVariables = new double[currentVariables.length];
		
		double currentValue = equation.compute(currentVariables);
        double bestValue = currentValue;
        
        List<double[]> variablesMemory = new LinkedList<>();
        
        int unsuccessfulShots = 0;
        int step = 1;
		for(; step < maxSteps; step++)
		{
			setRandomVariables(searchDomain, currentVariables);

			double newValue = equation.compute(currentVariables);
			
            if(acceptanceProbability(currentValue, newValue, step/maxSteps) > Math.random())
            {
            	currentValue = newValue;
            	
                if(currentValue < bestValue)
                {
                	variablesMemory.add(currentVariables.clone());
                	
                	System.arraycopy(currentVariables, 0, bestVariables, 0, bestVariables.length);
                            
                    double improvement = bestValue - currentValue;
                    bestValue = currentValue;
                    
                    if(improvement < changeThreshold) break;           
                }
                else unsuccessfulShots++;
                
                if(unsuccessfulShots >= maxUnsuccessfulShots) break;
            }
		}

		double[] variablesHistory = generateVariablesHistory(variablesMemory, currentVariables.length);
		return new OptimizationResult(equation, step, bestValue, bestVariables, variablesHistory, searchDomain.ranges);
	}
	
	private static double[] generateVariablesHistory(List<double[]> list, int size)
	{
		double[] result = new double[size * list.size()];
		
		int i = 0;
		for(double[] array : list)
		{
			System.arraycopy(array, 0, result, i, array.length);
			i += array.length;
		}
		
		return result;
	}
	
	private static double[] generateRandomVariables(Equation equation, SearchDomain searchDomain)
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
	
	private static void setRandomVariables(SearchDomain searchDomain, double[] currentVariables)
	{
		for(int i = 0; i < currentVariables.length; i++)
		{
			double min = searchDomain.ranges[i][0];
			double max = searchDomain.ranges[i][1];
			currentVariables[i] = randomValueFromRange(min, max);
		}
	}
	
    private static double acceptanceProbability(double energy, double newEnergy, double temperature)
    {
        if(newEnergy < energy) return 1;
        
        return Math.exp(-(energy - newEnergy) / temperature);
    }
    
    private static double randomValueFromRange(double min, double max)
    {
    	return min + (max - min) * random.nextDouble();
    }
}

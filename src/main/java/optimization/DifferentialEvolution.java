package optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import equation.Equation;
import equation.OptimizationResult;
import equation.SearchDomain;

public class DifferentialEvolution
{
	private static Random random = new Random();

	public OptimizationResult findGlobalOptimum(Equation eq, int maxSteps, SearchDomain searchDomain)
	{		
		final double changeThreshold = 0.0001d;		
		final int problemSize = eq.getVariables().size();
		final double differentialWeight = 0.8d; //random.nextDouble()*2;
		final double crossoverRate = 0.9d; //random.nextDouble();			
		final int populationSize = 10;
			
		double[][] population = new double[populationSize][problemSize];
		double[]   bestSolution = new double[problemSize];
		
		for(int agent = 0; agent < population.length; agent++)
		{
			for(int variable = 0; variable < population[0].length; variable++)
			{
				population[agent][variable] = randomValueFromRange(searchDomain.ranges[variable][0], searchDomain.ranges[variable][1]);				
			}
		}
		
		for(int step = 0; step < maxSteps; step++)
		{
			double[][] newPopulation = new double[populationSize][problemSize];
			
			for(int agent = 0; agent < population.length; agent++)
			{
				double[] newSample = newSample(agent, population, problemSize, differentialWeight, crossoverRate);
				
				if(eq.compute(newSample) < eq.compute(population[agent]))
				{
					if(eq.compute(population[agent]) - eq.compute(newSample) < changeThreshold)
					{
						double[] bestVariables = findBestResult(eq, population);
						return new OptimizationResult(step, eq.compute(bestVariables), bestVariables, null);
					}
					
					newPopulation[agent] = newSample;
				}
				else newPopulation[agent] = Arrays.copyOf(population[agent], problemSize);			
			}
			
			population = newPopulation;
			
			bestSolution = findBestResult(eq, population);
		}
	
		return new OptimizationResult(maxSteps, eq.compute(bestSolution), bestSolution, null);
	}
	
	private double[] newSample(int agent, double[][] agents, int problemSize, double differentialWeight, double crossoverRate)
	{
		double[] newSample = new double[problemSize];
		
		List<Integer> indiciesList = randomAgentsIndices(3, agent, agents.length);
		
		for(int i = 0; i < problemSize; i++)
		{
			int R = random.nextInt(problemSize);
			
			if(i == R && random.nextDouble() < crossoverRate)
			{
				double P2i = agents[indiciesList.get(2)][i];
				double P1i = agents[indiciesList.get(1)][i];
				double P0i = agents[indiciesList.get(0)][i];
				
				newSample[i] = P2i + differentialWeight * (P0i - P1i);						
			}
			else
			{
				newSample[i] = agents[agent][i];
			}
		}
		
		return newSample;
	}
    
    private double randomValueFromRange(double min, double max)
    {
    	return min + (max - min) * random.nextDouble();
    }
    
    private List<Integer> randomAgentsIndices(int howMany, int excludedIndex, int range)
    {
    	List<Integer> indicies = new ArrayList<>(howMany);
    	
    	while(indicies.size() != howMany)
    	{
    		int i = random.nextInt(range);
    		
    		if(i != excludedIndex && !indicies.contains(i)) indicies.add(i);
    	}
    	
    	return indicies;
    }
    
	private double[] findBestResult(Equation eq, double[][] variables)
	{
		double[] bestVariables = new double[eq.getVariables().size()];
		double bestValue = Double.MAX_VALUE;
		
		for(int i = 0; i < variables.length; i++)
		{
			double value = eq.compute(variables[i]);
			
			if(value < bestValue)
			{
				bestVariables = Arrays.copyOf(variables[i], eq.getVariables().size());
			}
		}
		
		return bestVariables;
	}
}

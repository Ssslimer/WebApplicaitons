package optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import equation.Equation;

public class DifferentialEvolution
{
	private static Random random = new Random();

	public static OptimizationResult findGlobalOptimum(Equation eq, int maxSteps, SearchDomain searchDomain)
	{		
		final double changeThreshold = 0.00001d;
		final int problemSize = eq.getVariables().size();
		final double differentialWeight = 0.8d;
		final double crossoverRate = 0.9d;
		final int populationSize = 10;
			
		double[][] population = new double[populationSize][problemSize];
		double[]   bestSolution = new double[problemSize];
		
        List<double[][]> variablesMemory = new LinkedList<>();
		
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
				double[] newSample = newSample(agent, population, problemSize, differentialWeight, crossoverRate, searchDomain);
				
				if(eq.compute(newSample) < eq.compute(population[agent]))
				{
					newPopulation[agent] = newSample;
					
					if(eq.compute(population[agent]) - eq.compute(newSample) < changeThreshold)
					{
						double[] bestVariables = findBestResult(eq, variablesMemory);
                    	double[] variablesHistory = generateVariablesHistory(variablesMemory, problemSize * populationSize);
						return new OptimizationResult(eq, step, eq.compute(bestVariables), bestVariables, variablesHistory, searchDomain.ranges);
					}										
				}
				else newPopulation[agent] = Arrays.copyOf(population[agent], problemSize);			
			}
			
			population = newPopulation;

			variablesMemory.add(Arrays.stream(population).map(double[]::clone).toArray(double[][]::new));
			
			bestSolution = findBestResult(eq, variablesMemory);
		}
		
		double[] variablesHistory = generateVariablesHistory(variablesMemory, problemSize * populationSize);
		return new OptimizationResult(eq, maxSteps, eq.compute(bestSolution), bestSolution, variablesHistory, searchDomain.ranges);
	}
	
	private static double[] generateVariablesHistory(List<double[][]> list, int size)
	{
		double[] result = new double[size * list.size()];
		
		int i = 0;
		for(double[][] array2D : list)
		{
			for(double[] array : array2D)
			{
				for(double d : array)
				{
					result[i] = d;
					i++;
				}
			}
		}

		return result;
	}
	
	private static double[] newSample(int agentId, double[][] agents, int problemSize, double differentialWeight, double crossoverRate, SearchDomain domain)
	{
		double[] newSample = new double[problemSize];
		
		boolean b=true;
		randomizeAgents : while(b)
		{
			List<Integer> indiciesList = randomAgentsIndices(3, agentId, agents.length);
			
			for(int i = 0; i < problemSize; i++)
			{
				int R = random.nextInt(problemSize);
				
				if(i == R && random.nextDouble() < crossoverRate)
				{
					double P2i = agents[indiciesList.get(2)][i];
					double P1i = agents[indiciesList.get(1)][i];
					double P0i = agents[indiciesList.get(0)][i];
					
					double variableValue = P2i + differentialWeight * (P0i - P1i);
					
					if(variableValue < domain.ranges[i][0] || variableValue > domain.ranges[i][1]) continue randomizeAgents; 
					else newSample[i] = variableValue;
				}
				else newSample[i] = agents[agentId][i];
			}
			
			break;
		}

		return newSample;
	}
    
    private static double randomValueFromRange(double min, double max)
    {
    	return min + (max - min) * random.nextDouble();
    }
    
    private static List<Integer> randomAgentsIndices(int howMany, int excludedIndex, int range)
    {
    	List<Integer> indicies = new ArrayList<>(howMany);
    	
    	while(indicies.size() != howMany)
    	{
    		int i = random.nextInt(range);
    		
    		if(i != excludedIndex && !indicies.contains(i)) indicies.add(i);
    	}
    	
    	return indicies;
    }
    
	private static double[] findBestResult(Equation eq, List<double[][]> variables)
	{
		double[] bestVariables = new double[eq.getVariables().size()];
		double bestValue = Double.MAX_VALUE;
		
		for(double[][] array2D : variables)
		{
			for(int i = 0; i < array2D.length; i++)
			{
				double value = eq.compute(array2D[i]);
				
				if(value < bestValue)
				{
					bestVariables = Arrays.copyOf(array2D[i], eq.getVariables().size());
				}
			}
		}		
		
		return bestVariables;
	}
}

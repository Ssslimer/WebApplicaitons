package optimization;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import equation.Equation;

public class DifferentialEvolution
{
	private static Random random = new Random();

	public double[] findGlobalOptimum(Equation eq, Optimum optimum, int maxSteps, SearchDomain searchDomain)
	{		
		final int agentsCount = 10;
		double[][] agents = new double[agentsCount][eq.getVariables().size()];
		
		for(int agent = 0; agent < agents.length; agent++)
		{
			for(int variable = 0; variable < agents[0].length; variable++)
			{
				agents[agent][variable] = randomValueFromRange(searchDomain.ranges[variable][0], searchDomain.ranges[variable][1]);				
			}
		}
		
		for(int step = 0; step < maxSteps; step++)
		{
			for(int agent = 0; agent < agents.length; agent++)
			{
				Set<Integer> indicies = randomAgentsIndices(3, agent, agents.length);
				
				double[] variables = new double[eq.getVariables().size()+1];
				int R = random.nextInt(variables.length);
				
				/*for(int i = 0; i <; i++)
				{
					
				}*/
			}
		}
	
		return findBestResult(eq, agents);
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
    
    private double randomValueFromRange(double min, double max)
    {
    	return min + (max - min) * random.nextDouble();
    }
    
    private Set<Integer> randomAgentsIndices(int howMany, int excludedIndex, int range)
    {
    	Set<Integer> indicies = new HashSet<>();
    	
    	while(indicies.size() != howMany)
    	{
    		int i = random.nextInt(range);
    		
    		indicies.add(i);
    	}
    	
    	return indicies;
    }
}

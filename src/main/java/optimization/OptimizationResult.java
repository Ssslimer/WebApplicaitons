package optimization;

import java.io.Serializable;

import equation.Equation;

public class OptimizationResult implements Serializable
{
	private static final long serialVersionUID = -7868064535618414648L;
	
	private final Equation equation;
	private final int steps;
	private final double value;
	private final double[] variables;
	private final double[] populations;
	private final double[][] ranges;
	
	public OptimizationResult(Equation equation, int step, double value, double[] variables, double[] populations, double[][] ranges)
	{
		this.equation = equation;
		this.steps = step;
		this.value = value;
		this.variables = variables;
		this.populations = populations;
		this.ranges = ranges;
	}

	public int getStep()
	{
		return steps;
	}

	public double getValue()
	{
		return value;
	}

	public double[] getVariables()
	{
		return variables;
	}

	public double[] getPopulations()
	{
		return populations;
	}
	
	public double[][] getRanges()
	{
		return ranges;
	}
	
	public Equation getEquation()
	{
		return equation;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Result:");
		builder.append(" Steps ");
		builder.append(steps);
		builder.append(" Minima value ");
		builder.append(value);
		builder.append(" Best variables ");
		for(double var : variables) builder.append(var +" ");
		
		return builder.toString();
	}
}
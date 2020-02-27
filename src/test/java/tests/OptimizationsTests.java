package tests;
import org.junit.jupiter.api.Test;

import equation.Equation;
import equation.EquationParser;
import optimization.DifferentialEvolution;
import optimization.SearchDomain;
import optimization.SimulatedAnnealing;

public class OptimizationsTests
{
	private void printResults(Equation equation, double[] variables)
	{
		System.out.print("Position: ");
		for(int i = 0; i < variables.length; i++) System.out.printf("%.4f ", variables[i]);
		
		double result = equation.compute(variables);
		System.out.printf("Global minima: %.4f \n", result);
		System.out.println();
	}
	
	@Test
	public void testSimulatedAnnealing()
	{
		EquationParser parser = new EquationParser();

		System.out.println("Testing ROSENBROCK function");
		Equation eq = parser.parse(TestFunction.ROSENBROCK.asString());
		double[] optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();		
		printResults(eq, optimum);
		System.out.println("Exact extrema: 1 1 Global MINIMA: 0 \n");
		
		System.out.println("Testing BEALE function");
		eq = parser.parse(TestFunction.BEALE.asString());
		optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 3 0.5 Global MINIMA: 0 \n");
		
		System.out.println("Testing ACKLEY function");
		eq = parser.parse(TestFunction.ACKLEY.asString());
		optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
		
		System.out.println("Testing BOOTH function");
		eq = parser.parse(TestFunction.BOOTH.asString());
		optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 1 3 Global MINIMA: 0 \n");
		
		System.out.println("Testing GOLDSTEIN_PRICE function");
		eq = parser.parse(TestFunction.GOLDSTEIN_PRICE.asString());
		optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 -1 Global MINIMA: 3 \n");
		
		System.out.println("Testing MATYAS function");
		eq = parser.parse(TestFunction.MATYAS.asString());
		optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
		
		System.out.println("Testing THREE_HUMP_CAMEL function");
		eq = parser.parse(TestFunction.THREE_HUMP_CAMEL.asString());
		optimum = SimulatedAnnealing.findGlobalOptimum(eq, 10_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
	}
	
	@Test
	public void testDifferentialEvolution()
	{
		EquationParser parser = new EquationParser();
		
		System.out.println("Testing ROSENBROCK function");
		Equation eq = parser.parse(TestFunction.ROSENBROCK.asString());
		double[] optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();		
		printResults(eq, optimum);
		System.out.println("Exact extrema: 1 1 Global MINIMA: 0 \n");
		
		System.out.println("Testing BEALE function");
		eq = parser.parse(TestFunction.BEALE.asString());
		optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 3 0.5 Global MINIMA: 0 \n");
		
		System.out.println("Testing ACKLEY function");
		eq = parser.parse(TestFunction.ACKLEY.asString());
		optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
		
		System.out.println("Testing BOOTH function");
		eq = parser.parse(TestFunction.BOOTH.asString());
		optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 1 3 Global MINIMA: 0 \n");
		
		System.out.println("Testing GOLDSTEIN_PRICE function");
		eq = parser.parse(TestFunction.GOLDSTEIN_PRICE.asString());
		optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 -1 Global MINIMA: 3 \n");
		
		System.out.println("Testing MATYAS function");
		eq = parser.parse(TestFunction.MATYAS.asString());
		optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
		
		System.out.println("Testing THREE_HUMP_CAMEL function");
		eq = parser.parse(TestFunction.THREE_HUMP_CAMEL.asString());
		optimum = DifferentialEvolution.findGlobalOptimum(eq, 1_000, new SearchDomain(-5, 5, -5, 5)).getVariables();	
		printResults(eq, optimum);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
	}
	
}

import org.junit.jupiter.api.Test;

import equation.Equation;
import equation.EquationParser;
import optimization.Optimum;
import optimization.SearchDomain;
import optimization.SimulatedAnnealing;

public class OptimizationsTests
{
	@Test
	public void testSimulatedAnnealing()
	{
		EquationParser parser = new EquationParser();

		System.out.println("Testing ROSENBROCK function");
		Equation eq = parser.parse(TestFunction.ROSENBROCK.asString());
		SimulatedAnnealing s = new SimulatedAnnealing();
		double[] optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));		
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 1 1 Global MINIMA: 0 \n");
		
		System.out.println("Testing BEALE function");
		eq = parser.parse(TestFunction.BEALE.asString());
		s = new SimulatedAnnealing();
		optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));	
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 3 0.5 Global MINIMA: 0 \n");
		
		System.out.println("Testing ACKLEY function");
		eq = parser.parse(TestFunction.ACKLEY.asString());
		s = new SimulatedAnnealing();
		optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));	
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
		
		System.out.println("Testing BOOTH function");
		eq = parser.parse(TestFunction.BOOTH.asString());
		s = new SimulatedAnnealing();
		optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));	
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 1 3 Global MINIMA: 0 \n");
		
		System.out.println("Testing GOLDSTEIN_PRICE function");
		eq = parser.parse(TestFunction.GOLDSTEIN_PRICE.asString());
		s = new SimulatedAnnealing();
		optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));	
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 0 -1 Global MINIMA: 3 \n");
		
		System.out.println("Testing MATYAS function");
		eq = parser.parse(TestFunction.MATYAS.asString());
		s = new SimulatedAnnealing();
		optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));	
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
		
		System.out.println("Testing THREE_HUMP_CAMEL function");
		eq = parser.parse(TestFunction.THREE_HUMP_CAMEL.asString());
		s = new SimulatedAnnealing();
		optimum = s.findGlobalOptimum(eq, Optimum.MINIMA, 10_000, new SearchDomain(-5, 5, -5, 5));	
		printResults(eq, optimum, Optimum.MINIMA);
		System.out.println("Exact extrema: 0 0 Global MINIMA: 0 \n");
	}
	
	private void printResults(Equation equation, double[] variables, Optimum optimum)
	{
		System.out.print("Position: ");
		for(int i = 0; i < variables.length; i++) System.out.printf("%.4f ", variables[i]);
		
		double result = equation.compute(variables);
		System.out.printf("Global %s: %.4f \n", optimum.name(), result);
	}
	
}

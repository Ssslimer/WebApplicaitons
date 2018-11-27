import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import equation.Equation;
import equation.EquationParser;

public class EquationParserTests
{
	@Test
	public void testSimpleEquations()
	{
		EquationParser parser = new EquationParser();
		
		Equation eq = parser.parse("5+4");
		double result = eq.compute();
		Assertions.assertEquals(9, result, 0.0001f);	
		
		eq = parser.parse("10+5");
		result = eq.compute();
		Assertions.assertEquals(15, result, 0.0001f);
		
		eq = parser.parse("5*4");
		result = eq.compute();
		Assertions.assertEquals(20, result, 0.0001f);
		
		eq = parser.parse("10/2");
		result = eq.compute();
		Assertions.assertEquals(5, result, 0.0001f);
		
		eq = parser.parse("5-4");
		result = eq.compute();
		Assertions.assertEquals(1, result, 0.0001f);
		
		eq = parser.parse("5^2");
		result = eq.compute();
		Assertions.assertEquals(25, result, 0.0001f);
		
		eq = parser.parse("5^3");
		result = eq.compute();
		Assertions.assertEquals(125, result, 0.0001f);
		
		eq = parser.parse("10/20");
		result = eq.compute();
		Assertions.assertEquals(0.5f, result, 0.0001f);
		
		eq = parser.parse("5-14");
		result = eq.compute();
		Assertions.assertEquals(-9, result, 0.0001f);
		
		eq = parser.parse("(2+3)*2");
		result = eq.compute();
		Assertions.assertEquals(10, result, 0.0001f);

		eq = parser.parse("(2+3)^2");
		result = eq.compute();
		Assertions.assertEquals(25, result, 0.0001f);
		
		eq = parser.parse("((2+3)^2)/5");
		result = eq.compute();
		Assertions.assertEquals(5, result, 0.0001f);

		eq = parser.parse("2+3*4");
		result = eq.compute();
		Assertions.assertEquals(14, result, 0.0001f);
		
		eq = parser.parse("-1+3");
		result = eq.compute();
		Assertions.assertEquals(2, result, 0.0001f);
		
		eq = parser.parse("-1/1");
		result = eq.compute();
		Assertions.assertEquals(-1, result, 0.0001f);
			
		eq = parser.parse("(2+3)*(6/3)");
		result = eq.compute();
		Assertions.assertEquals(10, result, 0.0001f);
		
		eq = parser.parse("(2+3)*(6/3)^2");
		result = eq.compute();
		Assertions.assertEquals(20, result, 0.0001f);

		eq = parser.parse("((2+3)*(6/3))^2");
		result = eq.compute();
		Assertions.assertEquals(100, result, 0.0001f);
				
		eq = parser.parse("((2+3)*(6/3))^2-15");
		result = eq.compute();
		Assertions.assertEquals(85, result, 0.0001f);

		eq = parser.parse("((2+3)*(6/3))^2-15*2");
		result = eq.compute();
		Assertions.assertEquals(70, result, 0.0001f);
		
		eq = parser.parse("(((2+3)*(6/3))^2-15)*2");
		result = eq.compute();
		Assertions.assertEquals(170, result, 0.0001f);
		
		eq = parser.parse("1+2*3^2");
		result = eq.compute();
		Assertions.assertEquals(19, result, 0.0001f);
		
		eq = parser.parse("1+(1-1)^2*0");
		result = eq.compute();
		Assertions.assertEquals(1, result, 0.0001f);
		
		eq = parser.parse("4^0.5");
		result = eq.compute();
		Assertions.assertEquals(2, result, 0.0001f);
		
		eq = parser.parse("125^(1/3)");
		result = eq.compute();
		Assertions.assertEquals(5, result, 0.0001f);
		
		eq = parser.parse("pi");
		result = eq.compute();
		Assertions.assertEquals(Math.PI, result, 0.01f);
		
		eq = parser.parse("2*pi");
		result = eq.compute();
		Assertions.assertEquals(2*Math.PI, result, 0.01f);
		
		eq = parser.parse("pi^2");
		result = eq.compute();
		Assertions.assertEquals(Math.pow(Math.PI, 2), result, 0.01f);
		
		eq = parser.parse("pi^(2*15-10)");
		result = eq.compute();
		Assertions.assertEquals(Math.pow(Math.PI, 2*15-10), result, 0.01f);
		
		eq = parser.parse("2*e");
		result = eq.compute();
		Assertions.assertEquals(2*Math.E, result, 0.01f);
		
		eq = parser.parse("exp(1+12/10)");
		result = eq.compute();
		Assertions.assertEquals(Math.exp(1+12d/10d), result, 0.01f);

		eq = parser.parse("exp2");
		result = eq.compute();
		Assertions.assertEquals(Math.exp(2), result, 0.01f);
		
		eq = parser.parse("2*e+1");
		result = eq.compute();
		Assertions.assertEquals(2*Math.E+1, result, 0.01f);
		
		eq = parser.parse("1+e*2");
		result = eq.compute();
		Assertions.assertEquals(1+Math.E*2, result, 0.01f);
		
		eq = parser.parse("2*e+5");
		result = eq.compute();
		Assertions.assertEquals(2*Math.E+5, result, 0.01f);
		
		eq = parser.parse("1+e*2^3");
		result = eq.compute();
		Assertions.assertEquals(1 + Math.E * Math.pow(2, 3), result, 0.01f);
		
		eq = parser.parse("cos0");
		result = eq.compute();
		Assertions.assertEquals(1, result, 0.01f);
		
		eq = parser.parse("sin0");
		result = eq.compute();
		Assertions.assertEquals(0, result, 0.01f);
		
		eq = parser.parse("cos(2*pi)");
		result = eq.compute();
		Assertions.assertEquals(1, result, 0.01f);
		
		eq = parser.parse("exp(2-1)");
		result = eq.compute();
		Assertions.assertEquals(Math.exp(1), result, 0.0001f);
		
		eq = parser.parse("exp(2-1)+1");
		result = eq.compute();
		Assertions.assertEquals(Math.exp(1)+1, result, 0.0001f);
		
		eq = parser.parse("exp(2-1)");
		result = eq.compute();
		Assertions.assertEquals(Math.exp(1), result, 0.0001f);
		
		eq = parser.parse("-exp(2-1)");
		result = eq.compute();
		Assertions.assertEquals(-Math.exp(1), result, 0.0001f);
		
		eq = parser.parse("1+cos(1-1)");
		result = eq.compute();
		Assertions.assertEquals(1+Math.cos(1-1), result, 0.0001f);
		
		eq = parser.parse("1+(1-1)+3");
		result = eq.compute();
		Assertions.assertEquals(1+(1-1)+3, result, 0.0001f);
		
		eq = parser.parse("(5-2)*(8-6)*11");
		result = eq.compute();
		Assertions.assertEquals(66, result, 0.0001f);	
		
		eq = parser.parse("-(5-1)+3");
		result = eq.compute();
		Assertions.assertEquals(-(5-1)+3, result, 0.0001f);
		
		eq = parser.parse("1+cos(1-1)+3");
		result = eq.compute();
		Assertions.assertEquals(1+Math.cos(1-1)+3, result, 0.0001f);

		eq = parser.parse("2*exp(2-1)-3");
		result = eq.compute();
		Assertions.assertEquals(2*Math.exp(1)-3, result, 0.0001f);
		
		eq = parser.parse("exp(-5*(2*2)^0.5)");
		result = eq.compute();
		Assertions.assertEquals(Math.exp(-10), result, 0.0001f);
		
		eq = parser.parse("-20*exp(-0.2*(0.5*(1+1))^0.5)");
		result = eq.compute();
		Assertions.assertEquals(-20*Math.exp(-0.2f), result, 0.0001f);
		
		eq = parser.parse("-exp(0.5*(cos(2*pi)+cos(2*pi)))");
		result = eq.compute();
		Assertions.assertEquals(-Math.exp(1), result, 0.0001f);
		
		eq = parser.parse("-2*exp(1)-exp(1)");
		result = eq.compute();
		Assertions.assertEquals(-2*Math.exp(1)-Math.exp(1), result, 0.0001f);
		
		eq = parser.parse("-3*5+7");
		result = eq.compute();
		Assertions.assertEquals(-8, result, 0.0001f);
	}
	
	@Test
	public void testParametrizedEquations()
	{
		EquationParser parser = new EquationParser();
		
		Equation eq = parser.parse("par0");
		double result = eq.compute(1);
		Assertions.assertEquals(1, result, 0.0001f);
		
		eq = parser.parse("par0");
		result = eq.compute(10);
		Assertions.assertEquals(10, result, 0.0001f);
		
		eq = parser.parse("par0+10");
		result = eq.compute(10);
		Assertions.assertEquals(20, result, 0.0001f);
		
		eq = parser.parse("-par0*2+1");
		result = eq.compute(0);
		Assertions.assertEquals(1, result, 0.0001f);
		
		eq = parser.parse("par0+par1");
		result = eq.compute(10, 20);
		Assertions.assertEquals(30, result, 0.0001f);
		
		eq = parser.parse("par0*10+par1-15");
		result = eq.compute(10, 20);
		Assertions.assertEquals(105, result, 0.0001f);
		
		eq = parser.parse("((2+3)*(6/par0))^2-par1");
		result = eq.compute(3, 50);
		Assertions.assertEquals(50, result, 0.0001f);
		
		eq = parser.parse("(((2+3)*(6/par0))^2-par1)*2");
		result = eq.compute(3, 50);
		Assertions.assertEquals(100, result, 0.0001f);
		
		eq = parser.parse("exp(-0.2*(0.5*(par0^2+par1^2))^0.5)");
		result = eq.compute(0, 0);
		Assertions.assertEquals(Math.exp(-0.2d*Math.sqrt(0)), result, 0.01f);
			
		eq = parser.parse("-exp(-0.2*(0.5*(par0^2+par1^2))^0.5)");
		result = eq.compute(1, 1);
		Assertions.assertEquals(-Math.exp(-0.2f), result, 0.0001f);
		
		eq = parser.parse("exp(-0.2*(0.5*(par0^2+par1^2))^0.5)");
		result = eq.compute(1, 1);
		Assertions.assertEquals(Math.exp(-0.2f), result, 0.0001f);
			
		eq = parser.parse("-20*exp(-0.2*(0.5*(par0^2+par1^2))^0.5)");
		result = eq.compute(1, 1);
		Assertions.assertEquals(-20*Math.exp(-0.2f), result, 0.0001f);
		
		eq = parser.parse("-exp(-0.2*(0.5*(par0^2+par1^2))^0.5)-exp(0.5*(cos(2*pi*par0)+cos(2*pi*par1)))");
		result = eq.compute(1, 1);
		Assertions.assertEquals(-Math.exp(-0.2f) - Math.exp(1), result, 0.0001f);
	}	
	
	@Test
	public void testTestingEquations()
	{
		EquationParser parser = new EquationParser();
		
		Equation eq = parser.parse(TestFunction.ROSENBROCK.asString());
		double result = eq.compute(1, 1);
		Assertions.assertEquals(0, result, 0.0001f);
		
		eq = parser.parse(TestFunction.MATYAS.asString());
		result = eq.compute(0, 0);
		Assertions.assertEquals(0, result, 0.0001f);
		
		eq = parser.parse(TestFunction.BEALE.asString());
		result = eq.compute(3, 0.5);
		Assertions.assertEquals(0, result, 0.0001f);
		
		eq = parser.parse(TestFunction.THREE_HUMP_CAMEL.asString());
		result = eq.compute(0, 0);
		Assertions.assertEquals(0, result, 0.0001f);
		
		eq = parser.parse(TestFunction.BOOTH.asString());
		result = eq.compute(1, 3);
		Assertions.assertEquals(0, result, 0.0001f);
		
		eq = parser.parse(TestFunction.GOLDSTEIN_PRICE.asString());
		result = eq.compute(0, -1);
		Assertions.assertEquals(3, result, 0.0001f);
		
		eq = parser.parse(TestFunction.ACKLEY.asString());
		result = eq.compute(0, 0);
		Assertions.assertEquals(0, result, 0.0001f);
	}

}

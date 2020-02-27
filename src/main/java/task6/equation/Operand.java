package equation;

import java.io.Serializable;

enum Operand implements Serializable
{
	VALUE(0), VARIABLE(0), ADD(1), MINUS(1), DIVIDE(2), MULTIPLY(2), COSINE(2), SINE(2), POWER(3);
	
	private int power;    

	Operand(int power)
	{
		this.power = power;
	}

	int getPower()
	{
		return power;
	}
}

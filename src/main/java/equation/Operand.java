package equation;

public enum Operand
{
	VALUE(0), VARIABLE(0), ADD(1), MINUS(1), DIVIDE(2), MULTIPLY(2), COSINE(2), SINE(2), POWER(3);
	
	private int power;    

	private Operand(int power)
	{
		this.power = power;
	}

	public int getPower()
	{
		return power;
	}
}

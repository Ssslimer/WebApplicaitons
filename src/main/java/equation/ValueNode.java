package equation;

class ValueNode extends Node
{
	double value;
	
	public ValueNode(double pi)
	{
		super(Operand.VALUE);
		this.value = pi;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(value);
		builder.append(")");
		
		return builder.toString();
	}
}

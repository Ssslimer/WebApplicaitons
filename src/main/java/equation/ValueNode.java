package equation;

import java.io.Serializable;

class ValueNode extends Node implements Serializable
{
	private static final long serialVersionUID = 8487979342022600195L;
	
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

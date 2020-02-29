package task6.equation;

import java.io.Serializable;

class VariableNode extends Node implements Serializable
{
	private static final long serialVersionUID = -803095058690597308L;
	
	int variableIndex;
	
	VariableNode(int variableIndex)
	{
		super(Operand.VARIABLE);
		this.variableIndex = variableIndex;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(operand.name() +" ");
		builder.append(variableIndex);
		if(left != null) builder.append(left.toString());
		if(right != null) builder.append(right.toString());
		builder.append(")");
		
		return builder.toString();
	}
}

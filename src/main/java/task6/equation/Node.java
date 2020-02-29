package task6.equation;

import java.io.Serializable;

class Node implements Serializable
{
	private static final long serialVersionUID = 6466646292999705039L;
	
	Operand operand;
	Node left, right;
	
	Node(Operand operand)
	{
		this.operand = operand;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(operand.name() +" ");
		if(left != null) builder.append(left.toString());
		if(right != null) builder.append(right.toString());
		builder.append(")");
		
		return builder.toString();
	}
}

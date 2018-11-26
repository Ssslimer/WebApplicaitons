package equation;

public class Node
{
	Operand operand;
	Node left, right;
	
	public Node(Operand operand)
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

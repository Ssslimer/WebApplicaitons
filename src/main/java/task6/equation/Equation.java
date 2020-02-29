package task6.equation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Equation implements Serializable
{
	private static final long serialVersionUID = 9120785136454094701L;
	
	Node root;
	Set<Integer> variables = new HashSet<>();
	
	public double compute(double... variables)
	{
		if(this.variables.size() == 0 && variables != null)
		{
			if(variables.length != this.variables.size()) throw new IllegalArgumentException("Wrong number of variables");
		}	

		double result = computeNode(root, variables);
		
		return result;
	}
	
	private double computeNode(Node node, double... variables)
	{		
		switch(node.operand)
		{
			case ADD:		return computeNode(node.left, variables) + computeNode(node.right, variables);
			case MINUS:		return computeNode(node.left, variables) - computeNode(node.right, variables);
			case MULTIPLY: 	return computeNode(node.left, variables) * computeNode(node.right, variables);
			case DIVIDE:	return computeNode(node.left, variables) / computeNode(node.right, variables);
			case POWER: 	return Math.pow(computeNode(node.left, variables), computeNode(node.right, variables));
			case VALUE: 	return ((ValueNode)node).value;
			case VARIABLE: 	return variables[((VariableNode)node).variableIndex];
			case COSINE:	return Math.cos(computeNode(node.right, variables));
			case SINE:		return Math.sin(computeNode(node.right, variables));
		}
		
		return 0;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Equation of ");
		builder.append(variables.size());
		builder.append(" variables:\n");
		builder.append(root.toString());
		
		return builder.toString();
	}
	
	public Set<Integer> getVariables()
	{
		return variables;
	}
	
}

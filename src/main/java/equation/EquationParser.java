package equation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationParser
{
	Pattern floatPattern = Pattern.compile("[+-]?(\\d+\\.\\d+|\\d+)([eE]\\d+)?");
	Pattern intPattern = Pattern.compile("\\d+");
	
	public Equation parse(String s)
	{
		Equation equation = new Equation();
		
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			
			switch(c)
			{
				case '+': 
					if(equation.root != null)
					{
						Node newRoot = new Node(Operand.ADD);
						newRoot.left = equation.root;
						equation.root = newRoot;
					}
				break;
				
				case '-':
					if(equation.root == null) 
					{
						equation.root = new Node(Operand.MINUS);
						equation.root.left = new ValueNode(0);
					}
					else
					{
						Node newRoot = new Node(Operand.MINUS);
						newRoot.left = equation.root;
						equation.root = newRoot;
					}
				break;
				
				case '*':
					if(equation.root != null)
					{
						Node newRoot = new Node(Operand.MULTIPLY);
						newRoot.left = equation.root;
						equation.root = newRoot;
					}
				break;
				
				case '/':
					if(equation.root != null)
					{
						Node newRoot = new Node(Operand.DIVIDE);
						newRoot.left = equation.root;
						equation.root = newRoot;
					}
				break;
				
				case '^':
					if(equation.root != null)
					{
						Node newRoot = new Node(Operand.POWER);
						newRoot.left = equation.root;
						equation.root = newRoot;
					}
				break;
				
				case '(':
					if(equation.root == null)
					{
						SubequationData subequationData = findSubequationFromBrackets(s, i, equation, false);
						Equation subEquation = parse(subequationData.subequation);					
						equation.variables.addAll(subEquation.variables);
						equation.root = subEquation.root;
						
						i = subequationData.jumpTo-1;
					}
					else
					{					
						int nextPower = findNextOperandPower(s.substring(i, s.length()), equation);
						if(equation.root.operand.getPower() - nextPower >= 0)
						{
							if(equation.root.operand == Operand.MINUS)
							{
								SubequationData subequationData = findSubequationFromBrackets(s, i, equation, true);
								Equation subEquation = parse(subequationData.subequation);	
								equation.variables.addAll(subEquation.variables);
								equation.root.right = subEquation.root;
								i += subequationData.jumpTo-2;
							}
							else
							{
								String s2 = s.substring(i);
								Equation subEquation = parse(s2);
								equation.variables.addAll(subEquation.variables);
								equation.root.right = subEquation.root;
								i = s.length();
							}
						}
						else
						{
							SubequationData subequationData = findSubequationFromBrackets(s, i, equation, false);
							Equation subEquation = parse(subequationData.subequation);					
							equation.variables.addAll(subEquation.variables);						
							equation.root.right = subEquation.root;
							i += subequationData.jumpTo-1;
						}		
					}	
				break;
				
				
				default:					
					if(isDigit(c))
					{
						String stringToCheck = s.substring(i, s.length());
						Matcher matcher = floatPattern.matcher(stringToCheck);
		
						if(matcher.find())
						{
							String numberLiteral = matcher.group();
							float value = Float.parseFloat(numberLiteral);
							
							if(equation.root != null && equation.root.left != null)
							{
								if(equation.root.operand.getPower() - findNextOperandPower(stringToCheck, equation) >= 0)
								{
									equation.root.right = new ValueNode(value);
								}
								else
								{
									SubequationData subequationData = findSubequation(s.substring(i), equation.root.operand.getPower());
									Equation subEquation = parse(subequationData.subequation);
									equation.variables.addAll(subEquation.variables);
									
									if(equation.root == null) equation.root = subEquation.root;
									else
									{
										i += subequationData.jumpTo-1;
										equation.root.right = subEquation.root;
									}
	
									break;
								}
							}						
							else if(equation.root == null || equation.root.left == null) equation.root = new ValueNode(value);
	
							i += numberLiteral.length()-1;
						}
					}
					else if(isLetter(c))
					{
						if(c == 'e' && (s.length() == i+1 || (s.length() >= i+1 && !isLetter(s.charAt(i+1)))))
						{
							String stringToCheck = s.substring(i+1, s.length());
							
							if(equation.root != null && equation.root.left != null)
							{
								if(equation.root.operand.getPower() - findNextOperandPower(stringToCheck, equation) >= 0)
								{
									equation.root.right = new ValueNode(Math.E);
								}
								else
								{
									SubequationData subequationData = findSubequation(s.substring(i), equation.root.operand.getPower());
									Equation subEquation = parse(subequationData.subequation);
									equation.variables.addAll(subEquation.variables);
									
									if(equation.root == null) equation.root = subEquation.root;
									else
									{
										i += subequationData.jumpTo-1;
										equation.root.right = subEquation.root;
									}
	
									break;
								}
							}						
							else if(equation.root == null || equation.root.left == null) equation.root = new ValueNode(Math.E);
						}
						else if(s.length() >= i+3 && c == 'c' && s.charAt(i+1) == 'o' && s.charAt(i+2) == 's')
						{
							if(s.charAt(i+3) == '(')
							{
								SubequationData subequationData = findSubequationFromBrackets(s, i+3, equation, true);
								Equation subEquation = parse(subequationData.subequation);					
								equation.variables.addAll(subEquation.variables);
								
								if(equation.root == null)
								{
									equation.root = new Node(Operand.COSINE);
									equation.root.right = subEquation.root;
								}
								else
								{
									equation.root.right = new Node(Operand.COSINE);
									equation.root.right.right = subEquation.root;
								}
								
								i = subequationData.jumpTo-1;
							}
							else
							{
								SubequationData subequationData = findSubequation(s.substring(i+3), equation.root == null ? 0 : equation.root.operand.getPower());
								Equation subEquation = parse(subequationData.subequation);					
								equation.variables.addAll(subEquation.variables);
								
								if(equation.root == null)
								{
									equation.root = new Node(Operand.COSINE);
									equation.root.right = subEquation.root;
								}
								else
								{
									if(equation.root.operand.getPower() - findNextOperandPower(s.substring(i, s.length()), equation) >= 0)
									{
										subEquation = parse(s.substring(i+3));
										equation.variables.addAll(subEquation.variables);
										equation.root.right = new Node(Operand.COSINE);
										equation.root.right.right = subEquation.root;
										i = s.length();
										continue;
									}
									else equation.root.right = subEquation.root;		
								}
								
								i += subequationData.jumpTo+2;
							}
						}
						else if(s.length() >= i+3 && c == 's' && s.charAt(i+1) == 'i' && s.charAt(i+2) == 'n')
						{
							if(s.charAt(i+3) == '(')
							{
								SubequationData subequationData = findSubequationFromBrackets(s, i+3, equation, true);
								Equation subEquation = parse(subequationData.subequation);					
								equation.variables.addAll(subEquation.variables);
								
								if(equation.root == null)
								{
									equation.root = new Node(Operand.SINE);
									equation.root.right = subEquation.root;
								}
								else
								{
									equation.root.right = new Node(Operand.SINE);
									equation.root.right.right = subEquation.root;
								}
								
								i = subequationData.jumpTo-1;
							}
							else
							{
								SubequationData subequationData = findSubequation(s.substring(i+3), equation.root == null ? 0 : equation.root.operand.getPower());
								Equation subEquation = parse(subequationData.subequation);					
								equation.variables.addAll(subEquation.variables);
								
								if(equation.root == null)
								{
									equation.root = new Node(Operand.SINE);
									equation.root.left = subEquation.root;
									equation.root.right = subEquation.root;
								}
								else
								{
									if(equation.root.operand.getPower() - findNextOperandPower(s.substring(i, s.length()), equation) >= 0)
									{
										subEquation = parse(s.substring(i+3));
										equation.variables.addAll(subEquation.variables);
										equation.root.right = new Node(Operand.SINE);
										equation.root.right.left = subEquation.root;
										equation.root.right.right = subEquation.root;
										i = s.length();
										continue;
									}
									else equation.root.right = subEquation.root;		
								}
								
								i = subequationData.jumpTo-1;
							}
						}
						else if(s.length() >= i+3 && c == 'e' && s.charAt(i+1) == 'x' && s.charAt(i+2) == 'p')
						{
							if(s.charAt(i+3) == '(')
							{
								SubequationData subequationData = findSubequationFromBrackets(s, i+3, equation, true);
								Equation subEquation = parse(subequationData.subequation);					
								equation.variables.addAll(subEquation.variables);
								
								if(equation.root == null)
								{
									equation.root = new Node(Operand.POWER);
									equation.root.left = new ValueNode(Math.E);
									equation.root.right = subEquation.root;
								}
								else
								{
									equation.root.right = new Node(Operand.POWER);
									equation.root.right.left = new ValueNode(Math.E);
									equation.root.right.right = subEquation.root;
								}
								
								i = subequationData.jumpTo-1;
							}
							else
							{
								SubequationData subequationData = findSubequation(s.substring(i+3), equation.root == null ? 0 : equation.root.operand.getPower());
							
								Equation subEquation = parse(subequationData.subequation);					
								equation.variables.addAll(subEquation.variables);
								
								if(equation.root == null)
								{
									equation.root = new Node(Operand.POWER);
									equation.root.left = new ValueNode(Math.E);
									equation.root.right = subEquation.root;
								}
								else
								{
									if(equation.root.operand.getPower() - findNextOperandPower(s.substring(i, s.length()), equation) >= 0)
									{
										subEquation = parse(s.substring(i+3));
										equation.variables.addAll(subEquation.variables);
										equation.root.right = new Node(Operand.POWER);
										equation.root.right.left = new ValueNode(Math.E);
										equation.root.right.right = subEquation.root;
										i = s.length();
										continue;
									}
									else equation.root.right = subEquation.root;		
								}
								
								i = subequationData.jumpTo-1;
							}
						}
						else if(s.length() >= i+1 && c == 'p' && s.charAt(i+1) == 'i')
						{
							String stringToCheck = s.substring(i+2, s.length());
							
							if(equation.root != null && equation.root.left != null)
							{
								if(equation.root.operand.getPower() - findNextOperandPower(stringToCheck, equation) >= 0)
								{
									equation.root.right = new ValueNode(Math.PI);
								}
								else
								{
									SubequationData subequationData = findSubequation(s.substring(i), equation.root.operand.getPower());
									Equation subEquation = parse(subequationData.subequation);
									equation.variables.addAll(subEquation.variables);
									
									if(equation.root == null) equation.root = subEquation.root;
									else
									{
										i = subequationData.jumpTo-1;
										equation.root.right = subEquation.root;
									}
	
									break;
								}
							}						
							else if(equation.root == null || equation.root.left == null) equation.root = new ValueNode(Math.PI);
	
							i++;
						}						
						else if(s.length() >= i+3 && c == 'p' && s.charAt(i+1) == 'a' && s.charAt(i+2) == 'r')
						{
							String stringToCheck = s.substring(i+3, s.length());
							Matcher matcher = intPattern.matcher(stringToCheck);
			
							if(matcher.find())
							{
								String integerLateral = matcher.group();
								int parIndex = Integer.parseInt(integerLateral);
								
								if(equation.root != null && equation.root.left != null)
								{
									if(equation.root.operand.getPower() - findNextOperandPower(stringToCheck, equation) >= 0)
									{
										equation.root.right = new VariableNode(parIndex);										
										equation.variables.add(parIndex);
									}
									else
									{
										SubequationData subequationData = findSubequation(s.substring(i), equation.root.operand.getPower());
										Equation subEquation = parse(subequationData.subequation);									
										equation.variables.addAll(subEquation.variables);
										
										if(equation.root == null) equation.root = subEquation.root;
										else
										{
											i += subequationData.jumpTo-1;
											equation.root.right = subEquation.root;
										}
		
										equation.variables.add(parIndex);
										
										break;
									}
								}						
								else if(equation.root == null || equation.root.left == null)
								{
									equation.root = new VariableNode(parIndex);
								}
																
								equation.variables.add(parIndex);								
								i += integerLateral.length()+2;
							}
						}
					}
					
				break;
			}
		}
		
		return equation;
	}
	
	private boolean isLetter(char c)
	{
		return (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z');
	}
	
	private boolean isDigit(char c)
	{
		return (c >= '0' && c <= '9');
	}
	
	private SubequationData findSubequationFromBrackets(String s, int begin, Equation eq, boolean b) throws IndexOutOfBoundsException
	{
		int basicPower = (eq.root == null) ? 0 : eq.root.operand.getPower();

		int afterBrackets = skipBrackets(s, begin);
		if(b || eq.root == null) return new SubequationData(s.substring(begin+1, afterBrackets-1), afterBrackets);
		
		boolean foundOperand = false;
		for(int i = afterBrackets; i < s.length(); i++)
		{
			char c = s.charAt(i);
			switch(c)
			{
				case '+':
				case '-':
				case '*':
				case '/':
				case '^':
					if(foundOperand) return new SubequationData(s.substring(begin, i), i);
					if(!hasStrongerOperand(s, i, basicPower)) foundOperand = true;
					break;
					
				case '(': i = skipBrackets(s, i)-1;
				default : break;
			}
		}

		return new SubequationData(s.substring(begin+1), s.length());	
	}
	
	private int skipBrackets(String s, int begin)
	{
		int openings = 1;
		int closings = 0;
		int pointer = begin+1;
			
		while(openings != closings && pointer != s.length())
		{
			char nextChar = s.charAt(pointer);

			if(nextChar == '(') openings++;
			else if(nextChar == ')') closings++;
				
			pointer++;
		}
		
		return pointer;
	}
	
	private SubequationData findSubequation(String s, int basicPower) throws IndexOutOfBoundsException
	{
		boolean foundOperand = false;
			
		int j = 0;
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			
			switch(c)
			{
				case '+':
				case '-':
				case '*':
				case '/':
				case '^':
					if(foundOperand) return new SubequationData(s.substring(0, i), i);
					if(!hasStrongerOperand(s, i, basicPower))
					{
						foundOperand = true;
						j = i;
					}
					break;
					
				case '(': i = skipBrackets(s, i)-1;
				default : break;
			}
		}
		
		return new SubequationData(s, s.length());
	}
	
	private boolean hasStrongerOperand(String s, int begin, int operandBeforePower)
	{
		int basicPower = 0;
		char c = s.charAt(begin);
		switch(c)
		{
			case '+': basicPower = Operand.ADD.getPower(); break;
			case '-': basicPower = Operand.MINUS.getPower(); break;
			case '*': basicPower = Operand.MULTIPLY.getPower(); break;
			case '/': basicPower = Operand.DIVIDE.getPower(); break;
			case '^': basicPower = Operand.POWER.getPower(); break;
		}
				
		for(int i = begin+1; i < s.length(); i++)
		{
			switch(s.charAt(i))
			{
				case '+':
					if(Operand.ADD.getPower() - basicPower > 0) return true;
					else return (operandBeforePower - Operand.ADD.getPower() < 0);

				case '-':
					if(Operand.MINUS.getPower() - basicPower > 0) return true;
					else return (operandBeforePower - Operand.MINUS.getPower() < 0);
				
				case '*':
					if(Operand.MULTIPLY.getPower() - basicPower > 0) return true;
					else return (operandBeforePower - Operand.MULTIPLY.getPower() < 0);
					
				case '/':
					if(Operand.DIVIDE.getPower() - basicPower > 0) return true;
					else return (operandBeforePower - Operand.DIVIDE.getPower() < 0);
					
				case '^':
					if(Operand.POWER.getPower() - basicPower > 0) return true;
					else return (operandBeforePower - Operand.POWER.getPower() < 0);
				
				case '(':
					i = skipBrackets(s, i) - 1;
				default : break;
			}
		}
		
		return false;
	}
	
	private class SubequationData
	{
		String subequation;
		int jumpTo;
		
		public SubequationData(String subequation, int jumpTo)
		{
			this.subequation = subequation;
			this.jumpTo = jumpTo;
		}	
	}
	
	private int findNextOperandPower(String s, Equation eq)
	{
		for(int i = 0; i < s.length(); i++)
		{
			switch(s.charAt(i))
			{
				case '+': return Operand.ADD.getPower();
				case '-': return Operand.MINUS.getPower();
				case '*': return Operand.MULTIPLY.getPower();
				case '/': return Operand.DIVIDE.getPower();
				case '^': return Operand.POWER.getPower();
				case '(':
					SubequationData subequationData = findSubequationFromBrackets(s, i, eq, false);
					i = subequationData.jumpTo-1;
			}
		}

		return 0;
	}	
}
